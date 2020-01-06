package com.rabbit.mq.demo.customer;
import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhugp
 * @description TODO
 * @date 2019/12/11 9:52
 */
public class Recv2 {
    private static final Logger logger = LoggerFactory.getLogger(Recv2.class);

    private static String QUEUE_NAME = "hello";
    private static int i = 0;
    private static final boolean autoack = false;

    public static void main(String args[]) throws Exception {
        ConnectionFactory connectionFactory = new ConnectionFactory();
//        可以通过这种方式来给多个MQ地址，会返回第一个链接成功的连接
//        Address[] addresses = {new Address("192.168.1.94"), new Address("192.168.1.94")};
//        Connection connection = connectionFactory.newConnection(addresses);
        connectionFactory.setHost("192.168.1.94");
        ExecutorService es = Executors.newFixedThreadPool(20);
        Connection connection = connectionFactory.newConnection(es);
        connection.addShutdownListener(e -> System.out.println("关闭完成"));
        Channel channel = connection.createChannel();

        logger.info(String.valueOf(connection.isOpen()), channel.isOpen());


        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println("接受信息啦");
//        为了保证在整个节点范围内生成唯一的消费者标签，要么使用不带消费者标签的basicConsume方法或者就传递一个空字符串
//        下面这里用myConsumerTag作为消费者标签 官方不建议使用
//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
//            System.out.println("[x] Received ' " + message + "'（已处理"+(i++)+"）");
//        };
//        String consumeTag = channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
//        });

//        autoack如果参数为false 意为不自动确认收到。需要channel.basicAck来手动确认收到才行，否则消息会被重复消费
        String consumeTag = channel.basicConsume(QUEUE_NAME, autoack, "",
                new DefaultConsumer(channel) {
                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        String routingKey = envelope.getRoutingKey();
                        String contentType = properties.getContentType();
                        long deliveryTag = envelope.getDeliveryTag();
                        // (process the message components here ...)
                        String message = new String(body, StandardCharsets.UTF_8);
                        System.out.println("[x] Received ' " + message + "'（已处理"+(i++)+"）");
                        logger.info(routingKey, contentType, deliveryTag);
                        channel.basicAck(deliveryTag, false);
                    }

//                    断开连接的时候会被触发。 测试方式 手动断网
                    @Override
                    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
                        System.out.println("关闭了连接咯");
                        super.handleShutdownSignal(consumerTag,sig);

                    }

                    @Override
                    public void handleConsumeOk(String consumerTag) {
                        System.out.println("每次消费都会先调我");
                        super.handleConsumeOk(consumerTag);
                    }

                    @Override
                    public void handleCancelOk(String consumerTag) {
                        System.out.println("取消成功");
                        super.handleCancelOk(consumerTag);
                    }
                });

        System.out.println("consume标识符：" + consumeTag + "(被用来取消消费者的)");


    }

}
