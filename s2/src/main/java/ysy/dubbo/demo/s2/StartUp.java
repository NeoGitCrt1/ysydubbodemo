package ysy.dubbo.demo.s2;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ysy.dubbo.demo.s1.api.DemoService;

@EnableDubbo
@PropertySource("classpath:/dubbo.properties")
@SpringBootApplication
@RestController("s2")
public class StartUp {
    public static void main(String[] args) {
        SpringApplication.run(StartUp.class, args);
    }

    @Reference
    private DemoService demoService;

    @GetMapping("/{name}")
    public String hey(@PathVariable String name) {
        return demoService.sayHello(name);
    }
}
