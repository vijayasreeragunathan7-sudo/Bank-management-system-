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

    public static void createAccount() {
        System.out.print("Enter customer name: ");
        String name = scanner.nextLine();

        Account account = new Account(nextAccountId, name, 0);

        accounts.put(nextAccountId, account);

        System.out.println("Account created successfully!");
        System.out.println("Your Account ID is: " + nextAccountId);

        nextAccountId++;
    }

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

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== SECUREBANK =====");
            System.out.println("1. Create Account");
            System.out.println("2. View Account");
            System.out.println("3. Exit");
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
                    System.out.println("Thank you for using SecureBank!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}