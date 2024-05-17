package org.fwx.d03;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

import java.util.Scanner;

/**
 * @ClassName Producer
 * @Description 多个消费者消费数据，如果某个消费者接收到数据，但是程序出现异常退出，则该条消息会重新发送给其他消费者
 *              需要对消费者应答设置为false，并在消费者中设置消息应答模式为手动应答
 * @Author Fwx
 * @Date 2024/5/15 13:37
 * @Version 1.0
 */
public class Producer {
    private static final String QUEUE_NAME = "topic3";

    /**
     * 主函数：用于发送消息到指定队列。
     * @param args 命令行参数（未使用）
     * @throws Exception 抛出异常处理通用异常
     */
    public static void main(String[] args) throws Exception {
        // 获取RabbitMQ的Channel
        Channel channel = RabbitMQUtils.getChannel();
        // 声明队列，但不持久化
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        // 使用Scanner从标准输入读取消息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            // 读取并发送消息
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("发送消息完成：" + message);
        }
    }

}
