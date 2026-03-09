package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Bank {

    private final HashMap<Integer, BankAccount> allAccounts;
    private double totalCash;

    private double savingsDailyWithdrawalLimit;
    private double savingsAnnualInterestRate;

    public Bank() {
        this.allAccounts = new HashMap<Integer, BankAccount>();
        this.totalCash = 0.0;
        this.savingsDailyWithdrawalLimit = 0.0;
        this.savingsAnnualInterestRate = 0.0;
    }

    public HashMap<Integer, BankAccount> getAllAccounts() {
        return allAccounts;
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        allAccounts.put(account.getAccountNumber(), account);
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
