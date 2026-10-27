import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@interface Id {}

class Repository<T> {
    private final Map<String, T> storage = new HashMap<>();

    public T save(T entity) {
        String id = extractId(entity);
        storage.put(id, entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) save(item);
    }

    public T findById(String id) { return storage.get(id); }
    public List<T> findAll() { return new ArrayList<>(storage.values()); }
    public boolean delete(String id) { return storage.remove(id) != null; }

    private String extractId(T entity) {
        for (Field f : entity.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                f.setAccessible(true);
                try {
                    return String.valueOf(f.get(entity));
                } catch (Exception e) {
                    throw new RuntimeException("Cannot access @Id field", e);
                }
            }
        }
        throw new IllegalArgumentException("No @Id field found on " + entity.getClass().getSimpleName());
    }

    public static <N extends Number> double average(N[] values) {
        double sum = 0;
        for (N v : values) sum += v.doubleValue();
        return sum / values.length;
    }
}

class Account {
    @Id
    private final String accountNumber;
    private final String ownerName;
    private final long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Rs. " + balance;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 13: Generic Repository Pattern ===");
        Repository<Account> repo = new Repository<>();
        Account a1 = new Account("AC101", "Dhruv Bhoi", 25000);
        Account a2 = new Account("AC102", "Riya Patel", 40000);
        repo.saveAll(a1, a2);

        System.out.println("Fetched by ID 'AC101': " + repo.findById("AC101"));
        System.out.println("Total stored accounts: " + repo.findAll().size());

        Integer[] scores = { 88, 92, 95 };
        System.out.println("Average of scores: " + Repository.average(scores));
    }
}
