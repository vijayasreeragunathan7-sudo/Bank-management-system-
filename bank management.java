import java.util.*;

class Account {
    int id;
    String customerName;
    double balance;

    Account(int id, String customerName, double balance) {
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
    }
}

public class BankConsoleApp {

    static Map<Integer, Account> accounts = new HashMap<>();

    // Transfer money from one account to another
    static void transfer(int fromId, int toId, double amount) {

        Account from = accounts.get(fromId);
        Account to = accounts.get(toId);

        // Check both accounts exist
        if (from == null || to == null) {
            System.out.println("Transfer failed: Account not found");
            return;
        }

        // Check sufficient balance
        if (from.balance < amount) {
            System.out.println("Transfer failed: Insufficient balance");
            return;
        }

        // Atomic transfer
        from.balance -= amount;
        to.balance += amount;

        System.out.println("Transfer successful");
    }

    static void displayAccounts() {
        for (Account a : accounts.values()) {
            System.out.println(
                a.id + " - " + a.customerName +
                " - Balance: " + a.balance
            );
        }
    }

    public static void main(String[] args) {

        accounts.put(101, new Account(101, "Arun", 5000));
        accounts.put(102, new Account(102, "Kumar", 3000));

        System.out.println("Before Transfer:");
        displayAccounts();

        transfer(101, 102, 1000);

        System.out.println("\nAfter Transfer:");
        displayAccounts();

        // Test invalid account
        System.out.println("\nInvalid Transfer:");
        transfer(101, 999, 500);
    }
}
