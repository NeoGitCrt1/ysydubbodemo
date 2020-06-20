package ysy.dubbo.demo.s2.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ysy.dubbo.demo.s1.api.DemoService;

@RestController()
@RequestMapping("/s2")
public class SimpleController {


    @DubboReference
    private DemoService demoService;

    @GetMapping("/{name}")
    public Mono<String> hi(@PathVariable String name) {
        return Mono.just(demoService.sayHello(name));
    }
}
