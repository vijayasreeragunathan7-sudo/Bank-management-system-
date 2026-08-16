import java.util.*;

class Account {
    int id;
    String customerName;
    double balance;

    List<Transaction> transactions = new ArrayList<>();

    Account(int id, String customerName, double balance) {
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
    }

    void display() {
        System.out.println("Account ID   : " + id);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Balance      : " + balance);
    }
}

class Transaction {
    int fromId;
    int toId;
    double amount;

    Transaction(int fromId, int toId, double amount) {
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
    }
}

public class BankConsoleApp {

    static HashMap<Integer, Account> accounts = new HashMap<>();

    static HashMap<String, List<Integer>> customerIndex = new HashMap<>();

    static Scanner sc = new Scanner(System.in);

    static Account getAccount(int id) {
        return accounts.get(id);
    }

    static void createAccount() {

        System.out.print("Enter Account ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        if (accounts.containsKey(id)) {
            System.out.println("Account already exists!");
            return;
        }

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        if (balance < 0) {
            System.out.println("Balance cannot be negative.");
            return;
        }

        Account account = new Account(id, name, balance);

        accounts.put(id, account);

        customerIndex
                .computeIfAbsent(name.toLowerCase(), k -> new ArrayList<>())
                .add(id);

        System.out.println("Account created successfully!");
    }

    static void deposit() {

        System.out.print("Enter Account ID: ");
        int id = sc.nextInt();

        Account account = getAccount(id);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        account.balance += amount;

        System.out.println("Deposit successful!");
        System.out.println("Balance: " + account.balance);
    }

    static void withdraw() {

        System.out.print("Enter Account ID: ");
        int id = sc.nextInt();

        Account account = getAccount(id);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        if (amount <= 0 || amount > account.balance) {
            System.out.println("Invalid amount or insufficient funds!");
            return;
        }

        account.balance -= amount;

        System.out.println("Withdrawal successful!");
        System.out.println("Balance: " + account.balance);
    }

    // Atomic transfer
    static void transfer() {

        System.out.print("Enter Source Account ID: ");
        int fromId = sc.nextInt();

        System.out.print("Enter Target Account ID: ");
        int toId = sc.nextInt();

        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();

        Account source = getAccount(fromId);
        Account target = getAccount(toId);

        if (source == null || target == null) {
            System.out.println("Invalid source or target account!");
            return;
        }

        if (fromId == toId) {
            System.out.println("Source and target cannot be same!");
            return;
        }

        if (amount <= 0 || amount > source.balance) {
            System.out.println("Transfer failed!");
            return;
        }

        // Atomic operation
        source.balance -= amount;
        target.balance += amount;

        Transaction transaction =
                new Transaction(fromId, toId, amount);

        source.transactions.add(transaction);
        target.transactions.add(transaction);

        System.out.println("Transfer successful!");
    }

    // Reverse last transaction
    static void reverseLastTransaction() {

        System.out.print("Enter Account ID: ");
        int id = sc.nextInt();

        Account account = getAccount(id);

        if (account == null) {
            System.out.println("Account not found!");
            return;
        }

        if (account.transactions.isEmpty()) {
            System.out.println("No transaction to reverse!");
            return;
        }

        Transaction transaction =
                account.transactions.get(
                        account.transactions.size() - 1);

        Account source = getAccount(transaction.fromId);
        Account target = getAccount(transaction.toId);

        if (source == null || target == null) {
            System.out.println("Transaction accounts not found!");
            return;
        }

        target.balance -= transaction.amount;
        source.balance += transaction.amount;

        source.transactions.remove(transaction);
        target.transactions.remove(transaction);

        System.out.println("Last transaction reversed successfully!");
    }

    // Customer name index
    static void searchCustomer() {

        sc.nextLine();

        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine().toLowerCase();

        List<Integer> ids = customerIndex.get(name);

        if (ids == null || ids.isEmpty()) {
            System.out.println("No accounts found!");
            return;
        }

        System.out.println("Accounts of " + name + ":");

        for (int id : ids) {
            Account account = accounts.get(id);

            if (account != null) {
                account.display();
                System.out.println("--------------------");
            }
        }
    }

    static void displayAll() {

        if (accounts.isEmpty()) {
            System.out.println("No accounts available!");
            return;
        }

        for (Account account : accounts.values()) {
            account.display();
            System.out.println("--------------------");
        }
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== BANK MANAGEMENT SYSTEM =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Transfer Money");
            System.out.println("5. Reverse Last Transaction");
            System.out.println("6. Search Customer Accounts");
            System.out.println("7. Display All Accounts");
            System.out.println("8. Exit");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    deposit();
                    break;

                case 3:
                    withdraw();
                    break;

                case 4:
                    transfer();
                    break;

                case 5:
                    reverseLastTransaction();
                    break;

                case 6:
                    searchCustomer();
                    break;

                case 7:
                    displayAll();
                    break;

                case 8:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
                }
