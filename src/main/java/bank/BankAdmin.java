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
        double totalAssets = 0;
        for (BankAccount account : Bank.bankAccounts.values()) {
            totalAssets += account.getBalance();
        }
        return totalAssets;
    }

    public void toggleFreezeAccount(int accountNumber){
        if (Bank.bankAccounts.containsKey(accountNumber)) {
            BankAccount account = Bank.bankAccounts.get(accountNumber);
            account.isFrozen = !account.isFrozen;
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

    public List<Transaction> getSuspiciousActivityReport(int accountNumber){
        if (Bank.bankAccounts.containsKey(accountNumber)) {
            return Bank.bankAccounts.get(accountNumber).getSuspiciousActivityReport();
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

}
