import java.io.*;
import java.nio.file.*;
import java.util.Arrays;

class Account implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String accNo;
    private final String owner;
    private long balance;

    public Account(String accNo, String owner, long balance) {
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accNo + " | " + owner + " | Rs. " + balance;
    }
}

class StatePersister {
    public static void save(Account[] accounts, Path file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(accounts);
        }
    }

    public static Account[] load(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            return (Account[]) ois.readObject();
        }
    }
}

public class MiniBank {
    public static void main(String[] args) throws Exception {
        System.out.println("=== MiniBank Practical 11: Persistence & NIO Reporting ===");
        Path dataFile = Paths.get("minibank_accounts.dat");

        Account[] original = {
            new Account("AC001", "Dhruv Bhoi", 15000),
            new Account("AC002", "Riya Patel", 22000),
            new Account("AC003", "Aarav Shah", 31000)
        };

        StatePersister.save(original, dataFile);
        System.out.println("Saved " + original.length + " accounts to file.");

        Account[] loaded = StatePersister.load(dataFile);
        System.out.println("Reloaded accounts from file:");
        for (Account a : loaded) {
            System.out.println("  " + a);
        }

        Files.deleteIfExists(dataFile);
    }
}
