package com.chocoh.ql.framework.ascept;

import com.alibaba.fastjson.JSON;
import com.chocoh.ql.common.exception.GlobalException;
import com.chocoh.ql.common.utils.ServletUtil;
import lombok.Data;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

import static com.chocoh.ql.common.constant.Constants.LIMIT_BUCKET_KEY;
import com.chocoh.ql.common.annotation.Limit;

/**
 * @author chocoh
 */
@Data
@Component
@Aspect
public class LimitAop {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Around("@annotation(com.chocoh.ql.common.annotation.Limit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        // 获取方法签名、方法和注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Limit limit = method.getAnnotation(Limit.class);
        if (limit != null) {
            // 获取访问的IP地址
            String remoteAddr = ServletUtil.getRemoteAddr().replace(":", "-");
            // 获取方法名
            String methodName = "".equals(limit.key()) ? method.getName() : limit.key();
            // 盘段是否根据IP限流，拼接存储在redis中的key
            String key;
            if (limit.onIp()) {
                key = LIMIT_BUCKET_KEY + methodName + ":" + remoteAddr;
            } else {
                key = LIMIT_BUCKET_KEY + methodName;
            }
            // 获取当前单位时间对应接口的IP访问次数
            String json = stringRedisTemplate.opsForValue().get(key);
            Integer count = JSON.parseObject(json, Integer.class);
            if (count == null) {
                // 桶不存在则创建
                stringRedisTemplate.opsForValue().set(key, "1", limit.timeout(), limit.timeunit());
            } else if (count < limit.permits()) {
                // 小于单位时间最大访问次
                stringRedisTemplate.opsForValue().set(key, JSON.toJSONString(count + 1), limit.timeout(), limit.timeunit());
            } else {
                // 接口访问太频繁
                throw new GlobalException(limit.message());
            }
        }
        return joinPoint.proceed();
    }
}
