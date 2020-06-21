package ysy.dubbo.demo.s1.impl;

import org.apache.dubbo.rpc.AsyncContext;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ysy.dubbo.demo.s1.api.DemoService;
import ysy.dubbo.demo.s1.api.dto.M2;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * see kotlin folder
 */


//// 尽量在服务端配置超时
//@DubboService(methods = {
//        @Method(name = "acyncHello", timeout = 5000),
//        @Method(name = "asyncTask", timeout = 5000)
//})
public class DemoServiceImpl implements DemoService {
    private static final Logger LOG = LoggerFactory.getLogger(DemoServiceImpl.class);
    private final AtomicInteger ct = new AtomicInteger();

    @Override
    public String sayHello(String name) {
        ct.set(0);
        return "Hello " + name;
    }

    private final ExecutorService pool = new ThreadPoolExecutor(
            3, 3,
            10, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>()
    );

    @Override
    public M2 acyncHello(String name) {
        final int id = ct.incrementAndGet();
        final AsyncContext asyncContext = RpcContext.startAsync();

        pool.submit(() -> {
            long pp1 = 0;
            long p1 = 1;
            long now = 0;
            for (int i = 2; i < name.length() + 64; i++) {
                now = pp1 + p1;
                pp1 = p1;
                p1 = now;
            }
            LOG.info("acyncHello:{}> {}", id, now);

            // 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch();
            // 写回响应
            asyncContext.write(new M2("aloha:" + name));
        });

        return null;
    }

    private final ThreadLocalRandom tr = ThreadLocalRandom.current();

    @Override
    public void asyncTask(String name) {

        final int id = ct.incrementAndGet();
        pool.submit(() -> {
            try {
                Thread.sleep(tr.nextInt(5000));
                LOG.info("TASK {} DONE", id);
            } catch (InterruptedException e) {
                return;
            }
        });
    }
}
