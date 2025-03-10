import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class EnhancedBanking {
    static Scanner scanner = new Scanner(System.in);
    static String accountHolder = "Lalit";
    static double balance = 0;
    static final double INTEREST_RATE = 0.07; // 3% annual interest
    static String hashedPIN = hashPIN("0409");
    static ArrayList<String> transactions = new ArrayList<>();
    static int withdrawalCount = 0;
    static final int MAX_WITHDRAWALS = 3;
    static int failedAttempts = 0;
    static final int MAX_FAILED_ATTEMPTS = 3;
    static final int LOCKOUT_TIME = 30; // seconds

    public static void main(String[] args) {
        if (!authenticate()) {
            System.out.println("Authentication failed! Exiting...");
            return;
        }

        boolean isRunning = true;
        int choice;
        while (isRunning) {
            System.out.println("****************");
            System.out.println("WELCOME, " + accountHolder.toUpperCase() + "!");
            System.out.println("SECURE BANKING SYSTEM");
            System.out.println("****************");
            System.out.println("1. Show Balance.");
            System.out.println("2. Deposit.");
            System.out.println("3. Withdraw.");
            System.out.println("4. Show Transaction History.");
            System.out.println("5. Apply Interest.");
            System.out.println("6. Change PIN.");
            System.out.println("7. Forgot PIN.");
            System.out.println("8. Exit.");
            System.out.println("****************");
            System.out.print("Enter your choice (1 to 8): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1 -> showBalance();
                case 2 -> deposit();
                case 3 -> withdraw();
                case 4 -> showTransactionHistory();
                case 5 -> applyInterest();
                case 6 -> changePIN();
                case 7 -> forgotPIN();
                case 8 -> isRunning = false;
                default -> System.out.println("Invalid choice!");
            }
        }
        System.out.println("***************************");
        System.out.println("Thank you! Have a nice day.");
        System.out.println("***************************");
        scanner.close();
    }

    static boolean authenticate() {
        for (int i = 0; i < MAX_FAILED_ATTEMPTS; i++) {
            System.out.print("Enter PIN: ");
            String enteredPin = scanner.next();
            if (hashPIN(enteredPin).equals(hashedPIN)) {
                failedAttempts = 0;
                return true;
            } else {
                failedAttempts++;
                System.out.println("Incorrect PIN! Attempts remaining: " + (MAX_FAILED_ATTEMPTS - failedAttempts));
            }
        }
        System.out.println("Too many failed attempts! Account locked for " + LOCKOUT_TIME + " seconds.");
        try {
            TimeUnit.SECONDS.sleep(LOCKOUT_TIME);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        failedAttempts = 0;
        return false;
    }

    static void forgotPIN() {
        System.out.print("Enter account holder name: ");
        String name = scanner.next();
        if (!name.equalsIgnoreCase(accountHolder)) {
            System.out.println("Account verification failed! Try again.");
            return;
        }
        System.out.println("Verification code sent to registered email/phone (simulated: 123456)");
        System.out.print("Enter verification code: ");
        String verificationCode = scanner.next();
        if (!verificationCode.equals("123456")) {
            System.out.println("Incorrect verification code! Try again.");
            return;
        }
        System.out.print("Set a new PIN: ");
        String newPin = scanner.next();
        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.next();
        if (!newPin.equals(confirmPin)) {
            System.out.println("PINs do not match! Try again.");
        } else {
            hashedPIN = hashPIN(newPin);
            System.out.println("PIN reset successfully!");
        }
    }

    static void changePIN() {
        System.out.print("Enter current PIN: ");
        String currentPin = scanner.next();
        if (!hashPIN(currentPin).equals(hashedPIN)) {
            System.out.println("Incorrect PIN! PIN change failed.");
            return;
        }
        System.out.print("Enter new PIN: ");
        String newPin = scanner.next();
        if (hashPIN(newPin).equals(hashedPIN)) {
            System.out.println("New PIN cannot be the same as the current PIN. Try again.");
            return;
        }
        System.out.print("Confirm new PIN: ");
        String confirmPin = scanner.next();

        if (!newPin.equals(confirmPin)) {
            System.out.println("PINs do not match! Try again.");
        } else {
            hashedPIN = hashPIN(newPin);
            System.out.println("PIN changed successfully!");
        }
    }

    static void showBalance() {
        System.out.println("****************");
        System.out.printf("Current Balance: Rs. %.2f\n", balance);
    }

    static void deposit() {
        System.out.print("Enter amount to be deposited: ");
        double amount = scanner.nextDouble();
        if (amount <= 0) {
            System.out.println("Invalid amount!");
        } else {
            balance += amount;
            transactions.add("Deposited: Rs. " + amount);
            System.out.println("Amount Deposited Successfully.");
        }
    }

    static void withdraw() {
        if (withdrawalCount >= MAX_WITHDRAWALS) {
            System.out.println("Daily withdrawal limit reached!");
            return;
        }

        System.out.print("Enter amount to be withdrawn: ");
        double amount = scanner.nextDouble();
        if (amount > balance) {
            System.out.println("Insufficient Funds!");
        } else if (amount <= 0) {
            System.out.println("Invalid amount!");
        } else {
            balance -= amount;
            withdrawalCount++;
            transactions.add("Withdrawn: Rs. " + amount);
            System.out.println("Amount Withdrawn Successfully.");
        }
    }

    static void showTransactionHistory() {
        System.out.println("Transaction History:");
        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            transactions.forEach(System.out::println);
        }
    }

    static void applyInterest() {
        double interest = balance * (INTEREST_RATE / 12);
        balance += interest;
        transactions.add("Interest Applied: Rs. " + interest);
        System.out.printf("Interest of Rs. %.2f applied.\n", interest);
    }

    static String hashPIN(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(pin.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing PIN", e);
        }
    }
}