package org.fwx.rabbitmqboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ControllerTest
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 22:17
 * @Version 1.0
 */
@Api(tags = "测试")
@RestController
public class ControllerTest {

    @ApiOperation("测试方法")
    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
