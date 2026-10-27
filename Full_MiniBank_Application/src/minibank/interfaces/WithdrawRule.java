package minibank.interfaces;

@FunctionalInterface
public interface WithdrawRule {
    boolean allow(long balance, long amount);
}
