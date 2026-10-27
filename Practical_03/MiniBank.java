import java.util.Objects;

class Customer implements Cloneable {
    private final String customerId;
    private String name;
    private Address address;

    public static class Address {
        private final String line;
        private final String city;
        private final String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() { return line; }
        public String getCity() { return city; }
        public String getPincode() { return pincode; }

        @Override
        public String toString() {
            return line + ", " + city + " - " + pincode;
        }
    }

    public Customer(String customerId, String name, Address address) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
    }

    public Address getAddress() { return address; }

    @Override
    public Customer clone() {
        try {
            return (Customer) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Customer[" + customerId + " | " + name + " | Address: " + address + "]";
    }
}

class Account {
    private final String accountNumber;
    private final String ownerName;
    private final long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Rs. " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account account)) return false;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("=== MiniBank Practical 3: Object Methods & Nested Classes ===");
        Account a1 = new Account("AC0001", "Dhruv Bhoi", 4000);
        Account a2 = new Account("AC0001", "Dhruv Bhoi", 7500);
        Account a3 = new Account("AC0002", "Riya Patel", 6000);

        System.out.println("a1.toString() -> " + a1);
        System.out.println("a1.equals(a2) -> " + a1.equals(a2));
        System.out.println("a1.equals(a3) -> " + a1.equals(a3));

        Customer.Address addr = new Customer.Address("123 University Road", "Changa", "388421");
        Customer cust = new Customer("CUST101", "Dhruv Bhoi", addr);
        Customer clonedCust = cust.clone();
        System.out.println("\nOriginal: " + cust);
        System.out.println("Cloned  : " + clonedCust);
        System.out.println("cust instanceof Customer -> " + (cust instanceof Customer));
    }
}
