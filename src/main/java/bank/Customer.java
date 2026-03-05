package bank;

import java.util.ArrayList;
import java.util.List;

public class Customer implements UserInterface {
    
    private String username;
    private int userID;
    private int accountNum;
    private int pin;
    List<BankAccount> accounts;

    public Customer(String username, int userID, int accountNum, int pin){
        this.username = username;
        this.userID = userID;
        this.accountNum = accountNum;
        this.pin = pin;
        this.accounts = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }
    
    public int getUserID() {
        return userID;
    }

    public int getAccountNum() {
        return accountNum;
    }

    public int getPin() {
        return pin;
    }

    public List<BankAccount> getAccounts() {
        return accounts;
    }

    // withdra or deposit amount into given account, transation type = 0 for withdra, 1 for deposit
    public void processTransaction(int accountNumber, double amount, int transactionType){

    }

    //get total balance for a specific account number, which is associated with the customer
    public double checkBalance(int accountNum){
        return 0.0;
    }

    //get the username, userID, accounNum, and list of Accounts
    public String getAccountDetails(){
        return "";
    }

    //change the pin for the customer, pin has to be 4 digits, and the original pin has to match the current pin, and new pin can't be the same as original pin
    public void changePin(int originalPin,int newPin){

    }

}
