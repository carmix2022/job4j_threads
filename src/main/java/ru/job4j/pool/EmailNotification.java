package ru.job4j.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {

    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void addAndStartTask(Runnable task) {
        pool.submit(task);
    }

    public void emailTo(User user) {
        String subject = String.format("Notification %s to email %s", user.getUsername(), user.getEmail());
        String body = String.format("Add a new event to %s", user.getUsername());
        send(subject, body, user.getEmail());
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) { }

    public static void main(String[] args) {
        EmailNotification en = new EmailNotification();
        User user = new User("Vasya", "q@q.ru");
        en.addAndStartTask(() -> en.emailTo(user));
    }
}
