package org.fwx.d11;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.fwx.utils.RabbitMQUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Producer
 * @Description 优先级队列，参数设置：x-max-priority。需要先把数据全部发完，再启动消费者
 * @Author Fwx
 * @Date 2024/5/16 23:27
 * @Version 1.0
 */
public class Producer {
    private static final String QUEUE_NAME = "priority_queue";

    public static void main(String[] args) throws Exception {
        // 获取与RabbitMQ的通信通道
        Channel channel = RabbitMQUtils.getChannel();

        // 设置队列优先级参数
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority",10);

        // 声明一个具有优先级的队列
        channel.queueDeclare(QUEUE_NAME,true,false,false,arguments);

        /**
         * 循环发送信息到指定的队列。
         * 此段代码会发送10条消息到同一个队列中，其中第5条消息的优先级会被设置为5。
         *
         * @param channel AMQP通道，用于消息的发布。
         * @param QUEUE_NAME 队列名称，指定消息发送的目的地。
         */
        for (int i = 0; i < 10; i++) {
            // 构造消息文本，信息内容为"info"加上当前循环的次数。
            String msg = "info" + i;
            // 如果当前循环次数为5，设置消息的优先级为5。
            if (i == 5){
                AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().priority(5).build();
                // 发送带优先级的消息到队列。
                channel.basicPublish("", QUEUE_NAME, basicProperties, msg.getBytes("UTF-8"));
            } else {
                // 发送普通消息到队列。
                channel.basicPublish("", QUEUE_NAME, null, msg.getBytes("UTF-8"));
            }

            // 打印发送的消息内容。
            System.out.println("发送消息：" + msg);
        }

    }

}
