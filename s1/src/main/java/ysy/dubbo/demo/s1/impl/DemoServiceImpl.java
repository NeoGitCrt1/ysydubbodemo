package ysy.dubbo.demo.s1.impl;

import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ysy.dubbo.demo.s1.api.DemoService;
import ysy.dubbo.demo.s1.api.dto.M2;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

// 尽量在服务端配置超时
@DubboService(methods = {@Method(name = "acyncHello", timeout = 5000)})
public class DemoServiceImpl implements DemoService {
    private static final Logger LOG = LoggerFactory.getLogger(DemoServiceImpl.class);
    private final AtomicInteger ct = new AtomicInteger();
    @Override
    public String sayHello(String name) {
        ct.set(0);
        return "Hello " + name;
    }

    private final ExecutorService pool = Executors.newFixedThreadPool(2);

    @Override
    public M2 acyncHello(String name) {
        long pp1 = 0;
        long p1 = 1;
        long now = 0;
        for (int i = 2; i < name.length() + 64; i++) {
            now = pp1 + p1;
            pp1 = p1;
            p1 = now;
        }
        LOG.info("acyncHello:{}> {}", ct.incrementAndGet(), now);

        final AsyncContext asyncContext = RpcContext.startAsync();

        pool.submit(() -> {
            // 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 写回响应
            asyncContext.write(new M2("aloha:" + name));
        });

        return null;
    }
}
