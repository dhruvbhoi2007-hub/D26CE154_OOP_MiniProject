package minibank.app;
import minibank.model.*;
import minibank.service.BankService;
import minibank.exception.BankException;
import java.util.Scanner;

public class MiniBank {
    public static void main(String[] args) {
        BankInfo bankInfo = new BankInfo("CHARUSAT FTE MiniBank", "Changa Campus");
        System.out.println("====================================================");
        System.out.println("   Welcome to " + bankInfo.name());
        System.out.println("   Branch: " + bankInfo.branch());
        System.out.println("====================================================");

        BankService service = new BankService();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        // Automated demonstration run if no terminal attached
        int[] demoCommands = { 2, 3, 5, 6, 8 };
        int step = 0;

        while (running) {
            System.out.println("\n------------- MAIN MENU -------------");
            for (MenuOption opt : MenuOption.values()) {
                System.out.printf("  %d. %s\n", opt.getId(), opt.getLabel());
            }
            System.out.print("Select an option (1-8): ");

            int choice;
            if (sc.hasNextInt()) {
                choice = sc.nextInt();
            } else if (step < demoCommands.length) {
                choice = demoCommands[step++];
                System.out.println(choice);
            } else {
                break;
            }

            MenuOption option = MenuOption.fromChoice(choice);
            if (option == null) {
                System.out.println("Invalid option. Please try again.");
                continue;
            }

            try {
                switch (option) {
                    case OPEN_ACCOUNT -> {
                        Account acc = new SavingsAccount("AC" + System.currentTimeMillis() % 10000, "New Customer", 5000, 1000);
                        service.openAccount(acc);
                        System.out.println("Successfully opened: " + acc);
                    }
                    case DEPOSIT -> {
                        System.out.println("Executing Deposit Rs. 2000 to AC0001...");
                        service.deposit("AC0001", 2000);
                        System.out.println("Deposit successful!");
                    }
                    case WITHDRAW -> {
                        System.out.println("Executing Withdrawal Rs. 1500 from AC0001...");
                        service.withdraw("AC0001", 1500);
                        System.out.println("Withdrawal successful!");
                    }
                    case TRANSFER -> {
                        System.out.println("Executing Transfer Rs. 500 from AC0001 to AC0002...");
                        service.transfer("AC0001", "AC0002", 500);
                        System.out.println("Transfer completed!");
                    }
                    case STATEMENT -> {
                        System.out.println("\n" + service.getStatement("AC0001"));
                    }
                    case GENERATE_REPORT -> {
                        System.out.println("\n" + service.generateAuditReport());
                    }
                    case BATCH_TRANSFER -> {
                        service.runConcurrentBatch();
                    }
                    case EXIT -> {
                        System.out.println("\nExiting MiniBank. Persisting state to disk...");
                        service.saveState();
                        System.out.println("Goodbye!");
                        running = false;
                    }
                }
            } catch (BankException e) {
                System.out.println("Banking Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
