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

    /**
     * Set the frozen state of the account. Added to support admin actions.
     */
    void setFrozen(boolean frozen);

    String toString();

    Integer getAccountNumber();









    
    













    
}
