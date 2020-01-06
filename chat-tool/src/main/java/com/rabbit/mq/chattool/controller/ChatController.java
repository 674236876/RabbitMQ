package com.rabbit.mq.chattool.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/11 14:51
 * 整合rabbitmq  实现页面与终端的消息发送。 需要启动demo模块中的Recv
 */
@Controller
public class ChatController {

    private static final String QUEUE_NAME = "hello";


    @RequestMapping("/writeMessage")
    @ResponseBody
    public String write(String message) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.94");
        try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
            channel.addReturnListener((i, s, s1, s2, basicProperties, bytes) -> {
                System.out.println("消息发送无法路由了");
            });

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            return "消息已发送";
        }

    }
}
