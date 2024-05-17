package org.fwx.d07;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

import java.util.Scanner;

/**
 * @ClassName Producer
 * @Description 交换机 direct 直接模式，根据路由键进行消息投递
 * @Author Fwx
 * @Date 2024/5/15 16:30
 * @Version 1.0
 */
public class Producer {
    private static final String EXCHANGE_NAME = "direct_exchange";

    /**
     * 主函数：用于通过RabbitMQ发送消息。
     * 使用者在控制台输入数字1或2或3，程序将相应地发送不同路由键的消息到指定交换器。
     *
     * @param args 命令行参数（未使用）
     * @throws Exception 如果发生错误
     */
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMQUtils.getChannel();

        // 声明交换器
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        // 从控制台读取输入
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            // 获取控制台输入
            String message = scanner.next();
            // 根据输入发送不同路由键的消息
            if (message.equals("1")) {
                channel.basicPublish(EXCHANGE_NAME, "info", null, message.getBytes());
            } else if (message.equals("2")) {
                channel.basicPublish(EXCHANGE_NAME, "warning", null, message.getBytes());
            } else if (message.equals("3")) {
                channel.basicPublish(EXCHANGE_NAME, "error", null, message.getBytes());
            }
            // 输出消息发送完成
            System.out.println("发送消息完成：" + message);
        }

    }

}
