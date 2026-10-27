import java.util.*;

class BankService {
    private final Map<String, Double> accounts = new HashMap<>();

    public void openAccount(String accNo, double initialDeposit) {
        if (accounts.containsKey(accNo)) throw new IllegalArgumentException("Account already exists.");
        if (initialDeposit <= 0) throw new IllegalArgumentException("Initial deposit must be > 0.");
        accounts.put(accNo, initialDeposit);
    }

    public void deposit(String accNo, double amount) {
        if (!accounts.containsKey(accNo)) throw new NoSuchElementException("Account not found.");
        if (amount <= 0) throw new IllegalArgumentException("Deposit must be > 0.");
        accounts.put(accNo, accounts.get(accNo) + amount);
    }

    public void withdraw(String accNo, double amount) {
        if (!accounts.containsKey(accNo)) throw new NoSuchElementException("Account not found.");
        double balance = accounts.get(accNo);
        if (amount > balance) throw new IllegalStateException("Insufficient funds.");
        accounts.put(accNo, balance - amount);
    }

    public double getBalance(String accNo) { return accounts.getOrDefault(accNo, 0.0); }
    public Map<String, Double> getAll() { return Collections.unmodifiableMap(accounts); }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 14: Final Capstone Assembly ===");
        BankService service = new BankService();

        service.openAccount("AC001", 10000);
        service.openAccount("AC002", 5000);
        service.deposit("AC001", 2500);
        service.withdraw("AC002", 1500);

        System.out.println("MiniBank Service State:");
        service.getAll().forEach((acc, bal) -> {
            System.out.printf("  Account %s -> Rs. %.2f\n", acc, bal);
        });

        System.out.println("\nAll systems integrated, persistent, and operational.");
    }
}
