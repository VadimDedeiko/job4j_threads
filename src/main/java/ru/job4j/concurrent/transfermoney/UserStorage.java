package ru.job4j.concurrent.transfermoney;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;


@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> storage = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return storage.putIfAbsent(user.getId(), user) == null;
    }

    public synchronized boolean update(User user) {
        return storage.replace(user.getId(), user) == null;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId(), user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rsl = false;
        User userFrom = storage.get(fromId);
        User userTo = storage.get(toId);
        if (userFrom != null && userTo != null && userFrom.getAmount() >= amount) {
            userFrom.setAmount(userFrom.getAmount() - amount);
            userTo.setAmount(userTo.getAmount() + amount);
            rsl = true;
        }
        return rsl;
    }
}
