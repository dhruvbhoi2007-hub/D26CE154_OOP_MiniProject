class Customer {
    private static long customerCounter = 100;
    private final String customerId;
    private String name;
    private String email;
    private String mobile;

    public Customer(String name, String email, String mobile) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    private static synchronized String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
}

class Account {
    private static long accountCounter = 0;
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    public Account(String ownerName, long balance) {
        accountCounter++;
        this.accountNumber = String.format("AC%04d", accountCounter);
        this.ownerName = ownerName;
        this.balance = Math.max(0, balance);
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited Rs. " + amount + " to " + accountNumber + ". New Balance: " + balance);
        }
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrew Rs. " + amount + " from " + accountNumber + ". New Balance: " + balance);
            return true;
        }
        System.out.println("Withdrawal of Rs. " + amount + " FAILED on " + accountNumber + " (Insufficient Funds).");
        return false;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 2: Encapsulation & Account State ===");
        Customer c1 = new Customer("Dhruv Bhoi", "dhruv@charusat.edu.in", "9876543210");
        System.out.println("Customer Created: ID=" + c1.getCustomerId() + ", Name=" + c1.getName());

        Account[] accounts = new Account[3];
        accounts[0] = new Account("Dhruv Bhoi", 5000);
        accounts[1] = new Account("Riya Patel", 3000);
        accounts[2] = new Account("Aarav Shah");

        System.out.println("\nExecuting Operations on " + accounts[0].getAccountNumber() + ":");
        accounts[0].deposit(2000);
        accounts[0].withdraw(3000);
        accounts[0].withdraw(10000); // Should fail

        System.out.println("\nFinal Balances:");
        for (Account acc : accounts) {
            System.out.println("  " + acc.getAccountNumber() + " (" + acc.getOwnerName() + "): Rs. " + acc.getBalance());
        }
    }
}
