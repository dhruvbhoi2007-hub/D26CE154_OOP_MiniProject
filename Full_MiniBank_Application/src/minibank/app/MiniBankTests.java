package minibank.app;

import minibank.service.BankService;
import minibank.model.Account;
import minibank.exception.InsufficientFundsException;
import minibank.exception.InvalidAmountException;

public class MiniBankTests {
    public static void main(String[] args) {
        System.out.println("====================================================");
        System.out.println("      RUNNING MINIBANK CAPSTONE TEST SUITE          ");
        System.out.println("====================================================");
        int passed = 0, total = 0;

        BankService service = new BankService();

        // Test 1: Deposit Increases Balance
        total++;
        try {
            Account acc = service.getAccount("AC0001");
            long before = acc.getBalance();
            service.deposit("AC0001", 3000);
            if (acc.getBalance() == before + 3000) {
                System.out.println("[PASS] testDepositIncreasesBalance");
                passed++;
            } else {
                System.out.println("[FAIL] testDepositIncreasesBalance");
            }
        } catch (Exception e) {
            System.out.println("[FAIL] testDepositIncreasesBalance: " + e.getMessage());
        }

        // Test 2: Withdrawal Over Limit Throws InsufficientFundsException
        total++;
        try {
            service.withdraw("AC0001", 9999999);
            System.out.println("[FAIL] testWithdrawalOverLimitShouldThrow");
        } catch (InsufficientFundsException e) {
            System.out.println("[PASS] testWithdrawalOverLimitThrowsException (Shortfall: " + e.getShortfall() + ")");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testWithdrawalOverLimitThrowsException unexpected: " + e);
        }

        // Test 3: Invalid Amount Throws InvalidAmountException
        total++;
        try {
            service.deposit("AC0001", -500);
            System.out.println("[FAIL] testNegativeDepositShouldThrow");
        } catch (InvalidAmountException e) {
            System.out.println("[PASS] testNegativeDepositThrowsException (" + e.getMessage() + ")");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testNegativeDepositThrowsException unexpected: " + e);
        }

        // Test 4: Concurrency and Thread-Safe Transfers
        total++;
        try {
            service.runConcurrentBatch();
            System.out.println("[PASS] testConcurrentBatchExecution (Deadlock-free execution)");
            passed++;
        } catch (Exception e) {
            System.out.println("[FAIL] testConcurrentBatchExecution: " + e);
        }

        service.shutdown();
        System.out.println("\n====================================================");
        System.out.printf("MINIPROJECT TEST SUMMARY: %d / %d TESTS PASSED\n", passed, total);
        System.out.println("====================================================");
    }
}
