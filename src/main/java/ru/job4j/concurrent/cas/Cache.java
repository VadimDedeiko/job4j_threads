package ru.job4j.concurrent.cas;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (e, s) -> {
                    if (model.getVersion() != s.getVersion()) {
                        throw new OptimisticException("Versions are not equal");
                    }
                    return new Base(model.getId(), s.getVersion() + 1);
                }
        ) != null;
    }
    public void delete(Base model) {
        memory.remove(model.getId(), model);
    }
}