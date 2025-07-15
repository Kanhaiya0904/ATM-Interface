import java.util.*;

class ATM {
    private double balance;
    private List<String> transactionHistory;
    private String userId;
    private String userPin;

    public ATM(String userId, String userPin) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public boolean authenticate(String inputId, String inputPin) {
        return this.userId.equals(inputId) && this.userPin.equals(inputPin);
    }

    public void Menu(Map<String, ATM> users) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n====== ATM MENU ======");
            System.out.println("1. Transaction History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an operation to perform: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    TransactionHistory();
                    break;
                case 2:
                    withdraw(sc);
                    break;
                case 3:
                    deposit(sc);
                    break;
                case 4:
                    transfer(sc, users);
                    break;
                case 5:
                    System.out.println("Thank you for using the ATM!");
                    break;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        } while (choice != 5);
    }

    private void TransactionHistory() {
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("\n--- Transaction History ---");
            for (String record : transactionHistory) {
                System.out.println(record);
            }
        }
    }

    private void withdraw(Scanner sc) {
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            balance -= amount;
            transactionHistory.add("Withdrawn: ₹" + amount);
            System.out.println("Withdrawal successful.");
        }
    }

    private void deposit(Scanner sc) {
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        balance += amount;
        transactionHistory.add("Deposited: ₹" + amount);
        System.out.println("Deposit successful.");
    }

    private void transfer(Scanner sc, Map<String, ATM> users) {
        System.out.print("Enter receiver's User ID: ");
        String receiverId = sc.next();
        if (!users.containsKey(receiverId)) {
            System.out.println("Receiver not found.");
            return;
        }

        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();

        if (amount > balance) {
            System.out.println("Insufficient balance.");
        } else {
            ATM receiver = users.get(receiverId);
            balance -= amount;
            receiver.balance += amount;
            transactionHistory.add("Transferred ₹" + amount + " to " + receiverId);
            receiver.transactionHistory.add("Received ₹" + amount + " from " + this.userId);
            System.out.println("Amount successfully transferred to " + receiverId);
        }
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, ATM> users = new HashMap<>();
        users.put("user123", new ATM("user123", "1111"));
        users.put("user456", new ATM("user456", "4444"));
        users.put("user789", new ATM("user789", "7777"));

        System.out.println("===== Welcome to ATM System =====");
        System.out.print("Enter your User ID: ");
        String userId = sc.next();
        System.out.print("Enter your PIN: ");
        String userPin = sc.next();

        if (users.containsKey(userId) && users.get(userId).authenticate(userId, userPin)) {
            System.out.println("Login successful.");
            users.get(userId).Menu(users);
        } else {
            System.out.println("Invalid User ID or PIN.");
        }
    }
}
