package org.fwx.rabbitmqboot.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @ClassName SwaggerConfig
 * @Description TODO
 * @Author Fwx
 * @Date 2024/5/15 21:59
 * @Version 1.0
 */
@Configuration
//@EnableWebMvc // springboot 2.6.x，需要添加 @EnableWebMvc 解决Swagger与SpringMVC冲突的问题
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 配置Docket Bean以启用Swagger文档生成。
     *
     * @return Docket配置实例
     */
    @Bean
    public Docket webApiConfig() {
        // 创建Docket实例并配置Swagger文档的基本信息
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi") // 设置API的分组名称
                .apiInfo(webApiInfo()) // 设置API的基本信息
                .select() // 返回一个ApiSelectorBuilder实例，用来控制那些接口暴露给Swagger来展现
                //.paths(Predicates.and(PathSelectors.regex("/.*"))) // 指定路径处理PathSelectors.regex("/.*")代表所有的路径
                .build(); // 创建Docket实例
    }


    /**
     * 构建 API 信息对象
     *
     * @return 返回一个构建好的 ApiInfo 对象，包含接口文档的标题、描述、版本信息以及联系方式
     */
    private ApiInfo webApiInfo(){
        // 使用 ApiInfoBuilder 来构建 ApiInfo 对象
        return new ApiInfoBuilder()
                .title("rabbitmq 接口文档") // 设置文档标题
                .description("本文档描述了 rabbitmq 微服务接口定义") // 设置文档描述
                .version("1.0") // 设置文档版本号
                .contact(new Contact("fwx", "http://fwx.org", "88889999@qq.com")) // 设置文档作者及联系方式
                .build(); // 构建并返回 ApiInfo 对象
    }

}
