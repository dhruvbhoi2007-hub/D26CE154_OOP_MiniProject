package minibank.service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

public class TransactionLog {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void append(Path file, String logEntry) throws IOException {
        String stamped = "[" + LocalDateTime.now().format(FMT) + "] " + logEntry + System.lineSeparator();
        if (!Files.exists(file.getParent())) {
            Files.createDirectories(file.getParent());
        }
        Files.write(file, Collections.singletonList(stamped.trim()),
            StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}
