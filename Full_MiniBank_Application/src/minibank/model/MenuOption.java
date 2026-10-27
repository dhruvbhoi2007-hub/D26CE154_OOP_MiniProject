package minibank.model;

public enum MenuOption {
    OPEN_ACCOUNT(1, "Open Account"),
    DEPOSIT(2, "Deposit Funds"),
    WITHDRAW(3, "Withdraw Funds"),
    TRANSFER(4, "Transfer Funds"),
    STATEMENT(5, "View Statement"),
    GENERATE_REPORT(6, "Generate NIO Audit Report"),
    BATCH_TRANSFER(7, "Run Safe Concurrent Batch"),
    EXIT(8, "Exit & Persist State");

    private final int id;
    private final String label;

    MenuOption(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() { return id; }
    public String getLabel() { return label; }

    public static MenuOption fromChoice(int choice) {
        for (MenuOption opt : values()) {
            if (opt.id == choice) return opt;
        }
        return null;
    }
}
