package ru.job4j.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        boolean rsl  = memory.computeIfPresent(model.getId(), (k, v) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            return new Base(model.getId(), model.getVersion() + 1);
        }) != null;
        memory.get(model.getId()).setName(model.getName());
        return  rsl;
    }

    public void delete(Base model) {
        memory.computeIfPresent(model.getId(), (k, v) -> {
            Base stored = memory.get(model.getId());
            if (stored.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            memory.remove(model.getId());
            return v;
        });
    }

    public Base get(int key) {
        return memory.get(key);
    }
}