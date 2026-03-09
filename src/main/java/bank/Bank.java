package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Bank {

    static HashMap<Integer, BankAccount> allAccounts;
    static double totalCash;

    static double savingsDailyWithdrawalLimit;
    static double savingsAnnualInterestRate;

    public Bank() {
        this.allAccounts = new HashMap<Integer, BankAccount>();
        this.totalCash = 0.0;
        this.savingsDailyWithdrawalLimit = 0.0;
        this.savingsAnnualInterestRate = 0.0;
    }

    public static HashMap<Integer, BankAccount> getAllAccounts() {
        return allAccounts;
    }

    public static double getTotalCash() {
        return totalCash;
    }

    public static void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("account cannot be null");
        }
        allAccounts.put(account.getAccountNumber(), account);
    }

    public static void setSavingsDailyWithdrawalLimit(double limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit cannot be negative");
        }
        savingsDailyWithdrawalLimit = limit;
        Savings.setSavingsDailyWithdrawalLimit(limit);
    }

    public static double getSavingsDailyWithdrawalLimit() {
        return savingsDailyWithdrawalLimit;
    }

    public static void setSavingsAnnualInterestRate(double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException("rate cannot be negative");
        }
        savingsAnnualInterestRate = rate;
        Savings.setSavingsAnnualInterestRate(rate);
    }

    public static double getSavingsAnnualInterestRate() {
        return savingsAnnualInterestRate;
    }

    static void registerInitialBalance(double startingBalance) {
        totalCash += startingBalance;
    }

    static void adjustTotalCash(double delta) {
        totalCash += delta;
    }
}
