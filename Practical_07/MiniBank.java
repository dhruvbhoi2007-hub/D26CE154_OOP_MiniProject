import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {
    String message() default "must be > 0";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
}

class Account {
    @Id
    private String accountNumber;

    @Positive(message = "balance must be strictly positive (> 0)")
    private long balance;

    public Account(String accountNumber, long balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }
}

class AnnotationValidator {
    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Class<?> clazz = obj.getClass();

        for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.isAnnotationPresent(Positive.class)) {
                    Object val = f.get(obj);
                    if (val instanceof Number n && n.doubleValue() <= 0) {
                        Positive p = f.getAnnotation(Positive.class);
                        errors.add(f.getName() + ": " + p.message());
                    }
                }
            } catch (Exception e) {
                errors.add(f.getName() + ": validation failed");
            }
        }
        return errors.toArray(new String[0]);
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 7: Custom Annotations & Reflection ===");
        Account badAccount = new Account("AC0001", -500);
        String[] errors = AnnotationValidator.validate(badAccount);
        System.out.println("Validating account with negative balance:");
        for (String err : errors) {
            System.out.println("  Validation Error -> " + err);
        }

        Account goodAccount = new Account("AC0002", 5000);
        String[] goodErrors = AnnotationValidator.validate(goodAccount);
        System.out.println("\nValidating account with valid balance:");
        if (goodErrors.length == 0) {
            System.out.println("  Validation SUCCESS: No annotation errors found.");
        }
    }
}
