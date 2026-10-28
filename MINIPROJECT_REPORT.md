# MiniBank: Progressive Object-Oriented Banking System & Enterprise Capstone
## Academic Mini-Project Report (CEUE203: Object-Oriented Programming)

---

### **Student Information**
- **Student Name:** Dhruv Bhoi
- **Student ID:** D26CE154
- **Branch / Department:** Department of Computer Engineering
- **Faculty:** Faculty of Technology and Engineering (FTE)
- **Institution:** Charotar University of Science and Technology (CHARUSAT)
- **Academic Year:** 2026–27 | **Semester:** 3rd Semester (Odd)
- **GitHub Repository:** [https://github.com/dhruvbhoi2007-hub/D26CE154_OOP_MiniProject.git](https://github.com/dhruvbhoi2007-hub/D26CE154_OOP_MiniProject.git)
- **Submission Form:** [https://forms.gle/qh3yMuouwKyGLdwL6](https://forms.gle/qh3yMuouwKyGLdwL6)
- **Date of Submission:** October 28, 2026

---

## 📜 Certificate of Originality & Submission
This is to certify that the mini-project report entitled **"MiniBank: Progressive Object-Oriented Banking System & Enterprise Capstone"** is an authentic academic submission carried out by **Dhruv Bhoi (D26CE154)** in partial fulfillment of the requirements for **CEUE203: Object-Oriented Programming** at the Faculty of Technology and Engineering (FTE), CHARUSAT.

The project systematically demonstrates all 14 pedagogical milestones of the laboratory curriculum, culminating in a production-grade enterprise capstone architecture in Java (JDK 17+).

---

## 🎯 Executive Summary
**MiniBank** is an evolutionary console banking platform developed across a 14-milestone curriculum. The project combines two primary structural components:

1. **Evolutionary Milestone Directory (`Practical_01` to `Practical_14`):** Standalone implementations tracking how MiniBank progressively integrates core OOP concepts—from foundational records and encapsulation to custom annotations, runtime reflection, multithreading, deadlock avoidance, serialization, and generic repository patterns.
2. **Enterprise Capstone Application (`Full_MiniBank_Application`):** A clean, modular, multi-package Java architecture unifying all 14 practicals into an industrial-grade system featuring:
   - **Custom Annotations & Reflection:** `@Id`, `@Positive`, and `@MaxLength` with dynamic validation.
   - **Generic Data Access Layer:** `Repository<T>` with reflection-based primary key resolution.
   - **Thread Safety & Deadlock Prevention:** Concurrency engine with deterministic resource ordering breaking circular wait conditions during cross-account transfers.
   - **Persistence & Auditing:** Binary state serialization (`accounts.dat`) and high-performance Java NIO log stream analytics (`transactions.log`).
   - **Automated Verification:** 100% test pass rate across unit, exception, and concurrency suites.

---

## 🏗️ System Architecture & Engineering Design

```
+-----------------------------------------------------------------------------------+
|                             PRESENTATION LAYER (CLI)                              |
|      MiniBank (Interactive CLI Menu)      |      MiniBankTests (Automated Suite)  |
+-----------------------------------------+-----------------------------------------+
                                          | invokes
                                          v
+-----------------------------------------------------------------------------------+
|                                  SERVICE LAYER                                    |
|  BankService (Coordinator)               |  TransactionProcessor (ExecutorService)|
|  StatePersister (Object Serialization)   |  ReportGenerator (Java NIO Directory)  |
+-----------------------------------------+-----------------------------------------+
                                          | delegates
                                          v
+-----------------------------------------------------------------------------------+
|                             DATA ACCESS / REPOSITORY                              |
|  Repository<T> (Generic CRUD Engine)     |  AccountStore (Concurrent Map Storage) |
+-----------------------------------------+-----------------------------------------+
                                          | manages
                                          v
+-----------------------------------------------------------------------------------+
|                                DOMAIN MODEL LAYER                                 |
|  Account (Abstract Base)  <|--- SavingsAccount (Min Balance, 4% Interest)         |
|                           <|--- CurrentAccount (Overdraft Limit, 0% Interest)     |
|                           <|--- FixedDepositAccount (Lock Policy, 7% Interest)    |
|  Customer (Nested Address)|  BankInfo (Record)   |  Command (Parser Record)       |
+-----------------------------------------------------------------------------------+
|                     CROSS-CUTTING UTILITIES & METADATA INFRASTRUCTURE              |
|  @Id, @Positive, @MaxLength             |  AnnotationValidator (Reflection)       |
|  Validator (Regex Mobile/Email/PAN/IFSC)|  BankException Hierarchy (Shortfall)    |
+-----------------------------------------------------------------------------------+
```

### Domain Model & Polymorphism
- **`Account` (Abstract Base):** Implements `Transactable`, `InterestBearing`, `Comparable<Account>`, and `Serializable`. Encapsulates `accountNumber`, `ownerName`, and `balance` with thread-safe mutator methods.
- **`SavingsAccount`:** Enforces `minBalance` threshold on withdrawals. Yields 4.0% annual interest. Marked with the `Premium` marker interface.
- **`CurrentAccount`:** Permits overdraft up to an agreed `overdraftLimit`. Accrues 0.0% interest.
- **`FixedDepositAccount`:** Locked term deposit yielding 7.0% annual interest; prohibits withdrawals (`canWithdraw() -> false`).

---

## 🛠️ Complete Curricular Milestone Breakdown (Practicals 01 to 14)

| Practical | Core OOP Concept | Implementation Highlights in MiniBank |
| :---: | :--- | :--- |
| **01** | **Class Fundamentals** | `BankInfo` read-only record, `MenuOption` enum, Scanner CLI, modern switch expression loop. |
| **02** | **Encapsulation & State** | Private fields, balance invariants, `Customer` (name, phone, address), mutator validation guards. |
| **03** | **Object Methods & Nesting** | `@Override` `toString()`, `equals()` and `hashCode()` on account IDs, static nested `Address` class. |
| **04** | **Strings & Regex Validation** | Compiled regex patterns for mobile (`^[6-9]\d{9}$`), email, PAN, and IFSC; `Command` parser. |
| **05** | **Inheritance & Polymorphism** | Abstract `Account` base, specialized `SavingsAccount`, `CurrentAccount` (overdraft), `FixedDepositAccount`. |
| **06** | **Interfaces & Lambdas** | `Transactable`, `InterestBearing`, `Premium` marker, functional `WithdrawRule` with lambdas. |
| **07** | **Annotations & Reflection** | Custom `@Id`, `@Positive`, `@MaxLength`; runtime reflection engine (`AnnotationValidator`). |
| **08** | **Exception Handling** | Custom checked `BankException` hierarchy; `InsufficientFundsException` tracking exact monetary shortfall. |
| **09** | **Concurrency & Thread Safety** | Synchronized operations; multithreaded `AccountWorker` threads simulating concurrent traffic. |
| **10** | **Thread Pools & Deadlock Prevention** | `ExecutorService` pool; deterministic resource ordering (`transferDeadlockFree`) eliminating circular waits. |
| **11** | **Persistence & Java NIO** | `StatePersister` binary object serialization; `ReportGenerator` auditing logs via Java NIO Stream API. |
| **12** | **Collections Framework** | `AccountStore` backed by `Map<String, Account>`; sorting by balance/account number using `Comparable` and `Comparator`. |
| **13** | **Generic Architecture** | Generic `Repository<T>` interface; `InMemoryRepository<T>` dynamically extracting `@Id` via reflection. |
| **14** | **Capstone Assembly & Testing** | `BankService` layered coordinator, interactive CLI driving all features, automatic persistence, and test suite. |

---

## 💡 Key Innovations & Code Highlights

### 1. Deterministic Deadlock Prevention (`TransactionProcessor.java`)
```java
public static void transferDeadlockFree(Account a, Account b, long amount) throws BankException {
    // Deterministic lock ordering eliminates Dijkstra's circular wait condition
    Account first = a.getAccountNumber().compareTo(b.getAccountNumber()) < 0 ? a : b;
    Account second = (first == a) ? b : a;

    synchronized (first) {
        synchronized (second) {
            a.withdraw(amount);
            b.deposit(amount);
        }
    }
}
```

### 2. Reflection-Driven Generic Repository (`Repository.java`)
```java
public class Repository<T> {
    private final Map<String, T> storage = new ConcurrentHashMap<>();

    public T save(T entity) {
        String key = extractId(entity);
        storage.put(key, entity);
        return entity;
    }

    private String extractId(T entity) {
        Class<?> curr = entity.getClass();
        while (curr != null && curr != Object.class) {
            for (Field f : curr.getDeclaredFields()) {
                if (f.isAnnotationPresent(Id.class)) {
                    f.setAccessible(true);
                    try { return String.valueOf(f.get(entity)); }
                    catch (IllegalAccessException e) { throw new RuntimeException(e); }
                }
            }
            curr = curr.getSuperclass();
        }
        throw new IllegalArgumentException("No @Id field found on " + entity.getClass().getSimpleName());
    }
}
```

### 3. Java NIO Audit Stream Log Processing (`ReportGenerator.java`)
```java
try (DirectoryStream<Path> stream = Files.newDirectoryStream(logDir, "*.log")) {
    for (Path p : stream) {
        BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
        try (BufferedReader reader = Files.newBufferedReader(p)) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("DEPOSIT")) totalDeposits += extractAmount(line);
                else if (line.contains("WITHDRAW")) totalWithdrawals += extractAmount(line);
            }
        }
    }
}
```

---

## 🧪 Verification, Testing & Empirical Results

### Automated Integration Test Suite (`MiniBankTests.java`)
```text
====================================================
      RUNNING MINIBANK CAPSTONE TEST SUITE          
====================================================
Restored 3 accounts from persistent storage.
[PASS] testDepositIncreasesBalance
[PASS] testWithdrawalOverLimitThrowsException (Shortfall: 9971499)
[PASS] testNegativeDepositThrowsException (Deposit amount must be > 0: -500)
Executing thread-pool managed batch of 10 concurrent transactions...
[PASS] testConcurrentBatchExecution (Deadlock-free execution)

====================================================
MINIPROJECT TEST SUMMARY: 4 / 4 TESTS PASSED (100%)
====================================================
```

### Test Case Coverage Matrix
| Test ID | Test Scenario | Verified System Invariant | Result |
| :---: | :--- | :--- | :---: |
| **TC-01** | `testDepositIncreasesBalance` | Verifies deposit of Rs. 3000 increments balance atomically. | **PASSED** |
| **TC-02** | `testWithdrawalOverLimitThrowsException` | Verifies `InsufficientFundsException` and shortfall calculation. | **PASSED** |
| **TC-03** | `testNegativeDepositThrowsException` | Rejects negative amounts with `InvalidAmountException`. | **PASSED** |
| **TC-04** | `testConcurrentBatchExecution` | Executes 10 concurrent cross-account transfers with zero deadlocks. | **PASSED** |

---

## 🏁 How to Compile and Run

### 1. Run Automated Test Suite
```bash
cd Full_MiniBank_Application
javac -d bin $(Get-ChildItem -Path src -Recurse -Filter "*.java" | ForEach-Object { $_.FullName })
java -cp bin minibank.app.MiniBankTests
```

### 2. Launch Interactive Console Menu
```bash
java -cp bin minibank.app.MiniBank
```

---

## 📋 Submission Checklist & Links
- **Google Classroom Assignment:** Submitted formal document reports:
  - `D26CE154_MiniProject_Report.docx` (Word Document)
  - `D26CE154_MiniProject_Report.pdf` (21-page PDF Report)
- **Google Form Link:** [https://forms.gle/qh3yMuouwKyGLdwL6](https://forms.gle/qh3yMuouwKyGLdwL6)
- **Complete ZIP Archive:** `D26CE154_MiniProject.zip`
- **Git Repository Link:** [https://github.com/dhruvbhoi2007-hub/D26CE154_OOP_MiniProject.git](https://github.com/dhruvbhoi2007-hub/D26CE154_OOP_MiniProject.git)
