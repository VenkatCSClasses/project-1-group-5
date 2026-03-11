package bank;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankAdminTest {

    private BankAdmin bankAdmin;
    private Bank bank;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        bankAdmin = new BankAdmin("Admin User", 1893);
    }

    @Test
    void createAccountTest() {
        int beforeSize = Bank.allAccounts.size();

        bankAdmin.createAccount(1, 1000.0, 1);

        assertEquals(beforeSize + 1, Bank.allAccounts.size());

        BankAccount createdAccount = Bank.allAccounts.values().iterator().next();
        assertEquals(1000.0, createdAccount.checkBalance(), 0.000001);
        assertTrue(createdAccount instanceof Checking);

        assertThrows(IllegalArgumentException.class,
                () -> bankAdmin.createAccount(1, 1000.0, 99));
    }

    @Test
    void closeAccountTest() {
        bankAdmin.createAccount(1, 1000.0, 1);

        BankAccount createdAccount = Bank.allAccounts.values().iterator().next();
        int accountNumber = createdAccount.getAccountNumber();

        assertTrue(Bank.allAccounts.containsKey(accountNumber));

        bankAdmin.closeAccount(accountNumber);

        assertFalse(Bank.allAccounts.containsKey(accountNumber));

        assertThrows(IllegalArgumentException.class,
                () -> bankAdmin.closeAccount(99999));
    }

    @Test
    void processTransactionTest() {
        bankAdmin.createAccount(1, 1000.0, 1);

        BankAccount createdAccount = Bank.allAccounts.values().iterator().next();
        int accountNumber = createdAccount.getAccountNumber();

        bankAdmin.processTransaction(accountNumber, 500.0, 1);
        assertEquals(1500.0, Bank.allAccounts.get(accountNumber).checkBalance(), 0.000001);

        bankAdmin.processTransaction(accountNumber, 200.0, 2);
        assertEquals(1300.0, Bank.allAccounts.get(accountNumber).checkBalance(), 0.000001);

        assertThrows(IllegalArgumentException.class,
                () -> bankAdmin.processTransaction(99999, 100.0, 1));

        assertThrows(IllegalArgumentException.class,
                () -> bankAdmin.processTransaction(accountNumber, 100.0, 99));
    }

    @Test
    void calculateTotalAssetsTest() {
        bankAdmin.createAccount(1, 1000.0, 1);
        bankAdmin.createAccount(2, 2000.0, 1);

        double totalAssets = bankAdmin.calculateTotalAssets();

        assertEquals(3000.0, totalAssets, 0.000001);
    }

    @Test
    void toggleFreezeAccountTest() {
        bankAdmin.createAccount(1, 1000.0, 1);

        BankAccount createdAccount = Bank.allAccounts.values().iterator().next();
        int accountNumber = createdAccount.getAccountNumber();

        assertFalse(Bank.allAccounts.get(accountNumber).isFrozen());

        bankAdmin.toggleFreezeAccount(accountNumber);
        assertTrue(Bank.allAccounts.get(accountNumber).isFrozen());

        bankAdmin.toggleFreezeAccount(accountNumber);
        assertFalse(Bank.allAccounts.get(accountNumber).isFrozen());

        assertThrows(IllegalArgumentException.class,
                () -> bankAdmin.toggleFreezeAccount(99999));
    }

    @Test
    void getSuspiciousActivityReportTest() {
        bankAdmin.createAccount(1, 1000.0, 1);

        BankAccount createdAccount = Bank.allAccounts.values().iterator().next();
        int accountNumber = createdAccount.getAccountNumber();

        List<Transaction> suspiciousActivity = bankAdmin.getSuspiciousActivityReport(accountNumber);
        assertEquals(Bank.allAccounts.get(accountNumber).getSuspiciousActivity(), suspiciousActivity);

        assertThrows(IllegalArgumentException.class,
                () -> bankAdmin.getSuspiciousActivityReport(99999));
    }
}