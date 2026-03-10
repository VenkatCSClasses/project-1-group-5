package bank;

public class Atm {
    
    private String location;
    private int atmID;
    private Bank bank;

    public Atm(String location, int atmID, Bank bank) {
        this.location = location;
        this.atmID = atmID;
        this.bank = bank;
    }

    public String getLocation() {
        return location;
    }

    public int getAtmID() {
        return atmID;
    }

    //log in for users, specifically customers but tellers and admins can use this as well
    public boolean validateCredentials(int cID, int inputPin){
        return bank.validateCredentials(cID, inputPin);
        
        
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
