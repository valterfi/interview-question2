package com.backbase.numbers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.backbase.numbers.controller.NumbersController;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
          .select()
          .apis(RequestHandlerSelectors.basePackage(NumbersController.class.getPackage().getName()))
          .paths(PathSelectors.any())
          .build();
    }
}
