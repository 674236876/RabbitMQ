package com.rabbit.mq.demo.customer;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/12 11:29
 */
public class RecvArrayList {
    private static final Logger logger = LoggerFactory.getLogger(RecvArrayList.class);
    private static final String QUEUE_HOME = "hello";

    public static void main(String args[]) throws Exception {
        Address[] addresses = {new Address("192.168.1.94"), new Address("192.168.1.94")};
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Connection connection = connectionFactory.newConnection(addresses);
        System.out.println(connection.getAddress().getHostName());
        System.out.println(connection.isOpen());
        connection.addShutdownListener(e -> System.out.println("连接关闭了"));
        Channel channel = connection.createChannel();
        System.out.println(channel.isOpen());

        channel.queueDeclare(QUEUE_HOME, false, false, false, null);
        channel.basicConsume(QUEUE_HOME, true, "",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String message = new String(body, StandardCharsets.UTF_8);
                        System.out.println("收到消息:" + message);
                    }

                    //                    断开连接的时候会被触发。 测试方式 手动断网
                    @Override
                    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                        System.out.println("关闭了连接咯");
                        super.handleShutdownSignal(consumerTag, sig);

                    }

                    @Override
                    public void handleConsumeOk(String consumerTag) {
                        System.out.println("每次消费都会先调我");
                        super.handleConsumeOk(consumerTag);
                    }

                });


    }


}
