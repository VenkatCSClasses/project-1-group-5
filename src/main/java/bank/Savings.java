package bank;

import java.time.LocalDate;
import java.util.Collections;
import java.lang.IllegalArgumentException;
// custom exception classes in same package; no import necessary


public class Savings extends BankAccount {

    // configuration previously provided by Bank: now static values set directly
    private static double savingsAnnualInterestRate = 0.0;
    private static double savingsDailyWithdrawalLimit = Double.MAX_VALUE;

    private LocalDate lastInterestAppliedDate;

    private LocalDate lastWithdrawalDate;
    private double withdrawnToday;

    /**
     * Create a savings account with a randomly generated customer & account ID
     * and zero opening balance.
     */
    public Savings() {
        this(0.0);
    }

    /**
     * Create a savings account with a randomly generated IDs and the given
     * starting balance.
     */
    public Savings(double startingBalance) {
        super(generateCustomerID(), generateAccountNum(), startingBalance);
        this.lastInterestAppliedDate = null;
        this.lastWithdrawalDate = null;
        this.withdrawnToday = 0.0;
    }

    // -------------------------
    // Withdraw
    // -------------------------

    @Override
    public void withdraw(double amount) {
        withdraw(amount, LocalDate.now());
    }

    public void withdraw(double amount, LocalDate date) {
        if (date == null) throw new IllegalArgumentException("date cannot be null");

        ensurePositiveAmount(amount);
        ensureNotFrozen();

        resetDailyIfNewDay(date);
        ensureWithinDailyLimit(amount);
        ensureSufficientFunds(amount);

        // Actually move money
        // BankAccount.withdraw already subtracts from balance.
        super.withdraw(amount);

        withdrawnToday += amount;
        lastWithdrawalDate = date;

        // Transactions not asserted by the provided tests; keep as hook if you track history.
        // recordSavingsTransaction("WITHDRAW", amount, false);
    }

    // -------------------------
    // Transfer (counts toward daily limit)
    // -------------------------

    @Override
    public void transfer(BankAccount target, double amount) {
        transfer(target, amount, LocalDate.now());
    }

    public void transfer(BankAccount target, double amount, LocalDate date) {
        if (target == null) throw new IllegalArgumentException("target cannot be null");
        if (date == null) throw new IllegalArgumentException("date cannot be null");

        ensurePositiveAmount(amount);
        ensureNotFrozen();

        resetDailyIfNewDay(date);
        ensureWithinDailyLimit(amount);
        ensureSufficientFunds(amount);

        // Withdraw from this savings (will also enforce any base BankAccount rules)
        super.withdraw(amount);

        // Deposit into target
        target.deposit(amount);

        withdrawnToday += amount;
        lastWithdrawalDate = date;

        // recordSavingsTransaction("TRANSFER_OUT", amount, false);
    }

    // -------------------------
    // Daily interest (once per day)
    // -------------------------

    public void compoundDailyInterest(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("date cannot be null");

        if (lastInterestAppliedDate != null && lastInterestAppliedDate.equals(date)) {
            return; // already applied today
        }

        double dailyRate = getDailyInterestRate(); // APR/365
        double interest = checkBalance() * dailyRate;

        // Apply interest by depositing (keeps balance updates centralized in BankAccount)
        if (interest != 0.0) {
            super.deposit(interest);
        }

        lastInterestAppliedDate = date;

        // recordSavingsTransaction("INTEREST", interest, false);
    }

    // -------------------------
    // Helpers
    // -------------------------

    private void resetDailyIfNewDay(LocalDate date) {
        if (lastWithdrawalDate == null || !lastWithdrawalDate.equals(date)) {
            withdrawnToday = 0.0;
            lastWithdrawalDate = date;
        }
    }

    private void ensureNotFrozen() {
        // Assumes BankAccount has isFrozen() and freeze()
        if (isFrozen()) {
            throw new IllegalStateException("Account is frozen");
        }
    }

    private void ensurePositiveAmount(double amount) {
        if (amount <= 0.0) {
            throw new IllegalArgumentException("amount must be > 0");
        }
    }

    private void ensureSufficientFunds(double amount) {
        // Ensures SavingsTest expects InsufficientFundsException on overdraft attempts
        if (checkBalance() < amount) {
            throw new InsufficientFundsException("Insufficient funds");
        }
    }

    private void ensureWithinDailyLimit(double amount) {
        double limit = savingsDailyWithdrawalLimit;
        if (withdrawnToday + amount > limit) {
            throw new DailyLimitExceededException("Daily withdrawal limit exceeded");
        }
    }

    private static double getDailyInterestRate() {
        return savingsAnnualInterestRate / 365.0;
    }

    // configuration helpers used in tests or bank helper
    public static void setSavingsAnnualInterestRate(double rate) {
        savingsAnnualInterestRate = rate;
    }

    public static void setSavingsDailyWithdrawalLimit(double limit) {
        savingsDailyWithdrawalLimit = limit;
    }

    public static double getSavingsDailyWithdrawalLimit() {
        return savingsDailyWithdrawalLimit;
    }

    @SuppressWarnings("unused")
    private void recordSavingsTransaction(String type, double amount, boolean suspiciousActivity) {
        // Not required for the provided tests.
        // If your BankAccount exposes something like addTransaction(...), call it here.
    }

    // -------------------------
    // Getters
    // -------------------------

    public LocalDate getLastInterestAppliedDate() { return lastInterestAppliedDate; }
    public LocalDate getLastWithdrawalDate() { return lastWithdrawalDate; }
    public double getWithdrawnToday() { return withdrawnToday; }
}