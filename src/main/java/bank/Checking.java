package bank;
import java.util.List;

public class Checking implements BankAccount {
    int customerID;
    int accountNum;
    double balance;
    boolean isFrozen;
    List<Transaction> transactionHistory;
    List<Transaction> suspiciousActivity;

    public Checking(int customerID, int accountNum, double balance){
        throw new IllegalArgumentException("Not implemented yet");
    }

    @Override
    public void deposit(double amount) {
        throw new IllegalArgumentException("Not implemented yet");
    }

    @Override
    public void withdraw(double amount) {
        throw new IllegalArgumentException("Not implemented yet");
    }

    @Override
    public void transfer(BankAccount targetAccount, double amount) {
        throw new IllegalArgumentException("Not implemented yet");
    }

    @Override
    public double checkBalance() {
        throw new IllegalArgumentException("Not implemented yet");
    }

    @Override
    public List<Transaction> getSuspiciousActivity() {
        throw new IllegalArgumentException("Not implemented yet");
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        throw new IllegalArgumentException("Not implemented yet");
    }

    @Override
    public boolean isFrozen() {
        throw new IllegalArgumentException("Not implemented yet");








    
}
