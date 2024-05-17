package org.fwx.d06;

import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

import java.util.Scanner;

/**
 * @ClassName Producer
 * @Description 交换机 fanout 扇出/广播模式，发布消息到交换器，交换器将消息广播给所有绑定的队列。
 * @Author Fwx
 * @Date 2024/5/15 16:12
 * @Version 1.0
 */
public class Producer {
    private static final String EXCHANGE_NAME = "fanout_exchange";

    /**
     * 主函数：创建一个RabbitMQ通道，声明一个扇形交换器，然后从标准输入读取消息并发布到交换器。
     * @param args 命令行参数（未使用）
     * @throws Exception 如果发生错误
     */
    public static void main(String[] args) throws Exception {
        // 获取RabbitMQ的通道
        Channel channel = RabbitMQUtils.getChannel();

        // 声明一个名为EXCHANGE_NAME的扇形交换器
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");

        // 创建一个Scanner对象从System.in读取输入
        Scanner scanner = new Scanner(System.in);

        // 循环读取标准输入的消息
        while (scanner.hasNext()) {
            // 读取下一条消息
            String message = scanner.next();
            // 将消息发布到交换器
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            // 打印消息发送成功的提示
            System.out.println("生产者发出消息" + message);
        }

    }

}
