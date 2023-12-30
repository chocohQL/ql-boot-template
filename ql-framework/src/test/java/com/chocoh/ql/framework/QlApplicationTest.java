package com.chocoh.ql.framework;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chocoh.ql.common.constant.Constants;
import com.chocoh.ql.common.pojo.entity.User;
import com.chocoh.ql.common.pojo.model.Response;
import com.chocoh.ql.common.utils.EmailClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author chocoh
 */
@SpringBootTest
public class QlApplicationTest {
    public static void main(String[] args) {
        Response success = Response.success(new ArrayList<>());
        QlApplicationTest.printJson(success);

        Response response = Response.dataMap()
                .put("1", "参数一")
                .put("2", "参数二")
                .getMap("3")
                .put("3.1", System.currentTimeMillis())
                .put("3.2", new Random().nextInt(8))
                .getMap("3.3")
                .put("4.1", Constants.HEADER_TOKEN)
                .put("4.2", new User(1L, "chocoh", "123123", "admin"))
                .put("4.3", new Response.DataMap().put("5.1", true).put("5.2", ""))
                .ok()
                .put("其他外层参数", "...");
        QlApplicationTest.printJson(response);
    }

    public static void printJson(Object obj) {
        JSON json = JSON.parseObject(JSON.toJSONString(obj));
        String formattedJsonString = JSONObject.toJSONString(json, true);
        System.out.println(formattedJsonString);
    }

    @Autowired
    private EmailClient emailClient;

    @Test
    public void emailTest() {
        emailClient.sendHttpEmail(
                CollUtil.newArrayList("xxxx@qq.com"),
                "【QL】hello",
                "<h1>hello</h1>",
                "D:\\code\\Java\\ql-boot-template\\ql-vue-template\\public\\icon.png");
    }
}
