class BankException extends Exception {
    public BankException(String message) { super(message); }
}

class InsufficientFundsException extends BankException {
    private final long shortfall;
    public InsufficientFundsException(String message, long shortfall) {
        super(message);
        this.shortfall = shortfall;
    }
    public long getShortfall() { return shortfall; }
}

class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String message) { super(message); }
}

class InvalidAmountException extends BankException {
    public InvalidAmountException(String message) { super(message); }
}

class Account {
    private final String accNo;
    private long balance;

    public Account(String accNo, long balance) {
        this.accNo = accNo;
        this.balance = balance;
    }

    public void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be > 0: " + amount);
        balance += amount;
    }

    public void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be > 0: " + amount);
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient balance in " + accNo, amount - balance);
        }
        balance -= amount;
    }

    public void transfer(Account to, long amount) throws BankException {
        try {
            this.withdraw(amount);
            to.deposit(amount);
            System.out.println("Transferred Rs. " + amount + " from " + accNo + " to " + to.accNo);
        } finally {
            System.out.println("[Audit] Transfer attempt finished for " + accNo);
        }
    }

    public long getBalance() { return balance; }
    public String getAccNo() { return accNo; }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 8: Custom Exception Hierarchy ===");
        Account a1 = new Account("AC001", 1000);
        Account a2 = new Account("AC002", 500);

        try {
            System.out.println("Attempting withdrawal of Rs. 5000 from AC001 (balance 1000):");
            a1.withdraw(5000);
        } catch (InsufficientFundsException e) {
            System.out.println("  [Caught] Withdrawal failed: Short by Rs. " + e.getShortfall());
        } catch (InvalidAmountException e) {
            System.out.println("  [Caught] " + e.getMessage());
        }

        try {
            System.out.println("\nExecuting Transfer of Rs. 400 from AC001 to AC002:");
            a1.transfer(a2, 400);
            System.out.println("AC001 Balance: " + a1.getBalance() + " | AC002 Balance: " + a2.getBalance());
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage());
        }
    }
}
