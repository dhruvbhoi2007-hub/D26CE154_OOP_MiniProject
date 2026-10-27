package minibank.util;
import minibank.model.annotation.Positive;
import minibank.model.annotation.MaxLength;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AnnotationValidator {
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
                if (f.isAnnotationPresent(MaxLength.class)) {
                    Object val = f.get(obj);
                    if (val != null) {
                        MaxLength m = f.getAnnotation(MaxLength.class);
                        if (val.toString().length() > m.value()) {
                            errors.add(f.getName() + " length exceeds " + m.value());
                        }
                    }
                }
            } catch (IllegalAccessException ignored) {}
        }
        return errors.toArray(new String[0]);
    }
}
