package ysy.dubbo.demo.s1;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
@NacosPropertySource(dataId = "s1-conf", autoRefreshed = true)
public class StartUp {
    public static void main(String[] args) {
        SpringApplication.run(StartUp.class, args);
    }

}
