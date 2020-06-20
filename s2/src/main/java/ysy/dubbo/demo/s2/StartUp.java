package ysy.dubbo.demo.s2;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@EnableDubbo
@PropertySource("classpath:/dubbo.properties")
@SpringBootApplication
public class StartUp {
    public static void main(String[] args) {
        SpringApplication.run(StartUp.class, args);
    }


}
