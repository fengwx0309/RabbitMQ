package org.fwx.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @ClassName RabbitMQUtils
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 12:55
 * @Version 1.0
 */
public class RabbitMQUtils {

    /**
     * 获取与消息队列服务器的连接。
     *
     * @return Connection 返回与消息队列服务器建立的连接对象。
     * @throws Exception 如果在建立连接时发生错误，则抛出异常。
     */
    public static Connection getConnection() throws Exception{
        // 创建ConnectionFactory实例用于配置和创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置消息队列服务器的主机地址
        factory.setHost("192.168.2.100");
        // 设置消息队列服务器的端口号
        factory.setPort(5672);
        // 设置连接消息队列服务器的用户名
        factory.setUsername("admin");
        // 设置连接消息队列服务器的密码
        factory.setPassword("123");

        // 使用配置的ConnectionFactory创建一个连接
        Connection connection = factory.newConnection();
        return connection;
    }

    /**
     * 获取与消息队列服务器的通道。
     * @return
     * @throws Exception
     */
    public static Channel getChannel() throws Exception{
        // 创建ConnectionFactory实例用于配置和创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // 设置消息队列服务器的主机地址
        factory.setHost("192.168.2.100");
        // 设置消息队列服务器的端口号
        factory.setPort(5672);
        // 设置连接消息队列服务器的用户名
        factory.setUsername("admin");
        // 设置连接消息队列服务器的密码
        factory.setPassword("123");

        // 使用配置的ConnectionFactory创建一个连接
        Connection connection = factory.newConnection();
        return connection.createChannel();
    }

}
