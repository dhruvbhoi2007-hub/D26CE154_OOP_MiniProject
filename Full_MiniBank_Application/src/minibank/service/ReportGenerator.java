package minibank.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class ReportGenerator {
    public static String generateDailyReport(Path logDir) throws IOException {
        StringBuilder report = new StringBuilder();
        report.append("====================================================\n");
        report.append("           MINIBANK END-OF-DAY AUDIT REPORT         \n");
        report.append("====================================================\n");

        if (!Files.exists(logDir)) {
            report.append("No log files located in: ").append(logDir).append("\n");
            return report.toString();
        }

        long totalDeposits = 0;
        long totalWithdrawals = 0;
        int fileCount = 0;

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.log")) {
            for (Path p : stream) {
                fileCount++;
                BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
                report.append(String.format("Log File: %-18s | Size: %d bytes | Modified: %s\n",
                    p.getFileName(), attrs.size(), attrs.lastModifiedTime()));

                try (BufferedReader reader = Files.newBufferedReader(p)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String upper = line.toUpperCase();
                        if (upper.contains("DEPOSIT")) {
                            totalDeposits += extractAmount(line);
                        } else if (upper.contains("WITHDRAW")) {
                            totalWithdrawals += extractAmount(line);
                        }
                    }
                }
            }
        }

        report.append("----------------------------------------------------\n");
        report.append("Total Log Files Scanned: ").append(fileCount).append("\n");
        report.append("Total Deposited Funds  : Rs. ").append(totalDeposits).append("\n");
        report.append("Total Withdrawn Funds  : Rs. ").append(totalWithdrawals).append("\n");
        report.append("Net Cash Movement      : Rs. ").append(totalDeposits - totalWithdrawals).append("\n");
        report.append("====================================================");
        return report.toString();
    }

    private static long extractAmount(String line) {
        String[] tokens = line.split("\\s+");
        for (String t : tokens) {
            try {
                return Long.parseLong(t);
            } catch (NumberFormatException ignored) {}
        }
        return 0;
    }
}
