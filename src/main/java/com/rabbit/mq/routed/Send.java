package com.rabbit.mq.routed;

import com.rabbit.mq.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {

    private final static String EXCHANGE_NAME = "test_exchange_routed";

    public static void main(String[] argv) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtil.getConnection();
        Channel channel = connection.createChannel();

        // 声明exchange
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        // 消息内容
        String delMessage = "我发送了一条消息到交换机，希望只要绑定了这个交换机的删除事件消费者都能够消费";
        String addMessage = "我发送了一条消息到交换机，希望只要绑定了这个交换机的添加事件消费者都能够消费";
        channel.basicPublish(EXCHANGE_NAME, "delete", null, delMessage.getBytes());
        channel.basicPublish(EXCHANGE_NAME, "add", null, addMessage.getBytes());
        System.out.println(" [x] Sent '" + delMessage + "'");
        System.out.println(" [x] Sent '" + addMessage + "'");

        channel.close();
        connection.close();
    }
}
