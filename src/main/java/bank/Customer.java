package bank;

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


    public void processTransaction(int accountNum){

    }

    //get balance for a specific account number, which is associated with the customer
    public double checkBalance(int accountNum){
        return 0.0;
    }

    //get the username, userID, accounNum, and list of Accounts
    public String getAccountDetails(){
        return "";
    }

    //change the pin for the customer
    public void changePin(int newPin){

    }

}
