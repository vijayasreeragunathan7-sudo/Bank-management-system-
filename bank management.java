import java.util.HashMap;
import java.util.Scanner;

// Custom exception for invalid account
class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String message) {
        super(message);
    }
}

// Custom exception for insufficient balance
class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Account class
class Account {

    private int id;
    private String customerName;
    private double balance;

    public Account(int id, String customerName, double balance) {
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void displayAccount() {
        System.out.println("Account ID     : " + id);
        System.out.println("Customer Name  : " + customerName);
        System.out.println("Balance        : ₹" + balance);
    }
}

// Main class
public class BankConsoleApp {

    static HashMap<Integer, Account> accounts = new HashMap<>();

    static int nextAccountId = 1001;

    static Scanner scanner = new Scanner(System.in);

    // Create account
    public static void createAccount() {

        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        Account account = new Account(nextAccountId, name, 0);

        accounts.put(nextAccountId, account);

        System.out.println("Account created successfully!");
        System.out.println("Your Account ID is: " + nextAccountId);

        nextAccountId++;
    }

    // Find account
    public static Account findAccount(int id)
            throws AccountNotFoundException {

        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account with ID " + id + " not found!"
            );
        }

        return accounts.get(id);
    }

    // View account / balance inquiry
    public static void viewAccount() {

        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {

            Account account = findAccount(id);

            account.displayAccount();

        } catch (AccountNotFoundException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Deposit
    public static void deposit() {

        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter deposit amount: ₹");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {

            Account account = findAccount(id);

            if (amount <= 0) {
                System.out.println(
                        "Error: Deposit amount must be greater than zero!"
                );
                return;
            }

            account.deposit(amount);

            System.out.println("Amount deposited successfully!");
            System.out.println(
                    "Current Balance: ₹" + account.getBalance()
            );

        } catch (AccountNotFoundException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Withdraw
    public static void withdraw() {

        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter withdrawal amount: ₹");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        try {

            Account account = findAccount(id);

            if (amount <= 0) {
                System.out.println(
                        "Error: Withdrawal amount must be greater than zero!"
                );
                return;
            }

            if (amount > account.getBalance()) {

                throw new InsufficientFundsException(
                        "Insufficient funds! Available balance: ₹"
                                + account.getBalance()
                );
            }

            account.withdraw(amount);

            System.out.println("Amount withdrawn successfully!");
            System.out.println(
                    "Current Balance: ₹" + account.getBalance()
            );

        } catch (AccountNotFoundException e) {

            System.out.println("Error: " + e.getMessage());

        } catch (InsufficientFundsException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Close account
    public static void closeAccount() {

        System.out.print("Enter Account ID to close: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {

            findAccount(id);

            accounts.remove(id);

            System.out.println("Account closed successfully!");

        } catch (AccountNotFoundException e) {

            System.out.println("Error: " + e.getMessage());
        }
    }

    // Main menu
    public static void main(String[] args) {

        while (true) {

            System.out.println();
            System.out.println("===== SECUREBANK =====");
            System.out.println("1. Create Account");
            System.out.println("2. View Balance");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Close Account");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    viewAccount();
                    break;

                case 3:
                    deposit();
                    break;

                case 4:
                    withdraw();
                    break;

                case 5:
                    closeAccount();
                    break;

                case 6:
                    System.out.println(
                            "Thank you for using SecureBank!"
                    );
                    scanner.close();
                    return;

                default:
                    System.out.println(
                            "Invalid choice! Please try again."
                    );
            }
        }
    }
    }
