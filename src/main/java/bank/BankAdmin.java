package bank;
import java.util.List;

public class BankAdmin {
    public void createAccount(int customerID, double initialDeposit, int accountType){
        if (accountType == 1) {
            Checking newAccount = new Checking(customerID, initialDeposit);
            Bank.addAccount(newAccount);
        } else if (accountType == 2) {
            Savings newAccount = new Savings(customerID, initialDeposit);
            Bank.addAccount(newAccount);
        } else {
            throw new IllegalArgumentException("Invalid account type");
        }
    }

    public void closeAccount(int accountNumber){
        if (Bank.getAllAccounts().containsKey(accountNumber)) {
            Bank.allAccounts.remove(accountNumber);
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

    public void processTransaction(int accountNumber, double amount, int transactionType){
       if (Bank.getAllAccounts().containsKey(accountNumber)) {
            if (transactionType == 1) {
                Bank.getAllAccounts().get(accountNumber).deposit(amount);
            } else if (transactionType == 2) {
                Bank.getAllAccounts().get(accountNumber).withdraw(amount);
            } else {
                throw new IllegalArgumentException("Invalid transaction type");
            }
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

    public double calculateTotalAssets(){
        double totalAssets = 0;
        for (BankAccount account : Bank.getAllAccounts().values()) {
            totalAssets += account.checkBalance();
        }
        return totalAssets;
    }

    public void toggleFreezeAccount(int accountNumber){
        if (Bank.getAllAccounts().containsKey(accountNumber)) {
            BankAccount account = Bank.getAllAccounts().get(accountNumber);
            if (account.isFrozen()) {
                account.isFrozen;
            } else {
                account.isFrozen();
            }
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

    public List<Transaction> getSuspiciousActivityReport(int accountNumber){
        if (Bank.getAllAccounts().containsKey(accountNumber)) {
            return Bank.getAllAccounts().get(accountNumber).getSuspiciousActivity();
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }

}
