package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank {

    private final List<BankAccount> allAccounts;
    private double totalCash;

    // Savings policy fields used by Savings
    private double savingsDailyWithdrawalLimit;
    private double savingsAnnualInterestRate;

    public Bank() {
        this.allAccounts = new ArrayList<>();
        this.totalCash = 0.0;

        // sensible defaults (tests will set explicitly)
        this.savingsDailyWithdrawalLimit = 0.0;
        this.savingsAnnualInterestRate = 0.0;
    }

    public List<BankAccount> getAllAccounts() {
        // Return the actual list OR an unmodifiable view.
        // Tests only read/contains/isEmpty, so unmodifiable is fine.
        return Collections.unmodifiableList(allAccounts);
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void addAccount(BankAccount account) {
        if (account == null) throw new IllegalArgumentException("account cannot be null");
        allAccounts.add(account);
    }

    public void setSavingsDailyWithdrawalLimit(double limit) {
        if (limit < 0) throw new IllegalArgumentException("limit cannot be negative");
        this.savingsDailyWithdrawalLimit = limit;
    }

    public double getSavingsDailyWithdrawalLimit() {
        return savingsDailyWithdrawalLimit;
    }

    public void setSavingsAnnualInterestRate(double rate) {
        if (rate < 0) throw new IllegalArgumentException("rate cannot be negative");
        this.savingsAnnualInterestRate = rate;
    }

    public double getSavingsAnnualInterestRate() {
        return savingsAnnualInterestRate;
    }

    // ------------------------------------------------------------
    // Hooks for BankAccount to keep Bank.totalCash accurate
    // ------------------------------------------------------------

    /** Called by BankAccount constructors to add initial deposits into bank total. */
    void registerInitialBalance(double startingBalance) {
        totalCash += startingBalance;
    }

    /** Called by BankAccount.deposit/withdraw to keep total cash in sync. */
    void adjustTotalCash(double delta) {
        totalCash += delta;
    }
}
