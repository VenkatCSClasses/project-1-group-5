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
        this.allAccounts = new HashMap<>();
        this.totalCash = 0.0;
        this.savingsDailyWithdrawalLimit = 0.0;
        this.savingsAnnualInterestRate = 0.0;
    }

    public List<BankAccount> getAllAccounts() {
        return Collections.unmodifiableList(new ArrayList<>(allAccounts.values()));
    }

    public double getTotalCash() {
        return totalCash;
    }

    public void addAccount(BankAccount account) {
        if (account == null) {
            throw new IllegalArgumentException("account cannot be null");
        }

        if (account.getAccountNumber() == null) {
            throw new IllegalArgumentException("account number cannot be null");
        }

        if (allAccounts.containsKey(account.getAccountNumber())) {
            throw new IllegalArgumentException("account number already exists");
        }

        allAccounts.put(account.getAccountNumber(), account);
        totalCash += account.checkBalance();
    }

    public BankAccount getAccount(Integer accountNumber) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("account number cannot be null");
        }

        return allAccounts.get(accountNumber);
    }

    public void removeAccount(Integer accountNumber) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("account number cannot be null");
        }

        BankAccount removedAccount = allAccounts.remove(accountNumber);

        if (removedAccount != null) {
            totalCash -= removedAccount.checkBalance();
        }
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
}