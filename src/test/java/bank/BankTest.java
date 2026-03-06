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
        assertEquals(0, bank.getTotalCash(), 0.01);
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
        assertEquals(0, bank.getTotalCash(), 0.01);
    }
    
    @Test
    public void testTotalCashUpdatesWhenAccountIsCreated() {
        double initialCash = bank.getTotalCash();
        account4 = new Checking(100.0);
        assertEquals(initialCash + 100.0, bank.getTotalCash(), 0.01);
    }
    
    @Test
    public void testTotalCashUpdatesAfterTransaction() {
        Checking account1 = new Checking(500.0);
        Savings account2 = new Savings(200.0);
        
        double cashBeforeTransaction = bank.getTotalCash();
        account1.withdraw(50.0);
        assertEquals(cashBeforeTransaction - 50.0, bank.getTotalCash(), 0.01);
        
        account2.deposit(75.0);
        assertEquals(cashBeforeTransaction - 50.0 + 75.0, bank.getTotalCash(), 0.01);
    }
    
    @Test
    public void testMultipleAccountsCreation() {
        BankAccount account1 = new Checking(100.0);
        BankAccount account2 = new Checking(200.0);
        BankAccount account3 = new Savings(1550.0);
        
        assertEquals(3, bank.getAllAccounts().size());
        assertTrue(bank.getAllAccounts().contains(account1));
        assertTrue(bank.getAllAccounts().contains(account2));
        assertTrue(bank.getAllAccounts().contains(account3));
    }
    
    @Test
    public void testTotalCashAccuracy() {
        BankAccount account1 = new Checking(300.0);
        assertEquals(300.0, bank.getTotalCash(), 0.01);
        
        BankAccount account2 = new Savings(250.0);
        assertEquals(550.0, bank.getTotalCash(), 0.01);
        
        BankAccount account3 = new Checking(150.0);
        assertEquals(700.0, bank.getTotalCash(), 0.01);
    }
}