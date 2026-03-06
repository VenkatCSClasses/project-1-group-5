package bank;
import java.util.List;

public class BankAdmin {
    public void createAccount(String accountHolderName, double initialDeposit, int accountType){
        BankAccount newAccount = new BankAccount(accountHolderName, initialDeposit, accountType);
    }

    public void closeAccount(int accountNumber){
       Bank.bankAccounts.remove(accountNumber);
    }

    public void processTransaction(int accountNumber, double amount, int transactionType){
       if transactionType == 1 {
           Bank.bankAccounts.get(accountNumber).deposit(amount);
       } else if (transactionType == 2) {
           Bank.bankAccounts.get(accountNumber).withdraw(amount);
       } else {
           throw new IllegalArgumentException("Invalid transaction type");
       }
    }

    public double calculateTotalAssets(){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public void toggleFreezeAccount(int accountNumber){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public List<Transaction> getSuspiciousActivityReport(int accountNumber){
        throw new IllegalArgumentException("Not implemented yet");
    }

}
