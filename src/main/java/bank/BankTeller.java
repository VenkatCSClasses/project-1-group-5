package bank;

public class BankTeller{
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
}
