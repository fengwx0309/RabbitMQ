package org.fwx.d01;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @ClassName Producer
 * @Description 简单的生产者消费者案例
 * @Author Fwx
 * @Date 2024/5/15 10:18
 * @Version 1.0
 */
public class Producer {
    /**
     * 主函数入口，用于演示如何使用RabbitMQ发送消息。
     *
     * @param args 命令行参数（未使用）
     * @throws IOException 如果发生I/O错误
     * @throws TimeoutException 如果连接超时
     */
    public static void main(String[] args) throws IOException, TimeoutException {
        // 定义要发送消息的主题
        String topic = "topic1";

        // 创建ConnectionFactory，配置RabbitMQ服务器的连接信息
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.2.100"); // RabbitMQ服务器地址
        factory.setPort(5672); // RabbitMQ服务器端口
        factory.setUsername("admin"); // 连接RabbitMQ的用户名
        factory.setPassword("123"); // 连接RabbitMQ的密码

        // 建立与RabbitMQ服务器的连接
        Connection connection = factory.newConnection();

        // 创建一个新的通道
        Channel channel = connection.createChannel();

        /**
         * 声明队列，如果队列不存在则创建
         *
         * @param topic 队列的名称。这是一个标识符，用于指定要创建的队列的名称。
         * @param durable 是否标记队列为持久化。如果为true，队列会在服务器重启后仍然存在。
         * @param exclusive 是否标记队列为排他。如果为true，该队列只能被一个连接访问，且在连接断开后自动删除。
         * @param autoDelete 是否标记队列为自动删除。如果为true，当最后一个消费者取消订阅后，该队列会自动删除。
         * @param arguments 一个包含额外参数的Map。可用于指定队列的额外属性，如死信Exchange等。
         */
        channel.queueDeclare(topic, false, false, false, null);

        /**
         * 在指定的队列上发布消息
         *
         * @param exchange 空字符串，表示使用默认交换器。在RabbitMQ中，交换器根据消息的路由键将消息路由到队列。
         * @param routingKey 主题，即消息的路由键。它用于指定消息应该被路由到哪个队列。
         * @param mandatory false，表示消息是否必须被路由到队列。如果为true，且消息无法被路由时，将会返回一个basic.return方法。
         * @param body 消息体，这里发送的是字符串"hello rabbitmq"的字节数组形式。
         */
        channel.basicPublish("", topic, null, "hello rabbitmq".getBytes());

        System.out.println("消息发送成功！");

        // 关闭通道和连接
//        channel.close();
    }

}
