package org.fwx.d08;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

/**
 * @ClassName Consumer
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 17:57
 * @Version 1.0
 */
/**
 * 消费者类，用于从RabbitMQ队列中消费消息。
 */
public class Consumer {
    // 定义一个主题交换器的名称
    private static final String EXCHANGE_NAME = "topic_exchange";

    /**
     * 主程序入口，创建消费者实例并调用q1和q2方法进行消息消费。
     *
     * @param args 命令行参数（未使用）
     * @throws Exception 如果发生异常
     */
    public static void main(String[] args) throws Exception {
        Consumer consumer = new Consumer();
        consumer.q1();
        consumer.q2();
    }

    /**
     * 定义并配置队列q1，将其绑定到指定的主题交换器上，消费匹配".orange."的主题消息。
     * *: 匹配一个单词
     * #: 匹配多个单词
     *
     * @throws Exception 如果发生异常
     */
    public void q1() throws Exception {
        String queueName = "q1";
        Channel channel = RabbitMQUtils.getChannel();

        // 声明队列
        channel.queueDeclare(queueName,false,false,false,null);

        // 将队列绑定到交换器，指定路由规则
        channel.queueBind(queueName,EXCHANGE_NAME,"*.orange.*");

        // 开始消费消息
        channel.basicConsume(queueName,true,(s, message) -> {
            System.out.println(queueName + " 接收到的消息是：" + new String(message.getBody()) + ", routingKey:" + message.getEnvelope().getRoutingKey());
        },s -> {});
    }

    /**
     * 定义并配置队列q2，将其绑定到指定的主题交换器上，消费匹配"*.*.rabbit"和"lazy.#"的主题消息。
     * *: 匹配一个单词
     * #: 匹配多个单词
     *
     * @throws Exception 如果发生异常
     */
    public void q2() throws Exception {
        String queueName = "q2";
        Channel channel = RabbitMQUtils.getChannel();

        // 声明队列
        channel.queueDeclare(queueName,false,false,false,null);

        // 将队列绑定到交换器，指定多个路由规则
        channel.queueBind(queueName,EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind(queueName,EXCHANGE_NAME,"lazy.#");

        // 开始消费消息
        channel.basicConsume(queueName,true,(s, message) -> {
            System.out.println(queueName + " 接收到的消息是：" + new String(message.getBody()) + ", routingKey:" + message.getEnvelope().getRoutingKey());
        },s -> {});
    }
}

