package org.fwx.d02;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.fwx.utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @ClassName Producer
 * @Description 生产者产生多条数据，多个消费者消费
 * @Author Fwx
 * @Date 2024/5/15 12:53
 * @Version 1.0
 */
public class Producer {

    private static final String QUEUE_NAME = "topic2";
    public static void main(String[] args) throws Exception {
        // 2. 创建连接
        Connection connection = RabbitMQUtils.getConnection();

        // 3. 创建channel
        Channel channel = connection.createChannel();

        /**
         * 4. 声明队列，如果队列不存在则创建
         *
         * @param topic 队列的名称。这是一个标识符，用于指定要创建的队列的名称。
         * @param durable 是否标记队列为持久化。如果为true，队列会在服务器重启后仍然存在。
         * @param exclusive 是否标记队列为排他。如果为true，该队列只能被一个连接访问，且在连接断开后自动删除。
         * @param autoDelete 是否标记队列为自动删除。如果为true，当最后一个消费者取消订阅后，该队列会自动删除。
         * @param arguments 一个包含额外参数的Map。可用于指定队列的额外属性，如死信Exchange等。
         */
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        for (int i = 0; i < 10; i++){
            /**
             * 5.在指定的队列上发布消息
             *
             * @param exchange 空字符串，表示使用默认交换器。在RabbitMQ中，交换器根据消息的路由键将消息路由到队列。
             * @param routingKey 主题，即消息的路由键。它用于指定消息应该被路由到哪个队列。
             * @param mandatory false，表示消息是否必须被路由到队列。如果为true，且消息无法被路由时，将会返回一个basic.return方法。
             * @param body 消息体，这里发送的是字符串"hello rabbitmq"的字节数组形式。
             */
            channel.basicPublish("",QUEUE_NAME,null,("hello rabbitmq" + i).getBytes());
        }


    }
}
