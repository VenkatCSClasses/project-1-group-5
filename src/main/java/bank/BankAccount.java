package bank;
import java.util.List;

public class BankAccount {
    int #customerID;
    int #accountNumber;
    double #balance;
    boolean #isFrozen;
    List<Transaction> #transactionHistory;

    public BankAccount(int customerID, int accountNumber, double initialDeposit){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public void deposit(double amount){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public void withdraw(double amount){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public void transfer(BankAccount targetAccount, double amount){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public double getBalance(){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public List<Transaction> getTransactionHistory(){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public List<Transaction> getSuspiciousActivityReport(){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public boolean isFrozen(){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public int getAccountNumber(){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public int getCustomerID(){
        throw new IllegalArgumentException("Not implemented yet");
    }















    
}
