# MiniBank - Advanced Object-Oriented Programming (CEUE203)

**Subject**: Object-Oriented Programming (CEUE203)  
**Student**: Dhruv Bhoi  
**ID**: D26CE154  
**Semester**: 3rd Semester, 2026-27  

---

## 📌 Project Overview
**MiniBank** is a comprehensive, semester-long console banking application designed to progressively implement core and advanced Object-Oriented Programming paradigms in Java (JDK 17+).

This repository contains:
1. **Evolutionary Milestone Stages (`Practical_01` through `Practical_14`)**: Standalone implementations showing how MiniBank evolves practical by practical across the curriculum.
2. **Enterprise Capstone (`Full_MiniBank_Application`)**: A complete, layered, modular Java architecture bringing together all 14 practicals with custom annotations, generic repositories, thread-safe concurrency, reflection, NIO reporting, and automated unit testing.

---

## 📂 Repository Structure

```
D26CE154_OOP_MiniProject/
├── Practical_01/               # Practical 1: Console Menu Shell & BankInfo Record
├── Practical_02/               # Practical 2: Encapsulation & Account State
├── Practical_03/               # Practical 3: Object Methods & Nested Classes
├── Practical_04/               # Practical 4: Regex Validation & Command Record
├── Practical_05/               # Practical 5: Inheritance & Account Polymorphism
├── Practical_06/               # Practical 6: Interfaces, Lambdas & Rules
├── Practical_07/               # Practical 7: Custom Annotations & Reflection
├── Practical_08/               # Practical 8: Custom Checked Exception Hierarchy
├── Practical_09/               # Practical 9: Synchronized Concurrency & Workers
├── Practical_10/               # Practical 10: Managed Thread Pool & Deadlock Prevention
├── Practical_11/               # Practical 11: Binary Object Serialization & NIO Reporting
├── Practical_12/               # Practical 12: Generic Collections & Account Sorting
├── Practical_13/               # Practical 13: Generic Repository Pattern
├── Practical_14/               # Practical 14: Capstone Menu Integration
└── Full_MiniBank_Application/  # Production-Grade Modular Architecture
    ├── src/
    │   └── minibank/
    │       ├── model/annotation/  # @Id, @Positive, @MaxLength
    │       ├── model/             # Account, SavingsAccount, CurrentAccount, FixedDepositAccount, Customer
    │       ├── interfaces/        # Transactable, InterestBearing, Premium, WithdrawRule
    │       ├── exception/         # BankException, InsufficientFundsException, InvalidAmountException, AccountNotFoundException
    │       ├── util/              # Validator, AnnotationValidator, CommandParser, StatementFormatter
    │       ├── repository/        # Generic Repository<T>, AccountStore
    │       ├── service/           # BankService, TransactionProcessor, StatePersister, ReportGenerator, TransactionLog
    │       └── app/               # MiniBank (Interactive CLI), MiniBankTests (Automated Suite)
    ├── data/                      # Binary persistent state (accounts.dat)
    └── logs/                      # NIO transaction audit logs (transactions.log)
```

---

## 🚀 How to Run

### Running Any Individual Stage (e.g., Practical 14)
Navigate into the respective practical folder, compile, and execute:
```bash
cd Practical_14
javac MiniBank.java
java MiniBank
```

### Running the Full Capstone Application
Compile all source packages and launch the interactive CLI:
```bash
cd Full_MiniBank_Application

# Compile
javac -d bin $(find src -name "*.java")    # On Linux/macOS
# On Windows PowerShell:
# javac -d bin $(Get-ChildItem -Path src -Recurse -Filter "*.java" | ForEach-Object { $_.FullName })

# Launch Interactive Menu
java -cp bin minibank.app.MiniBank
```

### Running the Automated Integration Test Suite
To verify deposits, exception triggers, shortfall calculations, and deadlock-free concurrent transactions:
```bash
cd Full_MiniBank_Application
java -cp bin minibank.app.MiniBankTests
```

---

## 🛠️ Syllabus Mapping & Concepts Implemented

| Practical | Core OOP Concept | Implementation Details |
| :---: | :--- | :--- |
| **01** | **Class Fundamentals** | `BankInfo` read-only record, `MenuOption` enum, Scanner input, looping switch expression. |
| **02** | **Encapsulation & State** | Private fields, balance invariants, `Customer` (name, phone, address), `Account` operations. |
| **03** | **Object Methods** | `@Override toString()`, `equals()` and `hashCode()` on account IDs, static nested `Address` class, `clone()`. |
| **04** | **Strings & Regex** | `Validator` regex checking (`AC\d{4}` accounts, `\d{10}` phones), `Command` record, `CommandParser`. |
| **05** | **Inheritance & Polymorphism** | Abstract `Account` base, `SavingsAccount`, `CurrentAccount` (overdraft), `FixedDepositAccount` (lock). |
| **06** | **Interfaces & Lambdas** | `Transactable`, `InterestBearing`, `Premium` marker, `WithdrawRule` functional interface with lambdas. |
| **07** | **Annotations & Reflection** | Custom annotations `@Id`, `@Positive`, `@MaxLength`; runtime reflection validator. |
| **08** | **Exception Handling** | Custom checked `BankException` hierarchy; `InsufficientFundsException` with shortfall tracking. |
| **09** | **Concurrency & Thread Safety** | Synchronized operations; multithreaded `AccountWorker` threads simulating concurrent traffic. |
| **10** | **Thread Pools & Deadlock Prevention** | `ExecutorService` thread pool; deterministic lock ordering (`transferDeadlockFree`) preventing circular waits. |
| **11** | **Persistence & Modern NIO** | `StatePersister` binary object serialization; `ReportGenerator` auditing logs via Java NIO Stream API. |
| **12** | **Collections Framework** | `AccountStore` backed by `Map<String, Account>`; sorting by balance/account number using `Comparable` and `Comparator`. |
| **13** | **Generic Architecture** | Generic `Repository<T>` interface; `InMemoryRepository<T>` dynamically finding `@Id` fields via reflection. |
| **14** | **Capstone Assembly & Testing** | `BankService` layered coordinator, interactive CLI driving all features, automatic persistence, and test suite. |

---
