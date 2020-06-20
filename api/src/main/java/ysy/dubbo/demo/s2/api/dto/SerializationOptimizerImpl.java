package ysy.dubbo.demo.s2.api.dto;

import org.apache.dubbo.common.serialize.support.SerializationOptimizer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 要让Kryo和FST完全发挥出高性能，最好将那些需要被序列化的类注册到dubbo系统中
 * 然后在XML配置中添加：
 * <dubbo:protocol name="dubbo" serialization="kryo" optimizer="xxx.xxxx.SerializationOptimizerImpl"/>
 */
public class SerializationOptimizerImpl implements SerializationOptimizer {
    @Override
    public Collection<Class<?>> getSerializableClasses() {
        List<Class<?>> classes = new LinkedList<>();
        classes.add(M762.class);
        return classes;
    }
}
