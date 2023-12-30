package com.chocoh.ql.framework;

import cn.hutool.bloomfilter.BitMapBloomFilter;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.DesensitizedUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson.JSON;
import com.chocoh.ql.common.pojo.entity.User;
import com.chocoh.ql.common.pojo.model.LoginUser;
import com.chocoh.ql.common.pojo.model.Response;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chocoh
 * @Description 工具类测试
 * @createTime 2023-12-28 21:00
 */
public class ToolTest {
    @Test
    public void hutoolTest() {
        // 随机验证码
        String string = RandomUtil.randomStringUpper(4);
        System.out.println(string);
        // 保留小数点
        double d = 1243534564.124535345;
        BigDecimal rd = NumberUtil.round(d, 4);
        String sd = NumberUtil.roundStr(d, 4);
        System.out.println(rd);
        System.out.println(sd);
        // 百分号转换
        String df = NumberUtil.decimalFormat("#.##%", 0.543245);
        System.out.println(df);
        // 数据脱敏
        String idCardNum = DesensitizedUtil.idCardNum("354657837578376657", 2, 2);
        System.out.println(idCardNum);
        String mobilePhone = DesensitizedUtil.mobilePhone("12345678901");
        System.out.println(mobilePhone);
        String password = DesensitizedUtil.password("12476284");
        System.out.println(password);
        // 不带-的uuid
        String uuid = IdUtil.simpleUUID();
        System.out.println(uuid);
        // 加密算法
        String md5 = DigestUtil.md5Hex("123123");
        System.out.println(md5);
    }

    @Test
    public void bloomFilterTest() {
        // 布隆过滤器
        BitMapBloomFilter filter = new BitMapBloomFilter(10);
        filter.add("123");
        filter.add("abc");
        System.out.println(filter.contains("abc"));
    }

    @Test
    public void beanUtilTest() {
        User user = new User();
        user.setPassword("123123");
        user.setUsername("chocoh");
        LoginUser loginUser = new LoginUser(user, Collections.singletonList(user.getRole()), null);
        Map<String, Object> stringObjectMap = BeanUtil.beanToMap(loginUser);
    }

    @Test
    public void mapTest() {
        HashMap<String, Object> dataMap = new Response.DataMap()
                .put("1", "a")
                .put("2", "b")
                .put("3", new Response.DataMap()
                        .put("3.1", "c")
                        .put("3.2", "d"));
        QlApplicationTest.printJson(JSON.toJSON(dataMap));
    }
}
