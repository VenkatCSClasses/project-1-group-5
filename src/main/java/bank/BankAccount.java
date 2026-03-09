package bank;
import java.util.List;


public interface BankAccount {


    void deposit(double amount);

    void withdraw(double amount);

    void transfer(BankAccount targetAccount, double amount);

    double checkBalance();

    List<Transaction> getSuspiciousActivity();

    List<Transaction> getTransactionHistory();

    boolean isFrozen();

    String toString();

    Integer getAccountNumber();









    
    













    
}
