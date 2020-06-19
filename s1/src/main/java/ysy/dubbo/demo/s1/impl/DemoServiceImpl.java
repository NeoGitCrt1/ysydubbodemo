package ysy.dubbo.demo.s1.impl;

import com.alibaba.dubbo.config.annotation.Service;
import ysy.dubbo.demo.s1.api.DemoService;

@Service
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
