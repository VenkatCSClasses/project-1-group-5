package bank;

public class BankTeller{
    public void createAccount(int customerNumber, double initialDeposit, int accountType){
        BankAccount newAccount = new BankAccount(accountHolderName, initialDeposit, accountType);
    }

    public void closeAccount(int accountNumber){
       Bank.bankAccounts.remove(accountNumber);
    }

    public void processTransaction(int accountNumber, double amount, int transactionType){
        if (Bank.bankAccounts.containsKey(accountNumber)) {
            if (transactionType == 1) {
                Bank.bankAccounts.get(accountNumber).deposit(amount);
            } else if (transactionType == 2) {
                Bank.bankAccounts.get(accountNumber).withdraw(amount);
            } else {
                throw new IllegalArgumentException("Invalid transaction type");
            }
        } else {
            throw new IllegalArgumentException("Account does not exist");
        }
    }
}
