package bank;
import java.util.List;

public class Checking implements BankAccount {
    int customerID;
    int accountNum;
    double balance;
    boolean isFrozen;
    List<Transaction> transactionHistory ;
    List<Transaction> suspiciousActivity;

    public Checking(int customerID, double balance){
        /*
        Initialises a new checking account with the given customer  
        The balance is set to 0.0 by default, and the account is not frozen by default.
        The account number is generated automatically and is unique for each account.
        */
        this.customerID = customerID;
        this.balance = balance;
        this.isFrozen = false;

        //generate unique account number
        this.accountNum = (int) (Math.random() * 1000000);
        this.transactionHistory = new java.util.ArrayList<Transaction>();
        this.suspiciousActivity = new java.util.ArrayList<Transaction>();
    }

    @Override
    public void deposit(double amount) {
        /*
         Deposits the specified amount into the account.
        If the amount is less than or equal to 0, an IllegalArgumentException is thrown with the message 
        "Deposit amount must be greater than 0!".
         
        */
        if(this.isFrozen){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Deposit";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalStateException("Account is frozen! Cannot perform transactions.");
        }else if(amount >= 5000.0){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Deposit";
            boolean status = false;
            boolean suspiciousActivity = true;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            this.suspiciousActivity.add(newTransaction);
            throw new IllegalArgumentException("Deposit amount exceeds the limit of $5000.0!");
        }else if(amount <= 0){

            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Deposit";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Deposit amount must be greater than 0!");
        } else{

            this.balance += amount;

            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Deposit";
            boolean status = true;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);

        }
    
    }

    @Override
    public void withdraw(double amount) {
        /*
        Withdraws the specified amount from the account.
        If the amount is greater than the current balance, an IllegalArgumentException is thrown with the message "Insufficient funds!"
        If the amount is less than or equal to 0, an IllegalArgumentException is thrown with the message "Withdrawal amount must be greater than 0!"
   
        */

        if(this.isFrozen){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Withdrawal";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalStateException("Account is frozen! Cannot perform transactions.");
        } else if(amount >= 5000.0){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Withdrawal";
            boolean status = false;
            boolean suspiciousActivity = true;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            this.suspiciousActivity.add(newTransaction);

            throw new IllegalArgumentException("Withdrawal amount exceeds the limit of $5000.0!");
        }else if(this.balance < amount){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Withdrawal";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Insufficient funds!");
        }else if(amount <= 0){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Withdrawal";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0!");
        }else {
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Withdrawal";
            boolean status = true;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            this.balance -= amount;
        }

    }

    @Override
    public void transfer(BankAccount targetAccount, double amount) {
        
        if(this.isFrozen){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Transfer";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalStateException("Account is frozen! Cannot perform transactions.");
        }else if(targetAccount.isFrozen()){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Transfer";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalStateException("Target account is frozen! Cannot perform transactions.");
        } else if((targetAccount == null) || (targetAccount.getAccountNumber() == this.accountNum)){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Transfer";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Target account cannot be null or the same as the source account!");
        } else if (amount >= 5000.0){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Transfer";
            boolean status = false;
            boolean suspiciousActivity = true;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            this.suspiciousActivity.add(newTransaction);
            throw new IllegalArgumentException("Transfer amount exceeds the limit of $5000.0!");
        }else if(this.balance < amount){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Transfer";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Insufficient funds!");
        }else if (amount <= 0){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Transfer";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Transfer amount must be greater than 0!");
        } else{
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Transfer";
            boolean status = true;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, amount, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            this.balance -= amount;
            targetAccount.deposit(amount);  
        }

    }

    @Override
    public double checkBalance() {
        if(this.isFrozen){
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Check Balance";
            boolean status = false;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, 0.0, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            throw new IllegalStateException("Account is frozen! Cannot perform any actions.");
        }else{
            int transactionID = (int) (System.currentTimeMillis() % 10000000) + (int) (Math.random() * 1000000);
            int timestamp = (int) (System.currentTimeMillis() / 1000);
            String type = "Check Balance";
            boolean status = true;
            boolean suspiciousActivity = false;
            Transaction newTransaction = new Transaction(transactionID, timestamp, type, 0.0, status, suspiciousActivity);
            this.transactionHistory.add(newTransaction);
            return this.balance;
        }

    }

    @Override
    public int getCustomerID() {
        return this.customerID;

    }

    @Override
    public List<Transaction> getSuspiciousActivity() {
        return this.suspiciousActivity; 
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        return this.transactionHistory;
        
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public void setFrozen(boolean frozen) {
        this.isFrozen = frozen;
    }
    @Override
    public Integer getAccountNumber() {
        return this.accountNum; 
    }

    @Override
    public String toString() {
        return "Checking Account - Customer ID: " + this.customerID + ", Balance: $" + String.format("%.2f", this.balance) + ", Frozen: " + this.isFrozen;
    }










    
}
