package minibank.service;
import minibank.model.Account;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class StatePersister {
    public static void save(List<Account> accounts, Path file) throws IOException {
        if (file.getParent() != null) {
            Files.createDirectories(file.getParent());
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(accounts);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Account> load(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            return (List<Account>) ois.readObject();
        }
    }
}
