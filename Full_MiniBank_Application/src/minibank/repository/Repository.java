package minibank.repository;
import minibank.model.annotation.Id;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Repository<T> {
    private final Map<String, T> storage = new ConcurrentHashMap<>();

    public T save(T entity) {
        String key = extractId(entity);
        storage.put(key, entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) save(item);
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    public int count() {
        return storage.size();
    }

    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) return 0.0;
        double sum = 0;
        for (N v : values) sum += v.doubleValue();
        return sum / values.length;
    }

    private String extractId(T entity) {
        Class<?> curr = entity.getClass();
        while (curr != null && curr != Object.class) {
            for (Field f : curr.getDeclaredFields()) {
                if (f.isAnnotationPresent(Id.class)) {
                    f.setAccessible(true);
                    try {
                        return String.valueOf(f.get(entity));
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Cannot access @Id field", e);
                    }
                }
            }
            curr = curr.getSuperclass();
        }
        throw new IllegalArgumentException("No @Id field found on " + entity.getClass().getSimpleName());
    }
}
