package minibank.repository;
import minibank.model.Account;
import java.util.*;

public class AccountStore {
    private final Repository<Account> repo = new Repository<>();
    private final List<String> history = new ArrayList<>();

    public void add(Account acc) {
        repo.save(acc);
        history.add("Added account " + acc.getAccountNumber());
    }

    public Account get(String accNo) { return repo.findById(accNo); }
    public List<Account> getAll() { return repo.findAll(); }

    public List<Account> sortedByBalance() {
        List<Account> list = repo.findAll();
        list.sort((a, b) -> Long.compare(b.getBalance(), a.getBalance()));
        return list;
    }

    public List<String> getHistory() { return Collections.unmodifiableList(history); }
}
