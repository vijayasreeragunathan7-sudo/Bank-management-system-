import java.util.HashMap;
import java.util.Scanner;

class Account {
    int id;
    String customerName;
    double balance;

    Account(int id, String customerName, double balance) {
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
    }

    void displayAccount() {
        System.out.println("Account ID: " + id);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Balance: ₹" + balance);
    }
}

public class BankConsoleApp {

    static HashMap<Integer, Account> accounts = new HashMap<>();
    static int nextAccountId = 1001;

    static Scanner scanner = new Scanner(System.in);

    // Create Account
    public static void createAccount() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        Account account = new Account(nextAccountId, name, 0);

        accounts.put(nextAccountId, account);

        System.out.println("Account created successfully!");
        System.out.println("Your Account ID is: " + nextAccountId);

        nextAccountId++;
    }

    // View Account
    public static void viewAccount() {
        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (accounts.containsKey(id)) {
            accounts.get(id).displayAccount();
        } else {
            System.out.println("Account not found!");
        }
    }

    // Deposit Money
    public static void deposit() {
        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter deposit amount: ₹");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (!accounts.containsKey(id)) {
            System.out.println("Account not found!");
            return;
        }

        if (amount <= 0) {
            System.out.println("Deposit amount must be greater than zero!");
            return;
        }

        Account account = accounts.get(id);
        account.balance += amount;

        System.out.println("Amount deposited successfully!");
        System.out.println("Current Balance: ₹" + account.balance);
    }

    // Withdraw Money
    public static void withdraw() {
        System.out.print("Enter Account ID: ");
        int id = scanner.nextInt();

        System.out.print("Enter withdrawal amount: ₹");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (!accounts.containsKey(id)) {
            System.out.println("Account not found!");
            return;
        }

        if (amount <= 0) {
            System.out.println("Withdrawal amount must be greater than zero!");
            return;
        }

        Account account = accounts.get(id);

        if (amount > account.balance) {
            System.out.println("Insufficient funds!");
            return;
        }

        account.balance -= amount;

        System.out.println("Amount withdrawn successfully!");
        System.out.println("Current Balance: ₹" + account.balance);
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== SECUREBANK =====");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Exit");

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
                    System.out.println("Thank you for using SecureBank!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
                }
