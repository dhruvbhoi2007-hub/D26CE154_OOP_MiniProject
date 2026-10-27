class Account {
    private final String accNo;
    private long balance = 0;

    public Account(String accNo) { this.accNo = accNo; }

    public synchronized void deposit(long amount) {
        balance += amount;
    }

    public synchronized boolean withdraw(long amount) {
        if (balance >= amount) { balance -= amount; return true; }
        return false;
    }

    public long getBalance() { return balance; }
}

class AccountWorker implements Runnable {
    private final Account account;
    private final int times;
    private final long amount;

    public AccountWorker(Account account, int times, long amount) {
        this.account = account;
        this.times = times;
        this.amount = amount;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            account.deposit(amount);
        }
    }
}

public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== MiniBank Practical 9: Thread Safety & Concurrency ===");
        Account acc = new Account("AC100");
        int threadCount = 10;
        int depositsPerThread = 1000;
        long amountPerDeposit = 1;

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(new AccountWorker(acc, depositsPerThread, amountPerDeposit), "WorkerThread-" + (i + 1));
            threads[i].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        System.out.println("Expected Balance: " + (threadCount * depositsPerThread * amountPerDeposit));
        System.out.println("Actual Balance  : " + acc.getBalance() + " (Synchronized: Thread-Safe!)");
    }
}
