package bank;

public class Atm {
    
    private String location;
    private int atmID;

    public Atm(String location, int atmID){
        this.location = location;
        this.atmID = atmID;
    }

    //log in for users, specifically customers but tellers and admins can use this as well
    public boolean validateCredentials(int userID, int inputPin){
        if (String.valueOf(inputPin).length() != 4 || inputPin < 0) {
            throw new IllegalArgumentException("Pin must be a positive 4-digit number.");
        }
        Bank bank = Bank.getCustomers().get(userID);
        if (bank == null) {
            throw new IllegalArgumentException("User ID not found.");
        }
        else {
            Customer customer = bank.getCustomers().get(userID);
            return customer.getPin() == inputPin;
        }
    }

    //does the deposit, withdraw
    public static void processTransaction(BankAccount account, double amount, int transactionType){
        if (transactionType == 0) {
            account.withdraw(amount);
        } else if (transactionType == 1) {
            account.deposit(amount);
        } else {
            throw new IllegalArgumentException("Invalid transaction type.");
        }
    }
}
