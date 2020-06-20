package ysy.dubbo.demo.s2.sys;

import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ysy.dubbo.demo.s1.api.DemoService;

@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {


    @DubboReference
    private DemoService demoService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.println(demoService.sayHello("aaa"));
    }


}
