package bank;
import static bank.Customer.allCustomers;

import java.util.ArrayList;
import java.util.List;

public class BankAdmin extends Customer{
    private String username;
    private int pin;
    private int userType; // 0 for customer, 1 for teller, 2 for admin
    private int userID;

    private static final List<Customer> allCustomers = new ArrayList<>();

    public BankAdmin(String username, int pin) {
        super(username, pin);
        for (Customer customer : allCustomers) {
            if (customer.getUsername().equals(username)) {
                throw new IllegalArgumentException("Username already exists.");
            }
            if (customer.getUserID() == userID) {
                throw new IllegalArgumentException("UserID already exists.");
            }
        }
        if (String.valueOf(pin).length() != 4) {
            throw new IllegalArgumentException("Pin must be 4 digits.");
        }
        if (pin < 0) {
            throw new IllegalArgumentException("Pin cannot be negative.");
        }
        this.userType = 2; // set user type to admin
    }

    public void createAccount(int customerID, double initialDeposit, int accountType){
        if (accountType == 1) {
            Checking newAccount = new Checking(customerID, initialDeposit);
            Bank.addAccountStatic(newAccount);
        } else if (accountType == 2) {
            Savings newAccount = new Savings(customerID, initialDeposit);
            Bank.addAccountStatic(newAccount);
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }

    public void closeAccount(int accountNumber){
        if (Bank.allAccounts.containsKey(accountNumber)) {
            Bank.allAccounts.remove(accountNumber);
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

    public void processTransaction(int accountNumber, double amount, int transactionType){
       if (Bank.allAccounts.containsKey(accountNumber)) {
            if (transactionType == 1) {
                Bank.allAccounts.get(accountNumber).deposit(amount);
            } else if (transactionType == 2) {
                Bank.allAccounts.get(accountNumber).withdraw(amount);
            } else {
                throw new IllegalArgumentException("Invalid transaction type");
            }
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

    public double calculateTotalAssets(){
        double totalAssets = 0;
        for (BankAccount account : Bank.allAccounts.values()) {
            totalAssets += account.checkBalance();
        }
        return totalAssets;
    }

    public void toggleFreezeAccount(int accountNumber){
        if (Bank.allAccounts.containsKey(accountNumber)) {
            BankAccount account = Bank.allAccounts.get(accountNumber);
            account.setFrozen(!account.isFrozen());
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

    public List<Transaction> getSuspiciousActivityReport(int accountNumber){
        if (Bank.allAccounts.containsKey(accountNumber)) {
            return Bank.allAccounts.get(accountNumber).getSuspiciousActivity();
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

}
