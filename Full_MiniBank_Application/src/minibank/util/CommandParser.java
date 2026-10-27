package minibank.util;
import minibank.model.Command;
import minibank.model.TransactionType;

public class CommandParser {
    public static Command parse(String line) {
        if (line == null) throw new IllegalArgumentException("Input line cannot be null.");
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Expected format: <ACTION> <ACC_NO> <AMOUNT>");
        }
        TransactionType type = TransactionType.valueOf(parts[0].toUpperCase());
        String accNo = parts[1];
        long amount = Long.parseLong(parts[2]);
        return new Command(type, accNo, amount);
    }
}
