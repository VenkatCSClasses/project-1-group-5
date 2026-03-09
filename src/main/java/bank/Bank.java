package bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Bank {

    // static registry used by teller/admin helpers
    public static HashMap<Integer, BankAccount> allAccounts = new HashMap<>();
    
    private final HashMap<Integer, BankAccount> instanceAccounts;
    private final HashMap<Integer, Customer> allCustomers;
    private double totalCash;

    private double savingsDailyWithdrawalLimit;
    private double savingsAnnualInterestRate;

    public Bank() {
        // clear static map when new instance is created so that tests start fresh
        allAccounts.clear();
        this.instanceAccounts = new HashMap<>();
        this.allCustomers = new HashMap<>();
        this.totalCash = 0.0;
        this.savingsDailyWithdrawalLimit = 0.0;
        this.savingsAnnualInterestRate = 0.0;
    }

    public List<BankAccount> getAllAccounts() {
        return Collections.unmodifiableList(new ArrayList<>(instanceAccounts.values()));
    }

  

    public double getTotalCash() {
        return totalCash;
    }

    public void addAccount(BankAccount account) {
        // delegate to static registry so that teller/admin helpers work
        addAccountStatic(account);
        // keep instance state in sync as well
        instanceAccounts.put(account.getAccountNumber(), account);
        totalCash += account.checkBalance();
    }

    public static void addAccountStatic(BankAccount account) {
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
    }

    public BankAccount getAccount(Integer accountNumber) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("account number cannot be null");
        }

        return instanceAccounts.get(accountNumber);
    }

    public static BankAccount getAccountStatic(Integer accountNumber) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("account number cannot be null");
        }

        return allAccounts.get(accountNumber);
    }

    public void removeAccount(Integer accountNumber) {
        if (accountNumber == null) {
            throw new IllegalArgumentException("account number cannot be null");
        }

        BankAccount removedAccount = instanceAccounts.remove(accountNumber);
        allAccounts.remove(accountNumber);

        if (removedAccount != null) {
            totalCash -= removedAccount.checkBalance();
        }
    }

     public void addCustomer(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("customer cannot be null");
         }

         if (customer.getUserID() <= 0) {
             throw new IllegalArgumentException("user ID must be positive");
         }

         if (allCustomers.containsKey(customer.getUserID())) {
             throw new IllegalArgumentException("user ID already exists");
         }

         allCustomers.put(customer.getUserID(), customer);
     }

     public Customer getCustomer(Integer userID) {
        if (userID == null) {
            throw new IllegalArgumentException("user ID cannot be null");
        }

        return allCustomers.get(userID);
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