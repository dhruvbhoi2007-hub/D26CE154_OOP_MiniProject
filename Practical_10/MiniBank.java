import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Account {
    private final String accNo;
    private long balance;

    public Account(String accNo, long balance) {
        this.accNo = accNo;
        this.balance = balance;
    }

    public String getAccNo() { return accNo; }
    public long getBalance() { return balance; }
    public synchronized void deposit(long amt) { balance += amt; }
    public synchronized void withdraw(long amt) { balance -= amt; }
}

class TransactionProcessor {
    private final ExecutorService pool = Executors.newFixedThreadPool(4);

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
}

public class MiniBank {
    // Deadlock-free transfer locking lower accountNumber first
    public static void safeTransfer(Account a, Account b, long amount) {
        Account first = a.getAccNo().compareTo(b.getAccNo()) < 0 ? a : b;
        Account second = first == a ? b : a;

        synchronized (first) {
            synchronized (second) {
                a.withdraw(amount);
                b.deposit(amount);
                System.out.println("Safe transfer of Rs. " + amount + " from " + a.getAccNo() + " to " + b.getAccNo());
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 10: Managed Thread Pool & Deadlock Prevention ===");
        Account a1 = new Account("AC001", 10000);
        Account a2 = new Account("AC002", 10000);

        TransactionProcessor processor = new TransactionProcessor();

        // Submit batch of concurrent transfers
        for (int i = 0; i < 5; i++) {
            processor.submit(() -> safeTransfer(a1, a2, 100));
            processor.submit(() -> safeTransfer(a2, a1, 100));
        }

        processor.stop();
        System.out.println("\nBatch processed safely without deadlock.");
        System.out.println("AC001 Final Balance: Rs. " + a1.getBalance());
        System.out.println("AC002 Final Balance: Rs. " + a2.getBalance());
    }
}
