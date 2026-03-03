package bank;
import java.util.List;
import java.time.LocalDate;
//Trying to commit now
public class Savings extends BankAccount {

    private final Bank bank;                 // reads savings interest rate + daily limit policy
    private LocalDate lastInterestAppliedDate;

    private LocalDate lastWithdrawalDate;
    private double withdrawnToday;

    public Savings(int customerID, int accountNum, double startingBalance, Bank bank) {
        super(customerID, accountNum, startingBalance);
        if (bank == null) throw new IllegalArgumentException("bank cannot be null");
        this.bank = bank;

        this.lastInterestAppliedDate = null;
        this.lastWithdrawalDate = null;
        this.withdrawnToday = 0.0;
    }

    @Override
    public void withdraw(double amount) {
        withdraw(amount, LocalDate.now()); // delegate
}

    public void withdraw(double amount, LocalDate date) {
    // Savings rules here
}

    @Override
    public void transfer(BankAccount target, double amount) {
    //To do 
}

public void transfer(BankAccount target, double amount, LocalDate date) {
    // Savings rules here
}

    public void compoundDailyInterest(LocalDate date) {
        // TODO: implement
    }

    // Helpers
    private void resetDailyIfNewDay(LocalDate date) {
        // TODO: implement
    }

    private void ensureNotFrozen() {
        // TODO: implement
    }

    private void ensurePositiveAmount(double amount) {
        // TODO: implement
    }

    private void ensureSufficientFunds(double amount) {
        // TODO: implement
    }

    private void ensureWithinDailyLimit(double amount) {
        // TODO: implement
    }

    private double getDailyInterestRateFromBank() {
        // TODO: return bank.getSavingsAnnualInterestRate() / 365.0;
        return 0.0;
    }

    private void recordSavingsTransaction(String type, double amount, boolean suspiciousActivity) {
        // TODO: implement (append to inherited transactionHistory via protected method)
    }

    // Getters
    public Bank getBank() { return bank; }
    public LocalDate getLastInterestAppliedDate() { return lastInterestAppliedDate; }
    public LocalDate getLastWithdrawalDate() { return lastWithdrawalDate; }
    public double getWithdrawnToday() { return withdrawnToday; }
}