package org.fwx.d08;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

import java.util.HashMap;

/**
 * @ClassName Producer
 * @Description 交换机 topic 模式
 *               先运行 Producer，创建交换机，然后运行 Consumer，创建队列。
 * @Author Fwx
 * @Date 2024/5/15 17:51
 * @Version 1.0
 */
public class Producer {
    private static final String EXCHANGE_NAME = "topic_exchange";

    /**
     * 主函数：用于演示如何使用RabbitMQ发送消息。
     * 通过声明一个主题交换器，并发布消息到该交换器，演示了如何根据路由键和绑定规则将消息路由到一个或多个队列中。
     *
     * @param args 命令行参数（未使用）
     * @throws Exception 如果发生错误
     */
    public static void main(String[] args) throws Exception {
        // 获取与RabbitMQ服务器的连接通道
        Channel channel = RabbitMQUtils.getChannel();

        // 声明一个主题交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        // 准备消息及其预期的接收队列
        HashMap<String, String> messages = new HashMap<>();

        // 各种消息与预期接收队列的映射
        messages.put("quick.orange.rabbit","被队列 Q1Q2 接收到");
        messages.put("lazy.orange.elephant","被队列 Q1Q2 接收到");
        messages.put("quick.orange.fox","被队列 Q1 接收到");
        messages.put("lazy.brown.fox","被队列 Q2 接收到");
        messages.put("lazy.pink.rabbit","虽然满足两个绑定但只被队列 Q2 接收一次");
        messages.put("quick.brown.fox","不匹配任何绑定不会被任何队列接收到会被丢弃");
        messages.put("quick.orange.male.rabbit","是四个单词不匹配任何绑定会被丢弃");
        messages.put("lazy.orange.male.rabbit","是四个单词但匹配 Q2");

        // 发布消息到交换器
        for (String key : messages.keySet()) {
            String message = messages.get(key);
            channel.basicPublish(EXCHANGE_NAME,key,null,message.getBytes());

            // 打印消息发送记录
            System.out.println("生产者发出消息:" + key + "=" + message);
        }
    }

}
