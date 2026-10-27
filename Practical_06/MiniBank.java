interface Transactable {
    void deposit(long amount);
    boolean withdraw(long amount);
}

interface InterestBearing {
    double interestRate();
    long getBalance();

    default double yearlyInterest() {
        return (getBalance() * interestRate()) / 100.0;
    }
}

@FunctionalInterface
interface WithdrawRule {
    boolean allow(long balance, long amount);
}

interface Premium {}

class StandardAccount implements Transactable, InterestBearing, Premium {
    private final String accNo;
    private long balance;

    public StandardAccount(String accNo, long balance) {
        this.accNo = accNo;
        this.balance = balance;
    }

    @Override
    public void deposit(long amount) { balance += amount; }

    @Override
    public boolean withdraw(long amount) {
        if (balance >= amount) { balance -= amount; return true; }
        return false;
    }

    @Override
    public double interestRate() { return 5.5; }

    @Override
    public long getBalance() { return balance; }

    public String getAccNo() { return accNo; }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 6: Interfaces & Lambdas ===");
        StandardAccount acc = new StandardAccount("AC777", 20000);
        System.out.println("Account " + acc.getAccNo() + " Yearly Interest: Rs. " + acc.yearlyInterest());
        System.out.println("Is Premium account? " + (acc instanceof Premium));

        WithdrawRule anonRule = new WithdrawRule() {
            @Override
            public boolean allow(long balance, long amount) {
                return amount <= 10000 && balance >= amount;
            }
        };
        WithdrawRule lambdaRule = (balance, amount) -> amount <= 10000 && balance >= amount;

        System.out.println("Anon rule allow Rs. 8000: " + anonRule.allow(acc.getBalance(), 8000));
        System.out.println("Lambda rule allow Rs. 15000: " + lambdaRule.allow(acc.getBalance(), 15000));
    }
}
