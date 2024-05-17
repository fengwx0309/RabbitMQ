package org.fwx.d04;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.fwx.utils.RabbitMQUtils;

import java.util.Scanner;

/**
 * @ClassName Producer
 * @Description 设置队列持久化，消息持久化
 *              消息不公平分配策略: 默认轮询分发消息
 *                               设置值为1时，谁消费的快，消费完成就分配消息给谁
 *                               预取值：值需要大于1，消费者按设置的值来获取对应的条数，比如预取值为5，则消费者每次获取5条消息
 * @Author Fwx
 * @Date 2024/5/15 13:37
 * @Version 1.0
 */
public class Producer {
    private static final String QUEUE_NAME = "topic4";

    /**
     * 主函数：用于发送消息到指定队列。
     * @param args 命令行参数（未使用）
     * @throws Exception 抛出异常处理通用异常
     */
    public static void main(String[] args) throws Exception {
        // 获取RabbitMQ的Channel
        Channel channel = RabbitMQUtils.getChannel();
        // 声明队列，并设置持久化
        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME,durable,false,false,null);

        // 使用Scanner从标准输入读取消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            // 读取并发送消息
            String message = scanner.next();
            // 消息持久化    MessageProperties.PERSISTENT_TEXT_PLAIN
            channel.basicPublish("",QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("发送消息完成：" + message);
        }
    }

}
