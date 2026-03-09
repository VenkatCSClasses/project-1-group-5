package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {
    private String username;
    private int pin;
    private int userType;
    private int userID;
    private ArrayList<BankAccount> accounts;

    private static final List<Customer> allCustomers = new ArrayList<>();
    private static int nextUserID = 1;

    public Customer(String username, int pin) {
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                throw new IllegalArgumentException("Username already exists.");
            }
        }

        if (pin < 0) {
            throw new IllegalArgumentException("Pin cannot be negative.");
        }
        if (String.valueOf(pin).length() != 4) {
            throw new IllegalArgumentException("Pin must be 4 digits.");
        }

        this.username = username;
        this.userType = 0;
        this.pin = pin;
        this.userID = nextUserID;
        nextUserID++;

        this.accounts = new ArrayList<>();
        allCustomers.add(this);
    }

    public static List<Customer> getAllCustomers() {
        return Collections.unmodifiableList(allCustomers);
    }

    public static int getCustomerCount() {
        return allCustomers.size();
    }

    public String getUsername() {
        return username;
    }

    public int getUserID() {
        return userID;
    }

    public int getPin() {
        return pin;
    }

    public ArrayList<BankAccount> getAccounts() {
        return accounts;
    }

    public void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null.");
        }
        accounts.add(account);
    }

    private BankAccount findAccount(int accountNumber) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        throw new IllegalArgumentException("Account number not found.");
    }

    public void processTransaction(int accountNumber, double amount, int transactionType) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }

        BankAccount account = findAccount(accountNumber);
        Atm.processTransaction(account, amount, transactionType);
    }

    public double checkBalance(int accountNumber) {
        if (accounts.size() == 0) {
            return 0.0;
        }
        BankAccount account = findAccount(accountNumber);
        return account.checkBalance();
    }

    public String getAccountDetails() {
        if (accounts.size() == 0) {
            return "Username: " + username + ", UserID: " + userID + ", Accounts: []";
        }

        String accountNums = "";
        for (BankAccount account : accounts) {
            accountNums += account.getAccountNumber() + ", ";
        }

        return "Username: " + username + ", UserID: " + userID + ", Accounts: ["
                + accountNums.substring(0, accountNums.length() - 2) + "]";
    }

    public void changePin(int originalPin, int newPin) {
        if (newPin == originalPin) {
            throw new IllegalArgumentException("New pin cannot be the same as the original pin.");
        }
        if (newPin < 0) {
            throw new IllegalArgumentException("New pin cannot be negative.");
        }
        if (String.valueOf(newPin).length() != 4) {
            throw new IllegalArgumentException("New pin must be 4 digits.");
        }
        if (pin != originalPin) {
            throw new IllegalArgumentException("Original pin is incorrect.");
        }
        this.pin = newPin;
    }
}