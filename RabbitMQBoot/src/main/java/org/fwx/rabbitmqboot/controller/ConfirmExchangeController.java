package org.fwx.rabbitmqboot.controller;

import lombok.extern.slf4j.Slf4j;
import org.fwx.rabbitmqboot.config.ConfirmExchangeConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ComfirmExchangeController
 * @Description 发布确认模式生产者
 * @Author Fwx
 * @Date 2024/5/16 9:49
 * @Version 1.0
 */

@Slf4j
@RestController
@RequestMapping("/confirm")
public class ConfirmExchangeController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     *  http://localhost:8080/confirm/send/测试数据
     * @param msg
     */
    @GetMapping("/send/{msg}")
    public void send(@PathVariable("msg") String msg) {
        rabbitTemplate.convertAndSend(ConfirmExchangeConfig.CONFIRM_EXCHANGE_NAME, ConfirmExchangeConfig.CONFIRM_ROUTING_KEY,msg);
        log.info("发送消息：{}",msg);
    }
}
