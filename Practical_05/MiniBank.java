abstract class Account {
    protected final String accountNumber;
    protected final String ownerName;
    protected long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
}

class SavingsAccount extends Account {
    private final long minBalance;

    public SavingsAccount(String accountNumber, String ownerName, long balance, long minBalance) {
        super(accountNumber, ownerName, balance);
        this.minBalance = minBalance;
    }

    @Override
    public double interestRate() { return 4.0; }

    @Override
    public boolean canWithdraw(long amount) {
        return (balance - amount) >= minBalance;
    }
}

class CurrentAccount extends Account {
    private final long overdraftLimit;

    public CurrentAccount(String accountNumber, String ownerName, long balance, long overdraftLimit) {
        super(accountNumber, ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
    public double interestRate() { return 0.0; }

    @Override
    public boolean canWithdraw(long amount) {
        return (balance - amount) >= -overdraftLimit;
    }
}

class FixedDepositAccount extends Account {
    public FixedDepositAccount(String accountNumber, String ownerName, long balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public double interestRate() { return 7.0; }

    @Override
    public boolean canWithdraw(long amount) { return false; }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 5: Inheritance & Polymorphism ===");
        Account[] accounts = {
            new SavingsAccount("SA001", "Dhruv Bhoi", 10000, 1000),
            new CurrentAccount("CA001", "Tech Ventures", 25000, 50000),
            new FixedDepositAccount("FD001", "Retirement Fund", 100000)
        };

        for (Account a : accounts) {
            System.out.printf("%s (%s): Interest Rate = %.1f%% | Can withdraw 5000: %b\n",
                a.getAccountNumber(), a.getClass().getSimpleName(), a.interestRate(), a.canWithdraw(5000));
        }
    }
}
