package bank;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Savings implements BankAccount {
    int customerID;
    int accountNum;
    double balance;
    boolean isFrozen;

    Set<Transaction> transactionHistory;
    Set<Transaction> suspiciousActivity;

    private final Bank bank;

    private static double savingsAnnualInterestRate = 0.0;
    private static double savingsDailyWithdrawalLimit = Double.MAX_VALUE;

    private LocalDate lastInterestAppliedDate;
    private LocalDate lastWithdrawalDate;
    private double withdrawnToday;

    public Savings(int customerID, double balance, Bank bank) {
        if (bank == null) {
            throw new IllegalArgumentException("bank cannot be null");
        }

        this.customerID = customerID;
        this.balance = balance;
        this.bank = bank;
        this.isFrozen = false;

        this.accountNum = (int) (Math.random() * 1000000);
        this.transactionHistory = new HashSet<>();
        this.suspiciousActivity = new HashSet<>();

        this.lastInterestAppliedDate = null;
        this.lastWithdrawalDate = null;
        this.withdrawnToday = 0.0;

        this.bank.registerInitialBalance(balance);
    }

    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            Transaction newTransaction = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Deposit",
                    amount,
                    false,
                    false
            );
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Deposit amount must be greater than 0!");
        }

        this.balance += amount;
        this.bank.adjustTotalCash(amount);

        Transaction newTransaction = new Transaction(
                (int) (Math.random() * 1000000),
                (int) (System.currentTimeMillis() / 1000),
                "Deposit",
                amount,
                true,
                false
        );
        this.transactionHistory.add(newTransaction);
    }

    @Override
    public void withdraw(double amount) {
        withdraw(amount, LocalDate.now());
    }

    public void withdraw(double amount, LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }

        if (amount <= 0) {
            Transaction newTransaction = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Withdrawal",
                    amount,
                    false,
                    false
            );
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0!");
        }

        if (this.balance < amount) {
            Transaction newTransaction = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Withdrawal",
                    amount,
                    false,
                    false
            );
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Insufficient funds!");
        }

        if (this.lastWithdrawalDate == null || !this.lastWithdrawalDate.equals(date)) {
            this.withdrawnToday = 0.0;
            this.lastWithdrawalDate = date;
        }

        if (this.withdrawnToday + amount > savingsDailyWithdrawalLimit) {
            Transaction newTransaction = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Withdrawal",
                    amount,
                    false,
                    true
            );
            this.transactionHistory.add(newTransaction);
            this.suspiciousActivity.add(newTransaction);
            throw new IllegalArgumentException("Daily withdrawal limit exceeded!");
        }

        this.balance -= amount;
        this.withdrawnToday += amount;
        this.lastWithdrawalDate = date;
        this.bank.adjustTotalCash(-amount);

        Transaction newTransaction = new Transaction(
                (int) (Math.random() * 1000000),
                (int) (System.currentTimeMillis() / 1000),
                "Withdrawal",
                amount,
                true,
                false
        );
        this.transactionHistory.add(newTransaction);
    }

    @Override
    public void transfer(BankAccount targetAccount, double amount) {
        transfer(targetAccount, amount, LocalDate.now());
    }

    public void transfer(BankAccount targetAccount, double amount, LocalDate date) {
        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null!");
        }

        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }

        if (amount <= 0) {
            Transaction newTransaction = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Transfer",
                    amount,
                    false,
                    false
            );
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Transfer amount must be greater than 0!");
        }

        if (this.balance < amount) {
            Transaction newTransaction = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Transfer",
                    amount,
                    false,
                    false
            );
            this.transactionHistory.add(newTransaction);
            throw new IllegalArgumentException("Insufficient funds!");
        }

        if (this.lastWithdrawalDate == null || !this.lastWithdrawalDate.equals(date)) {
            this.withdrawnToday = 0.0;
            this.lastWithdrawalDate = date;
        }

        if (this.withdrawnToday + amount > savingsDailyWithdrawalLimit) {
            Transaction newTransaction = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Transfer",
                    amount,
                    false,
                    true
            );
            this.transactionHistory.add(newTransaction);
            this.suspiciousActivity.add(newTransaction);
            throw new IllegalArgumentException("Daily withdrawal limit exceeded!");
        }

        this.balance -= amount;
        this.withdrawnToday += amount;
        this.lastWithdrawalDate = date;

        // money leaves this account's bank first
        this.bank.adjustTotalCash(-amount);

        // target deposit will add to its own bank
        targetAccount.deposit(amount);

        Transaction newTransaction = new Transaction(
                (int) (Math.random() * 1000000),
                (int) (System.currentTimeMillis() / 1000),
                "Transfer",
                amount,
                true,
                false
        );
        this.transactionHistory.add(newTransaction);
    }

    public void compoundDailyInterest(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }

        if (lastInterestAppliedDate != null && lastInterestAppliedDate.equals(date)) {
            return;
        }

        double dailyRate = savingsAnnualInterestRate / 365.0;
        double interest = this.balance * dailyRate;

        if (interest != 0.0) {
            this.balance += interest;
            this.bank.adjustTotalCash(interest);

            Transaction newTransaction = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Interest",
                    interest,
                    true,
                    false
            );
            this.transactionHistory.add(newTransaction);
        }

        lastInterestAppliedDate = date;
    }

    @Override
    public double checkBalance() {
        return this.balance;
    }

    @Override
    public java.util.List<Transaction> getSuspiciousActivity() {
        return new java.util.ArrayList<>(this.suspiciousActivity);
    }

    @Override
    public java.util.List<Transaction> getTransactionHistory() {
        return new java.util.ArrayList<>(this.transactionHistory);
    }

    @Override
    public boolean isFrozen() {
        return this.isFrozen;
    }

    @Override
    public String toString() {
        return "Savings Account - Customer ID: " + this.customerID
                + ", Balance: $" + String.format("%.2f", this.balance)
                + ", Frozen: " + this.isFrozen;
    }

    public static void setSavingsAnnualInterestRate(double rate) {
        savingsAnnualInterestRate = rate;
    }

    public static void setSavingsDailyWithdrawalLimit(double limit) {
        savingsDailyWithdrawalLimit = limit;
    }

    public static double getSavingsAnnualInterestRate() {
        return savingsAnnualInterestRate;
    }

    public static double getSavingsDailyWithdrawalLimit() {
        return savingsDailyWithdrawalLimit;
    }
}