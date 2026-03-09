package bank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Savings implements BankAccount {

    private int customerID;
    private int accountNum;
    private double balance;
    private boolean isFrozen;

    private final Bank bank;

    private List<Transaction> transactionHistory;
    private List<Transaction> suspiciousActivity;

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
        this.transactionHistory = new ArrayList<>();
        this.suspiciousActivity = new ArrayList<>();

        this.lastInterestAppliedDate = null;
        this.lastWithdrawalDate = null;
        this.withdrawnToday = 0.0;
    }

    @Override
    public void deposit(double amount) {
        if (isFrozen) {
            throw new IllegalStateException("Account is frozen!");
        }

        if (amount <= 0) {
            Transaction t = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Deposit",
                    amount,
                    false,
                    false
            );
            transactionHistory.add(t);
            throw new IllegalArgumentException("Deposit amount must be greater than 0!");
        }

        balance += amount;

        Transaction t = new Transaction(
                (int) (Math.random() * 1000000),
                (int) (System.currentTimeMillis() / 1000),
                "Deposit",
                amount,
                true,
                false
        );
        transactionHistory.add(t);
    }

    @Override
    public void withdraw(double amount) {
        withdraw(amount, LocalDate.now());
    }

    public void withdraw(double amount, LocalDate date) {
        if (isFrozen) {
            throw new IllegalStateException("Account is frozen!");
        }

        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }

        if (amount <= 0) {
            Transaction t = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Withdrawal",
                    amount,
                    false,
                    false
            );
            transactionHistory.add(t);
            throw new IllegalArgumentException("Withdrawal amount must be greater than 0!");
        }

        if (balance < amount) {
            Transaction t = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Withdrawal",
                    amount,
                    false,
                    false
            );
            transactionHistory.add(t);
            throw new IllegalArgumentException("Insufficient funds!");
        }

        if (lastWithdrawalDate == null || !lastWithdrawalDate.equals(date)) {
            withdrawnToday = 0.0;
            lastWithdrawalDate = date;
        }

        if (withdrawnToday + amount > bank.getSavingsDailyWithdrawalLimit()) {
            Transaction t = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Withdrawal",
                    amount,
                    false,
                    true
            );
            transactionHistory.add(t);
            suspiciousActivity.add(t);
            throw new IllegalArgumentException("Daily withdrawal limit exceeded!");
        }

        balance -= amount;
        withdrawnToday += amount;

        Transaction t = new Transaction(
                (int) (Math.random() * 1000000),
                (int) (System.currentTimeMillis() / 1000),
                "Withdrawal",
                amount,
                true,
                false
        );
        transactionHistory.add(t);
    }

    @Override
    public void transfer(BankAccount targetAccount, double amount) {
        transfer(targetAccount, amount, LocalDate.now());
    }

    public void transfer(BankAccount targetAccount, double amount, LocalDate date) {
        if (isFrozen) {
            throw new IllegalStateException("Account is frozen!");
        }

        if (targetAccount == null) {
            throw new IllegalArgumentException("Target account cannot be null!");
        }

        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }

        if (amount <= 0) {
            Transaction t = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Transfer",
                    amount,
                    false,
                    false
            );
            transactionHistory.add(t);
            throw new IllegalArgumentException("Transfer amount must be greater than 0!");
        }

        if (balance < amount) {
            Transaction t = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Transfer",
                    amount,
                    false,
                    false
            );
            transactionHistory.add(t);
            throw new IllegalArgumentException("Insufficient funds!");
        }

        if (lastWithdrawalDate == null || !lastWithdrawalDate.equals(date)) {
            withdrawnToday = 0.0;
            lastWithdrawalDate = date;
        }

        if (withdrawnToday + amount > bank.getSavingsDailyWithdrawalLimit()) {
            Transaction t = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Transfer",
                    amount,
                    false,
                    true
            );
            transactionHistory.add(t);
            suspiciousActivity.add(t);
            throw new IllegalArgumentException("Daily withdrawal limit exceeded!");
        }

        balance -= amount;
        withdrawnToday += amount;
        targetAccount.deposit(amount);

        Transaction t = new Transaction(
                (int) (Math.random() * 1000000),
                (int) (System.currentTimeMillis() / 1000),
                "Transfer",
                amount,
                true,
                false
        );
        transactionHistory.add(t);
    }

    public void compoundDailyInterest(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null");
        }

        if (lastInterestAppliedDate != null && lastInterestAppliedDate.equals(date)) {
            return;
        }

        double dailyRate = bank.getSavingsAnnualInterestRate() / 365.0;
        double interest = balance * dailyRate;

        if (interest != 0.0) {
            balance += interest;

            Transaction t = new Transaction(
                    (int) (Math.random() * 1000000),
                    (int) (System.currentTimeMillis() / 1000),
                    "Interest",
                    interest,
                    true,
                    false
            );
            transactionHistory.add(t);
        }

        lastInterestAppliedDate = date;
    }

    @Override
    public double checkBalance() {
        return balance;
    }

    @Override
    public List<Transaction> getSuspiciousActivity() {
        return new ArrayList<>(suspiciousActivity);
    }

    @Override
    public List<Transaction> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);
    }

    @Override
    public boolean isFrozen() {
        return isFrozen;
    }

    @Override
    public Integer getAccountNumber() {
        return accountNum;
    }

    @Override
    public String toString() {
        return "Savings Account - Customer ID: " + customerID
                + ", Balance: $" + String.format("%.2f", balance)
                + ", Frozen: " + isFrozen;
    }
}