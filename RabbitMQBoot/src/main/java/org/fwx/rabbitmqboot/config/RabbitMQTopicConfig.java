package org.fwx.rabbitmqboot.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName RabbitMQTopicConfig
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/16 0:04
 * @Version 1.0
 */
@Configuration
public class RabbitMQTopicConfig {


   public static final String TOPIC_EXCHANGE = "topic_exchange";

    public static final String TOPIC_QUEUE1 = "topic_queue1";

    public static final String TOPIC_QUEUE2 = "topic_queue2";

    @Bean(name = "topicExchange")
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean(name = "topicQueue1")
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1,false,false,false);
    }

    @Bean(name = "topicQueue2")
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2,false,false,false);
    }

    @Bean(name = "topicExchangeBindingQueue1")
    public Binding topicExchangeBindingQueue1(@Qualifier("topicQueue1") Queue queue,TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("*.rabbitmq.*");
    }

    /**
     * 创建一个从队列到主题交换器的绑定。
     * 这个绑定使用通配符模式"*.*.server"，意味着它可以匹配所有以"server"结尾的路由键。
     * 这样，任何符合该模式的消息都会被路由到这个队列中。
     *
     * @param queue 要绑定的队列，通过@Qualifier注解指定为"topicQueue2"。
     * @param exchange 要绑定到的主题交换器。
     * @return 返回一个表示队列和交换器之间绑定关系的对象。
     */
    @Bean(name = "topicExchangeBindingQueue2")
    public Binding topicExchangeBindingQueue2(@Qualifier("topicQueue2") Queue queue, TopicExchange exchange) {
        // 使用BindingBuilder创建绑定，将队列绑定到交换器上，使用特定的路由键模式
        return BindingBuilder.bind(queue).to(exchange).with("*.*.server");
    }

}
