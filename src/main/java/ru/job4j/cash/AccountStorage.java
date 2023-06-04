package ru.job4j.cash;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

public class AccountStorage {
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        return accounts.putIfAbsent(account.id(), account) == null;
    }

    public synchronized boolean update(Account account) {
        return accounts.replace(account.id(), account) != null;
    }

    public synchronized boolean delete(int id) {
        return accounts.remove(id) != null;
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> donorAccount = this.getById(fromId);
        Optional<Account> acceptorAccount = this.getById(toId);
        if (donorAccount.isEmpty() || acceptorAccount.isEmpty() || donorAccount.get().amount() < amount) {
            return false;
        }
        return this.update(new Account(fromId, donorAccount.get().amount() - amount))
                && this.update(new Account(toId, acceptorAccount.get().amount() + amount));
    }
}