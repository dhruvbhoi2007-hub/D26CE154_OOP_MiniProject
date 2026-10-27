import java.util.Scanner;

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

public class MiniBank {
    public static void main(String[] args) {
        BankInfo bankInfo = new BankInfo("CHARUSAT FTE Bank", "Changa Branch");
        System.out.println("==========================================");
        System.out.println(" Welcome to " + bankInfo.name() + " (" + bankInfo.branch() + ")");
        System.out.println("==========================================");

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        // Demonstration execution
        int[] demoChoices = { 2, 5 };
        int step = 0;

        while (running && step < demoChoices.length) {
            System.out.println("\n--- Banking Menu ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Exit Application");
            
            int choice = demoChoices[step++];
            System.out.println("Enter choice: " + choice);

            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            switch (option) {
                case OPEN_ACCOUNT -> System.out.println("-> [OPEN ACCOUNT]: Feature placeholder - implemented in later lab.");
                case DEPOSIT -> System.out.println("-> [DEPOSIT]: Feature placeholder - implemented in later lab.");
                case WITHDRAW -> System.out.println("-> [WITHDRAW]: Feature placeholder - implemented in later lab.");
                case TRANSFER -> System.out.println("-> [TRANSFER]: Feature placeholder - implemented in later lab.");
                case EXIT -> {
                    System.out.println("Thank you for using " + bankInfo.name() + ". Goodbye!");
                    running = false;
                }
            }
        }
        sc.close();
    }
}
