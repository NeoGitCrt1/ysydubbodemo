package ysy.dubbo.demo.s2.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Autowired;
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

    @NacosValue(value = "${useLocalCache:false}", autoRefreshed = true)
    private boolean useLocalCache;


    @Autowired
    private DemoService rpcDemo;

    @GetMapping("/h/{name}")
    public Mono<String> hi(@PathVariable String name) {
//        EchoService es = (EchoService) demoService;
        Mono<String> res = Mono.just(rpcDemo.sayHello(name) + useLocalCache);
//        Mono.just(es.$echo("OK")).log().subscribe();
        return res.log();
    }

    @GetMapping("/a/{name}")
    public Mono<M2> hia(@PathVariable String name) {
        for (int i = 0; i < 7; i++) {
            rpcDemo.asyncTask(name);
        }
        M2 fallback = rpcDemo.acyncHello(name);
        // normally "fallback" must be null
        if (fallback != null) {
            // rpc exception happened
            return Mono.just(fallback);
        }
        CompletableFuture<M2> helloFuture = RpcContext.getContext().getCompletableFuture();


        return Mono
                .fromFuture(helloFuture)
                .onErrorReturn(new M2("Mansch"))
                .log();
    }
}
