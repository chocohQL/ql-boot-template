package com.chocoh.ql.framework;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chocoh.ql.common.constant.Constants;
import com.chocoh.ql.common.model.Response;
import com.chocoh.ql.Manager.TaskServiceManager;
import com.chocoh.ql.dal.domain.entity.User;
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
    private TaskServiceManager taskServiceManager;

    @Test
    public void starterTest() {
        for (int i = 0; i < 10; i++) {
            taskServiceManager.execute(() -> {
                        System.out.println(Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000 * 5);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
            );
        }
    }
}
