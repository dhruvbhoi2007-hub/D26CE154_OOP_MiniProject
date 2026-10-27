import java.util.regex.Pattern;

class Validator {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9]\\d{9}$");
    private static final Pattern EMAIL_PATTERN  = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PAN_PATTERN    = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
    private static final Pattern IFSC_PATTERN   = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
    }
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    public static boolean isValidPan(String pan) {
        return pan != null && PAN_PATTERN.matcher(pan).matches();
    }
    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC_PATTERN.matcher(ifsc).matches();
    }
}

enum TransactionType { DEPOSIT, WITHDRAW, TRANSFER }

record Command(TransactionType type, String accountNumber, long amount) {}

class CommandParser {
    public static Command parse(String line) {
        if (line == null) throw new IllegalArgumentException("Line cannot be null");
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Expected 3 tokens: <TYPE> <ACC_NO> <AMOUNT>");
        }
        TransactionType type = TransactionType.valueOf(parts[0].toUpperCase());
        String accNo = parts[1];
        long amount = Long.parseLong(parts[2]);
        return new Command(type, accNo, amount);
    }
}

class StatementFormatter {
    public static String buildStatement(String accNo, String owner, long balance) {
        StringBuilder sb = new StringBuilder();
        sb.append("--------------------------------------\n");
        sb.append(" MINI-BANK ACCOUNT STATEMENT\n");
        sb.append("--------------------------------------\n");
        sb.append("Account Number : ").append(accNo).append("\n");
        sb.append("Account Holder : ").append(owner).append("\n");
        sb.append("Current Balance: Rs. ").append(balance).append("\n");
        sb.append("Status         : ACTIVE\n");
        sb.append("--------------------------------------");
        return sb.toString();
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 4: Validation & Command Parsing ===");
        System.out.println("isValidMobile('9876543210') -> " + Validator.isValidMobile("9876543210"));
        System.out.println("isValidEmail('dhruv@charusat.edu.in') -> " + Validator.isValidEmail("dhruv@charusat.edu.in"));
        System.out.println("isValidPan('ABCDE1234F')    -> " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("isValidIfsc('SBIN0001234')  -> " + Validator.isValidIfsc("SBIN0001234"));

        Command cmd = CommandParser.parse("DEPOSIT AC0001 500");
        System.out.println("\nParsed Command: Type=" + cmd.type() + ", Account=" + cmd.accountNumber() + ", Amount=" + cmd.amount());

        System.out.println("\n" + StatementFormatter.buildStatement("AC0001", "Dhruv Bhoi", 5500));
    }
}
