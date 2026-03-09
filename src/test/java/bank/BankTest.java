package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BankTest {
    private Bank bank;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
    }

    @Test
    public void testBankInitialization() {
        assertNotNull(bank);
        assertNotNull(bank.getAllAccounts());
        assertEquals(0.0, bank.getTotalCash(), 0.01);
        assertTrue(bank.getAllAccounts().isEmpty());
    }

    @Test
    public void testSetSavingsDailyWithdrawalLimit() {
        double limit = 500.0;
        bank.setSavingsDailyWithdrawalLimit(limit);
        assertEquals(limit, bank.getSavingsDailyWithdrawalLimit(), 0.01);
    }

    @Test
    public void testSetSavingsAnnualInterestRate() {
        double rate = 0.02;
        bank.setSavingsAnnualInterestRate(rate);
        assertEquals(rate, bank.getSavingsAnnualInterestRate(), 0.01);
    }

    @Test
    public void testAllAccountsIsNotNull() {
        assertNotNull(bank.getAllAccounts());
    }

    @Test
    public void testTotalCashInitialValue() {
        assertEquals(0.0, bank.getTotalCash(), 0.01);
    }

    @Test
    public void testTotalCashUpdatesWhenAccountIsCreated() {
        double initialCash = bank.getTotalCash();
        Checking account4 = new Checking(4, 100.0, bank);
        assertEquals(initialCash + 100.0, bank.getTotalCash(), 0.01);
    }

    @Test
    public void testTotalCashUpdatesAfterTransaction() {
        Checking account1 = new Checking(1, 500.0, bank);
        Savings account2 = new Savings(2, 200.0, bank);

        double cashBeforeTransaction = bank.getTotalCash();
        account1.withdraw(50.0);
        assertEquals(cashBeforeTransaction - 50.0, bank.getTotalCash(), 0.01);

        account2.deposit(75.0);
        assertEquals(cashBeforeTransaction - 50.0 + 75.0, bank.getTotalCash(), 0.01);
    }

    @Test
    public void testMultipleAccountsCreation() {
        BankAccount account1 = new Checking(1, 100.0, bank);
        BankAccount account2 = new Checking(2, 200.0, bank);
        BankAccount account3 = new Savings(3, 1550.0, bank);

        bank.addAccount(account1);
        bank.addAccount(account2);
        bank.addAccount(account3);

        assertEquals(3, bank.getAllAccounts().size());
        assertTrue(bank.getAllAccounts().contains(account1));
        assertTrue(bank.getAllAccounts().contains(account2));
        assertTrue(bank.getAllAccounts().contains(account3));
    }

    @Test
    public void testTotalCashAccuracy() {
        BankAccount account1 = new Checking(1, 300.0, bank);
        assertEquals(300.0, bank.getTotalCash(), 0.01);

        BankAccount account2 = new Savings(2, 250.0, bank);
        assertEquals(550.0, bank.getTotalCash(), 0.01);

        BankAccount account3 = new Checking(3, 150.0, bank);
        assertEquals(700.0, bank.getTotalCash(), 0.01);
    }
}