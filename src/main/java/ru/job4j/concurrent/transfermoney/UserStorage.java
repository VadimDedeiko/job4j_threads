package ru.job4j.concurrent.transfermoney;

import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;
import java.util.concurrent.ConcurrentHashMap;


@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final ConcurrentHashMap<Integer, User> storage = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return storage.putIfAbsent(user.getId(), new User(user.getId(), user.getAmount())) == null;
    }

    public synchronized boolean update(User user) {
        return storage.put(user.getId(), new User(user.getId(), user.getAmount())) == null;
    }

    public synchronized boolean delete(User user) {
        return storage.remove(user.getId()) == null;
    }

    public synchronized void transfer(int fromId, int toId, int amount) {
        User userFrom = storage.get(fromId);
        User userTo = storage.get(toId);
        update(new User(userFrom.getId(), userFrom.getAmount() - amount));
        update(new User(userTo.getId(), userTo.getAmount() + amount));
    }
}
