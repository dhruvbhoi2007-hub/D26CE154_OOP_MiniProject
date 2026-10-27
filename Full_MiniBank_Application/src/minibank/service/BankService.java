package minibank.service;

import minibank.model.*;
import minibank.repository.Repository;
import minibank.exception.*;
import minibank.util.StatementFormatter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BankService {
    private final Repository<Account> repository = new Repository<>();
    private final TransactionProcessor processor = new TransactionProcessor();
    private final Path dataPath = Paths.get("data", "accounts.dat");
    private final Path logPath = Paths.get("logs", "transactions.log");

    public BankService() {
        loadState();
    }

    public synchronized void openAccount(Account account) throws InvalidAmountException {
        if (account.getBalance() <= 0) {
            throw new InvalidAmountException("Opening balance must be > 0: " + account.getBalance());
        }
        repository.save(account);
        log("OPEN_ACCOUNT " + account.getAccountNumber() + " " + account.getBalance());
    }

    public synchronized void deposit(String accNo, long amount) throws BankException {
        Account acc = getAccount(accNo);
        acc.deposit(amount);
        log("DEPOSIT " + accNo + " " + amount);
    }

    public synchronized boolean withdraw(String accNo, long amount) throws BankException {
        Account acc = getAccount(accNo);
        boolean ok = acc.withdraw(amount);
        log("WITHDRAW " + accNo + " " + amount);
        return ok;
    }

    public void transfer(String fromAcc, String toAcc, long amount) throws BankException {
        Account a = getAccount(fromAcc);
        Account b = getAccount(toAcc);
        TransactionProcessor.transferDeadlockFree(a, b, amount);
        log("TRANSFER " + fromAcc + " " + toAcc + " " + amount);
    }

    public String getStatement(String accNo) throws AccountNotFoundException {
        Account acc = getAccount(accNo);
        return StatementFormatter.buildStatement(acc);
    }

    public List<Account> listAccounts() {
        return repository.findAll();
    }

    public Account getAccount(String accNo) throws AccountNotFoundException {
        Account a = repository.findById(accNo);
        if (a == null)
            throw new AccountNotFoundException("Account not found: " + accNo);
        return a;
    }

    public void runConcurrentBatch() {
        System.out.println("Executing thread-pool managed batch of 10 concurrent transactions...");
        List<Account> all = repository.findAll();
        if (all.size() >= 2) {
            Account a1 = all.get(0);
            Account a2 = all.get(1);
            for (int i = 0; i < 5; i++) {
                processor.submit(() -> {
                    try {
                        TransactionProcessor.transferDeadlockFree(a1, a2, 100);
                    } catch (Exception ignored) {
                    }
                });
                processor.submit(() -> {
                    try {
                        TransactionProcessor.transferDeadlockFree(a2, a1, 100);
                    } catch (Exception ignored) {
                    }
                });
            }
        }
    }

    public String generateAuditReport() {
        try {
            return ReportGenerator.generateDailyReport(Paths.get("logs"));
        } catch (IOException e) {
            return "Error reading logs: " + e.getMessage();
        }
    }

    public void saveState() {
        try {
            StatePersister.save(repository.findAll(), dataPath);
            System.out.println("Saved " + repository.count() + " accounts to " + dataPath);
        } catch (IOException e) {
            System.err.println("Could not save state: " + e.getMessage());
        }
        processor.stop();
    }

    public void shutdown() {
        processor.stop();
    }

    private void loadState() {
        try {
            List<Account> loaded = StatePersister.load(dataPath);
            for (Account a : loaded)
                repository.save(a);
            System.out.println("Restored " + loaded.size() + " accounts from persistent storage.");
        } catch (Exception e) {
            // Seed defaults if empty
            Account a1 = new SavingsAccount("AC0001", "Dhruv Bhoi", 25000, 1000);
            Account a2 = new CurrentAccount("AC0002", "Riya Patel", 18000, 5000);
            Account a3 = new FixedDepositAccount("AC0003", "Aarav Shah", 50000);
            repository.saveAll(a1, a2, a3);
        }
    }

    private void log(String entry) {
        try {
            TransactionLog.append(logPath, entry);
        } catch (IOException ignored) {
        }
    }
}
