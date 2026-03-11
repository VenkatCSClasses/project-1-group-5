package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTellerTest {

    private BankTeller bankTeller;
    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bank.addCustomer(new Customer("Test Customer", 1234));
        bankTeller = new BankTeller("Test Teller", 9087);
        bank.addCustomer(bankTeller);

        bank.setSavingsAnnualInterestRate(0.365);
        bank.setSavingsDailyWithdrawalLimit(500.0);
    }

    @Test
    void createAccountTest() {
        int beforeSize = Bank.allAccounts.size();

        bankTeller.createAccount(1, 1000.0, 1);

        assertEquals(beforeSize + 1, Bank.allAccounts.size());

        BankAccount createdAccount = Bank.allAccounts.values().iterator().next();
        assertNotNull(createdAccount);
        assertEquals(1, createdAccount.getCustomerID());
        assertEquals(1000.0, createdAccount.checkBalance(), 0.000001);
        assertTrue(createdAccount instanceof Checking);
        assertFalse(createdAccount.isFrozen());

        assertThrows(IllegalArgumentException.class, () -> bankTeller.createAccount(1, 1000.0, 99));
    }

    @Test
    void closeAccountTest() {
        bankTeller.createAccount(1, 1000.0, 1);

        BankAccount createdAccount = Bank.allAccounts.values().iterator().next();
        int accountNumber = createdAccount.getAccountNumber();

        assertTrue(Bank.allAccounts.containsKey(accountNumber));

        bankTeller.closeAccount(accountNumber);

        assertFalse(Bank.allAccounts.containsKey(accountNumber));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.closeAccount(99));
    }

    @Test
    void processTransactionTest() {
        bankTeller.createAccount(1, 1000.0, 1);

        BankAccount createdAccount = Bank.allAccounts.values().iterator().next();
        int accountNumber = createdAccount.getAccountNumber();

        bankTeller.processTransaction(accountNumber, 500.0, 1); // deposit
        assertEquals(1500.0, Bank.allAccounts.get(accountNumber).checkBalance(), 0.000001);

        bankTeller.processTransaction(accountNumber, 200.0, 2); // withdraw
        assertEquals(1300.0, Bank.allAccounts.get(accountNumber).checkBalance(), 0.000001);

        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(99, 100.0, 1));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(accountNumber, -100.0, 1));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(accountNumber, 100.0, 99));
    }
}