package ysy.dubbo.demo.s1.api.dto;

import org.apache.dubbo.common.serialize.support.SerializationOptimizer;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SerializationOptimizerImpl implements SerializationOptimizer {
    @Override
    public Collection<Class<?>> getSerializableClasses() {
        List<Class<?>> classes = new LinkedList<>();
        classes.add(M2.class);
        return classes;
    }
}
