package com.rabbit.mq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/12 16:18
 */
public class ConnectionUtil {


    public static Connection getConnection() throws IOException, TimeoutException {

        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.1.94");
        return connectionFactory.newConnection();
    }
}
