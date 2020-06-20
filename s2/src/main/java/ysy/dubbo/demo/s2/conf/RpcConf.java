package ysy.dubbo.demo.s2.conf;

import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.Method;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ysy.dubbo.demo.s1.api.DemoService;

@Configuration
public class RpcConf {

    @DubboReference(
            check = false,
            mock = "ysy.dubbo.demo.s1.api.fallback.DemoServiceFallback",
            methods = {
                    @Method(name = "acyncHello", async = true),
                    @Method(name = "asyncTask", async = true, isReturn = false)
            })
    private DemoService demoService;

    @Bean
    public DemoService rpcDemo() {
        return demoService;
    }
}
