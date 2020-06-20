package ysy.dubbo.demo.s2.sys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ysy.dubbo.demo.s1.api.DemoService;

@Component
public class StartupApplicationListener implements
        ApplicationListener<ContextRefreshedEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(StartupApplicationListener.class);
    @Autowired
    private DemoService rpcDemo;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        LOG.info(">>>>>>{}", rpcDemo.sayHello("aaa"));
    }


}
