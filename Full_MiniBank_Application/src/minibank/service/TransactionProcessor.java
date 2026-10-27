package minibank.service;
import minibank.model.Account;
import minibank.exception.BankException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TransactionProcessor {
    private final ExecutorService pool = Executors.newFixedThreadPool(4, r -> {
        Thread t = new Thread(r);
        t.setDaemon(true);
        return t;
    });

    public void submit(Runnable task) {
        pool.execute(task);
    }

    public void stop() {
        pool.shutdown();
        try {
            pool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void transferDeadlockFree(Account a, Account b, long amount) throws BankException {
        Account first = a.getAccountNumber().compareTo(b.getAccountNumber()) < 0 ? a : b;
        Account second = (first == a) ? b : a;

        synchronized (first) {
            synchronized (second) {
                a.withdraw(amount);
                b.deposit(amount);
            }
        }
    }
}
