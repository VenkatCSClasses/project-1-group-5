package bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Customer {
    
    private String username;
    private int pin;
    private int userType; // 0 for customer, 1 for teller, 2 for admin
    private int userID;
    private HashMap<Integer, BankAccount> accounts;

    public Customer(String username, int pin, int userType){
        for (Customer customer : Bank.getCustomers()) {
            if (customer.getUsername().equals(username)) {
                throw new IllegalArgumentException("Username already exists.");
            }
        }
        if (String.valueOf(pin).length() != 4) {
            throw new IllegalArgumentException("Pin must be 4 digits.");
        }
        if (pin < 0) {
            throw new IllegalArgumentException("Pin cannot be negative.");
        }
        this.username = username;
        this.userType = userType;
        this.pin = pin;
        this.userID = Bank.getCustomerCount();
        this.accounts = new HashMap<Integer, BankAccount>();
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

    public HashMap<Integer, BankAccount> getAccounts() {
        return accounts;
    }

    // withdraw or deposit amount into given account, transation type = 0 for withdraw, 1 for deposit
    public void processTransaction(int accountNumber, double amount, int transactionType){
        BankAccount account = accounts.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account number not found.");
        }
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative.");
        }
        Atm.processTransaction(account, amount, transactionType);
    }

    //get total balance for a specific account number, which is associated with the bankAccount
    public double checkBalance(int accountNum){
        if (accounts.size()==0) {
            return 0.00;
        }
        else {
            BankAccount account = accounts.get(accountNum);
            if (account != null) {
                return account.checkBalance();
            }
        }
        return 0.00;
    }

    //get the username, userID, and list of Accounts
    public String getAccountDetails(){
        String accountNums = "";
        if (accounts.size()==0) {
            return "Username: " + username + ", UserID: " + userID + ", Accounts: []";
        }
        else
        {
            for (BankAccount account : accounts.values()) {
                accountNums += account.getAccountNumber() + ", ";
            }
            return "Username: " + username + ", UserID: " + userID + ", Accounts: [" + accountNums.substring(0, accountNums.length() - 2) + "]";
        }
    }

    //change the pin for the customer, pin has to be 4 digits, and the original pin has to match the current pin, and new pin can't be the same as original pin
    public void changePin(int originalPin,int newPin){
        if (newPin==originalPin) {
            throw new IllegalArgumentException("New pin cannot be the same as the original pin.");
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
