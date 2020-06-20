package ysy.dubbo.demo.s1.impl

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.apache.dubbo.config.annotation.DubboService
import org.apache.dubbo.config.annotation.Method
import org.apache.dubbo.rpc.RpcContext
import org.slf4j.LoggerFactory
import ysy.dubbo.demo.s1.api.DemoService
import ysy.dubbo.demo.s1.api.dto.M2
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.atomic.AtomicInteger

// 尽量在服务端配置超时
@DubboService(
        methods = arrayOf(
                Method(name = "acyncHello", timeout = 5000),
                Method(name = "asyncTask", timeout = 5000)))
class DemoServiceImplK : DemoService {
    private val LOG = LoggerFactory.getLogger(DemoServiceImpl::class.java)
    private val ct = AtomicInteger()
    override fun sayHello(name: String): String {
        ct.set(0)
        return "hello $name"
    }

    override fun acyncHello(name: String): M2? = runBlocking<M2?> {
        val asyncContext = RpcContext.startAsync()
        val id = ct.incrementAndGet()
        launch {
            var pp1: Long = 0
            var p1: Long = 1
            var now: Long = 0
            for (i in 2 until name.length + 64) {
                now = pp1 + p1
                pp1 = p1
                p1 = now
            }
            LOG.info("acyncHello:{}> {}", id, now)

            // 如果要使用上下文，则必须要放在第一句执行
            asyncContext.signalContextSwitch()
            // 写回响应
            asyncContext.write(M2("aloha:$name"))
        }
        null
    }

    private val tr = ThreadLocalRandom.current()

    override fun asyncTask(name: String): Unit = runBlocking<Unit> {
        val id = ct.incrementAndGet()
        launch {
            delay(tr.nextLong(5000))
            LOG.info("TASK {} DONE", id)
        }
    }
}