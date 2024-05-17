package org.fwx.rabbitmqboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.fwx.rabbitmqboot.config.RabbitMQTopicConfig;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName RabbitMQTopicController
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/16 0:17
 * @Version 1.0
 */
@Slf4j
@RestController
public class RabbitMQTopicController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/topic")
    public void send() {
        rabbitTemplate.convertAndSend(RabbitMQTopicConfig.TOPIC_EXCHANGE, "www.rabbitmq.server", "message1");
        rabbitTemplate.convertAndSend(RabbitMQTopicConfig.TOPIC_EXCHANGE, "com.rabbitmq.client", "message2");
        rabbitTemplate.convertAndSend(RabbitMQTopicConfig.TOPIC_EXCHANGE, "com.kafka.server", "message3");
    }

    @Autowired
    private TopicExchange exchange;
    @GetMapping("/testmq")
    public void testRabbitMQ(){
        log.info(rabbitTemplate.toString());
        log.info(rabbitTemplate.isRunning()+"");
        log.info(exchange.toString());
    }
}
