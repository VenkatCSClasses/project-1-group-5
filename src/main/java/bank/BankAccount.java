package bank;
import java.util.List;


public interface BankAccount {
    


    /**
     * Deposit money into the account.
     * @param amount the amount to deposit
     */
    void deposit(double amount);

    /**
     * Withdraw money from the account.
     * @param amount the amount to withdraw
     */
    void withdraw(double amount);

    /**
     * Transfer money to another account.
     * @param targetAccount the account to transfer to
     * @param amount the amount to transfer
     */
    void transfer(BankAccount targetAccount, double amount);

    /**
     * Check the current balance of the account.
     * @return the current balance
     */

    double checkBalance();

    /**
     * Get the transaction history for the account.
     * @return a list of transactions
     */

    List<Transaction> getSuspiciousActivity();

    /**
     * Get the transaction history for the account.
     * @return a list of transactions
     */

    List<Transaction> getTransactionHistory();

    /**
     * Get the customer ID associated with the account.
     * @return the customer ID
     */

    int getCustomerID();

    /**
     * Check if the account is frozen. Added to support admin actions.
     * @return true if the account is frozen, false otherwise
     */

    boolean isFrozen();

    /**
     * Set the frozen state of the account. Added to support admin actions.
     */
    void setFrozen(boolean frozen);

    /**
     * Return a string representation of the account.
     * @return a string representation of the account
     */
    String toString();

    /**
     * Get the account number.
     * @return the account number
     */
    Integer getAccountNumber();









    
    













    
}
