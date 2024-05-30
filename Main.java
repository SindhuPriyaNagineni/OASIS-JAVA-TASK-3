package sindhu1;

import java.util.Scanner;

//Transaction class to represent individual transactions
class Transaction {
   // Fields for transaction details
   private String type;
   private double amount;

   // Constructor
   public Transaction(String type, double amount) {
       this.type = type;
       this.amount = amount;
   }

   // Getter methods
   public String getType() {
       return type;
   }

   public double getAmount() {
       return amount;
   }
}

//User class to represent ATM users
class User {
   // Fields for user details
   public String userId;
   public String pin;
   public double balance;
   public Transaction[] transactions; // Array to store transaction history
   public int transactionCount; // Counter for number of transactions

   // Constructor
   public User(String userId, String pin) {
       this.userId = userId;
       this.pin = pin;
       this.balance = 0.0;
       this.transactions = new Transaction[100]; // Maximum 100 transactions
       this.transactionCount = 0;
   }

   // Getter methods
   public String getUserId() {
       return userId;
   }

   public double getBalance() {
       return balance;
   }

   // Method to add transaction to history
   public void addTransaction(String type, double amount) {
       transactions[transactionCount] = new Transaction(type, amount);
       transactionCount++;
   }
}

//ATM class to handle ATM functionalities
class ATM {
   // Method to display transaction history
   public static void showTransactionHistory(User user) {
       System.out.println("Transaction History:");
       for (int i = 0; i < user.transactionCount; i++) {
           Transaction transaction = user.transactions[i];
           System.out.println("Type: " + transaction.getType() + ", Amount: " + transaction.getAmount());
       }
   }

   // Method to withdraw money
   public static void withdraw(User user, double amount) {
       if (amount > 0 && amount <= user.getBalance()) {
           user.balance -= amount;
           user.addTransaction("Withdraw", amount);
           System.out.println("Withdrawal successful. Remaining balance: " + user.getBalance());
       } else {
           System.out.println("Invalid amount or insufficient balance.");
       }
   }

   // Method to deposit money
   public static void deposit(User user, double amount) {
       if (amount > 0) {
           user.balance += amount;
           user.addTransaction("Deposit", amount);
           System.out.println("Deposit successful. New balance: " + user.getBalance());
       } else {
           System.out.println("Invalid amount.");
       }
   }

   // Method to transfer money
   public static void transfer(User user, User recipient, double amount) {
       if (amount > 0 && amount <= user.getBalance()) {
           user.balance -= amount;
           recipient.balance += amount;
           user.addTransaction("Transfer to " + recipient.getUserId(), amount);
           recipient.addTransaction("Transfer from " + user.getUserId(), amount);
           System.out.println("Transfer successful. Remaining balance: " + user.getBalance());
       } else {
           System.out.println("Invalid amount or insufficient balance.");
       }
   }
}

//Main class to drive the ATM application
public class Main {
   public static void main(String[] args) {
       // Creating user accounts
       User user1 = new User("user1", "1234");
       User user2 = new User("user2", "5678");

       // Simulating ATM interface
       Scanner scanner = new Scanner(System.in);
       System.out.println("Welcome to the ATM!");

       // Authentication
       System.out.print("Enter user id: ");
       String userId = scanner.nextLine();
       System.out.print("Enter pin: ");
       String pin = scanner.nextLine();

       if (userId.equals(user1.getUserId()) && pin.equals(user1.pin) ||
           userId.equals(user2.getUserId()) && pin.equals(user2.pin)) {
           System.out.println("Authentication successful.");

           // ATM functionalities
           boolean quit = false;
           while (!quit) {
               System.out.println("\nChoose an option:");
               System.out.println("1. Transaction History");
               System.out.println("2. Withdraw");
               System.out.println("3. Deposit");
               System.out.println("4. Transfer");
               System.out.println("5. Quit");

               int choice = scanner.nextInt();
               switch (choice) {
                   case 1:
                       ATM.showTransactionHistory(userId.equals(user1.getUserId()) ? user1 : user2);
                       break;
                   case 2:
                       System.out.print("Enter withdrawal amount: ");
                       double withdrawAmount = scanner.nextDouble();
                       ATM.withdraw(userId.equals(user1.getUserId()) ? user1 : user2, withdrawAmount);
                       break;
                   case 3:
                       System.out.print("Enter deposit amount: ");
                       double depositAmount = scanner.nextDouble();
                       ATM.deposit(userId.equals(user1.getUserId()) ? user1 : user2, depositAmount);
                       break;
                   case 4:
                       System.out.print("Enter recipient user id: ");
                       String recipientId = scanner.next();
                       User recipient = recipientId.equals(user1.getUserId()) ? user1 : user2;
                       System.out.print("Enter transfer amount: ");
                       double transferAmount = scanner.nextDouble();
                       ATM.transfer(userId.equals(user1.getUserId()) ? user1 : user2, recipient, transferAmount);
                       break;
                   case 5:
                       System.out.println("Thank you for using the ATM. Goodbye!");
                       quit = true;
                       break;
                   default:
                       System.out.println("Invalid option. Please try again.");
               }
           }
       } else {
           System.out.println("Authentication failed. Invalid user id or pin.");
       }
       scanner.close();
   }
}