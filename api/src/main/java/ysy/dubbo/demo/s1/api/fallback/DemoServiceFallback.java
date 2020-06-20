package ysy.dubbo.demo.s1.api.fallback;

import ysy.dubbo.demo.s1.api.DemoService;
import ysy.dubbo.demo.s1.api.dto.M2;

public class DemoServiceFallback implements DemoService {
    @Override
    public String sayHello(String name) {
        return ">>>" + name;
    }

    @Override
    public M2 acyncHello(String name) {
        return new M2("??" + name);
    }

    @Override
    public void asyncTask(String name) {

    }
}
