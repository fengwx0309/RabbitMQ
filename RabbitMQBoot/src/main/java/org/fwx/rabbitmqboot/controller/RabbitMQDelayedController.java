package org.fwx.rabbitmqboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.fwx.rabbitmqboot.config.DelayedQueueConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @ClassName RabbitMQDelayedController
 * @Description 使用RabbitMQ 插件（rabbitmq_delayed_message_exchange-3.8.0.ez）实现延迟消息发送的控制器。
 * @Author Fwx
 * @Date 2024/5/16 1:15
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class RabbitMQDelayedController {
    // 自动注入RabbitMQ的模板，用于发送消息
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 通过GET请求发送一条延迟消息。
     *      测试：http://localhost:8080/ttl/sendDelayMsg/come on baby1/20000
     *           http://localhost:8080/ttl/sendDelayMsg/come on baby2/2000
     * @param message 要发送的消息内容。
     * @param delayTime 消息的延迟时间，单位为毫秒。
     * 该方法不返回任何内容。
     */
    @GetMapping("/sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer delayTime) {
        // 发送延迟消息，延迟时间由delayTime参数指定
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME, DelayedQueueConfig.DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(delayTime);
                    return correlationData;
                });
        // 记录发送延迟消息的日志
        log.info(" 当前时间： {}, 发送一条延迟 {} 毫秒的信息给队列 delayed.queue:{}", new Date(), delayTime, message);
    }

}

