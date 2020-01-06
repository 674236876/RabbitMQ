package com.rabbit.mq.practice.mqlogs;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/13 17:45
 */
public class Publisher {
    private static final String EXCHAGE_NAME = "log_exchage_fanout";

    public static void main(String args[]) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.94");
        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHAGE_NAME, "fanout");
            for (int i = 0; i < 3; i++) {
                String message = new Date() + "----logs";
                channel.basicPublish(EXCHAGE_NAME, "", null, message.getBytes());
            }
        }
    }
}
