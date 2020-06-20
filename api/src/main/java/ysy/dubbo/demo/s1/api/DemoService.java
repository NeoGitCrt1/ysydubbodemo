package ysy.dubbo.demo.s1.api;

import ysy.dubbo.demo.s1.api.dto.M2;

public interface DemoService {
    String sayHello(String name);

    M2 acyncHello(String name);
}
