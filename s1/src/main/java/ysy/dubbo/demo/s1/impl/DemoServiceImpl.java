package ysy.dubbo.demo.s1.impl;

import org.apache.dubbo.config.annotation.DubboService;
import ysy.dubbo.demo.s1.api.DemoService;

@DubboService
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
