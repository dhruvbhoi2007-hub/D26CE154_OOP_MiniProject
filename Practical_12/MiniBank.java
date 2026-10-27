import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

class Account implements Comparable<Account> {
    private final String accNo;
    private final String owner;
    private final long balance;

    public Account(String accNo, String owner, long balance) {
        this.accNo = accNo;
        this.owner = owner;
        this.balance = balance;
    }

    public String getAccNo() { return accNo; }
    public long getBalance() { return balance; }

    @Override
    public int compareTo(Account other) {
        return this.accNo.compareTo(other.accNo);
    }

    @Override
    public String toString() {
        return accNo + " (" + owner + "): Rs. " + balance;
    }
}

class AccountStore {
    private final Map<String, Account> map = new HashMap<>();
    private final List<String> history = new ArrayList<>();

    public void add(Account acc) {
        map.put(acc.getAccNo(), acc);
        history.add("Added " + acc.getAccNo());
    }

    public Account get(String accNo) { return map.get(accNo); }
    public Collection<Account> getAll() { return map.values(); }

    public List<Account> sortedByBalance() {
        List<Account> list = new ArrayList<>(map.values());
        list.sort((a, b) -> Long.compare(b.getBalance(), a.getBalance())); // Descending
        return list;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 12: Collections Framework ===");
        AccountStore store = new AccountStore();
        store.add(new Account("AC002", "Riya Patel", 12000));
        store.add(new Account("AC001", "Dhruv Bhoi", 45000));
        store.add(new Account("AC003", "Aarav Shah", 28000));

        System.out.println("Lookup by Key AC001 -> " + store.get("AC001"));

        System.out.println("\nAccounts Sorted By Balance (Highest First):");
        for (Account a : store.sortedByBalance()) {
            System.out.println("  " + a);
        }
    }
}
