package minibank.model;
import minibank.model.annotation.Id;
import minibank.model.annotation.Positive;
import minibank.interfaces.Transactable;
import minibank.interfaces.InterestBearing;
import minibank.exception.InsufficientFundsException;
import minibank.exception.InvalidAmountException;
import java.io.Serializable;
import java.util.Objects;

public abstract class Account implements Transactable, InterestBearing, Comparable<Account>, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    protected final String accountNumber;
    protected final String ownerName;

    @Positive(message = "balance must be strictly positive (> 0)")
    protected long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be > 0: " + amount);
        this.balance += amount;
    }

    @Override
    public synchronized boolean withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be > 0: " + amount);
        if (!canWithdraw(amount)) {
            throw new InsufficientFundsException("Insufficient funds on " + accountNumber, amount - balance);
        }
        this.balance -= amount;
        return true;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }

    @Override
    public long getBalance() { return balance; }

    @Override
    public int compareTo(Account other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return String.format("%s | %-15s | Rs. %-10d | %s (%.1f%%)",
            accountNumber, ownerName, balance, getClass().getSimpleName(), interestRate());
    }
}
