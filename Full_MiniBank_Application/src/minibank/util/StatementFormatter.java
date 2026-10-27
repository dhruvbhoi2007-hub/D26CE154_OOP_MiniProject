package minibank.util;
import minibank.model.Account;

public class StatementFormatter {
    public static String buildStatement(Account account) {
        StringBuilder sb = new StringBuilder();
        sb.append("====================================================\n");
        sb.append("               ACCOUNT STATEMENT                   \n");
        sb.append("====================================================\n");
        sb.append("Account Number : ").append(account.getAccountNumber()).append("\n");
        sb.append("Account Holder : ").append(account.getOwnerName()).append("\n");
        sb.append("Account Type   : ").append(account.getClass().getSimpleName()).append("\n");
        sb.append("Interest Rate  : ").append(account.interestRate()).append("% per annum\n");
        sb.append("Current Balance: Rs. ").append(account.getBalance()).append("\n");
        sb.append("Status         : ACTIVE\n");
        sb.append("====================================================");
        return sb.toString();
    }
}
