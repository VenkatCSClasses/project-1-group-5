package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bank {

    public final Set<BankAccount> allAccounts;
    public double totalCash;

    public double savingsDailyWithdrawalLimit;
    public double savingsAnnualInterestRate;

    public Bank() {
        this.allAccounts = new HashSet<>();
        this.totalCash = 0.0;
        this.savingsDailyWithdrawalLimit = 0.0;
        this.savingsAnnualInterestRate = 0.0;
    }

    public List<BankAccount> getAllAccounts() {
        return Collections.unmodifiableList(new ArrayList<>(allAccounts));
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        allAccounts.add(account);
    }

    public void setSavingsDailyWithdrawalLimit(double limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit cannot be negative");
        }
        this.savingsDailyWithdrawalLimit = limit;
        Savings.setSavingsDailyWithdrawalLimit(limit);
    }

    public double getSavingsDailyWithdrawalLimit() {
        return savingsDailyWithdrawalLimit;
    }

    public void setSavingsAnnualInterestRate(double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("rate cannot be negative");
        }
        this.savingsAnnualInterestRate = rate;
        Savings.setSavingsAnnualInterestRate(rate);
    }

    public double getSavingsAnnualInterestRate() {
        return savingsAnnualInterestRate;
    }

    void registerInitialBalance(double startingBalance) {
        totalCash += startingBalance;
    }

    void adjustTotalCash(double delta) {
        totalCash += delta;
    }
}
