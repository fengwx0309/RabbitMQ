package org.fwx.rabbitmqboot.mqconsumer;

import org.fwx.rabbitmqboot.config.RabbitMQTopicConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName RabbitMQTopicConsumer
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/16 0:24
 * @Version 1.0
 */
@Component
public class RabbitMQTopicConsumer {

    @RabbitListener(queues = RabbitMQTopicConfig.TOPIC_QUEUE1)
    public void topic1(String msg) {
        System.out.println("topic_queue1 接收到消息：" + msg);
    }

    @RabbitListener(queues = RabbitMQTopicConfig.TOPIC_QUEUE2)
    public void topic2(String msg) {
        System.out.println("topic_queue2 接收到消息：" + msg);
    }
}
