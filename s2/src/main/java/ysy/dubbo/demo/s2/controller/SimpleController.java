package ysy.dubbo.demo.s2.controller;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.service.EchoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import ysy.dubbo.demo.s1.api.DemoService;
import ysy.dubbo.demo.s1.api.dto.M2;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/s2")
public class SimpleController {


    @DubboReference(methods = {@Method(name = "acyncHello", async = true)})
    private DemoService demoService;

    @GetMapping("/h/{name}")
    public Mono<String> hi(@PathVariable String name) {
        EchoService es = (EchoService) demoService;
        Mono<String> res = Mono.just(demoService.sayHello(name));
        Mono.just(es.$echo("OK")).log().subscribe();
        return res.log();
    }

    @GetMapping("/a/{name}")
    public Mono<M2> hia(@PathVariable String name) {
        CompletableFuture<M2> helloFuture = null;
        for (int i = 0; i < 5; i++) {
            demoService.acyncHello(name);
            helloFuture = RpcContext.getContext().getCompletableFuture();
        }

        return Mono.fromFuture(helloFuture).log();
    }
}
