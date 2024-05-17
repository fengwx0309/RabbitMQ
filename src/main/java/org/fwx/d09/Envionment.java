package org.fwx.d09;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

import java.util.HashMap;

/**
 * @ClassName Envionment
 * @Description 死信队列，死信队列的配置，环境初始化
 * @Author Fwx
 * @Date 2024/5/15 19:14
 * @Version 1.0
 */
public class Envionment {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";

    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE = "dead_queue";

    public static final String NORMAL_ROUTING_KEY = "normal";
    public static final String DEAD_ROUTING_KEY = "dead";

    /**
     * 初始化交换机和队列
     * 此方法会声明两个交换器（一个用于正常消息，一个用于死信），两个队列（一个用于正常消息，一个用于存储死信），
     * 并将队列绑定到相应的交换器上。此外，还会为正常消息的队列配置死信交换器和相关的参数。
     *
     * @throws Exception 如果在初始化过程中遇到任何RabbitMQ操作异常，都会抛出此异常。
     */
    public static void init() throws Exception {
        // 获取RabbitMQ的Channel
        Channel channel = RabbitMQUtils.getChannel();

        // 声明正常消息交换器
        channel.exchangeDeclare(NORMAL_EXCHANGE, "direct");
        // 声明死信交换器
        channel.exchangeDeclare(DEAD_EXCHANGE, "direct");

        // 配置死信交换器的参数
        HashMap<String, Object> arguments = new HashMap<>();
        // 将指定的参数添加到arguments映射中，用于配置死信交换和死信路由键。
        // "x-dead-letter-exchange" - 指定消息的死信交换，这是一个特殊的消息交换机，用于处理被拒绝或超过重试次数的消息。
        // "x-dead-letter-routing-key" - 指定死信路由键，它是一个标识符，用于将死信消息路由到死信交换机。
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE); // 设置死信交换
        arguments.put("x-dead-letter-routing-key", "dead"); // 设置死信路由键为"dead"
        // 消息存活时间，超过此时间将被认定为死信。最好在生产者中设置此参数！！！
        // arguments.put("x-message-ttl", 10000);

        // 声明正常消息队列和死信队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 将正常消息队列绑定到正常消息交换器上
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, NORMAL_ROUTING_KEY);
        // 将死信队列绑定到死信交换器上
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, DEAD_ROUTING_KEY);
    }

    /**
     * 初始化交换机和队列，并设置队列长度为6,超出长度的消息将被认定为死信。
     *
     * @throws Exception 如果在初始化过程中遇到任何RabbitMQ操作异常，都会抛出此异常。
     */
    public static void initWithNormalQueueLength() throws Exception {
        // 获取RabbitMQ的Channel
        Channel channel = RabbitMQUtils.getChannel();

        // 声明正常消息交换器
        channel.exchangeDeclare(NORMAL_EXCHANGE, "direct");
        // 声明死信交换器
        channel.exchangeDeclare(DEAD_EXCHANGE, "direct");

        // 配置死信交换器的参数
        HashMap<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE); // 设置死信交换
        arguments.put("x-dead-letter-routing-key", "dead"); // 设置死信路由键为"dead"
        arguments.put("x-max-length", 6); // 设置队列长度为6，超出长度的消息将被认定为死信。

        // 声明正常消息队列和死信队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        // 将正常消息队列绑定到正常消息交换器上
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, NORMAL_ROUTING_KEY);
        // 将死信队列绑定到死信交换器上
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, DEAD_ROUTING_KEY);
    }
}
