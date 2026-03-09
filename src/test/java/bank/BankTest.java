package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

    private Bank bank;
    private Savings savings1;
    private Savings savings2;

    @BeforeEach
    public void setUp() {
        bank = new Bank();
        savings1 = new Savings(1, 1000.0);
        savings2 = new Savings(2, 500.0);
    }

    @Test
    public void testBankInitialization() {
        assertNotNull(bank);
        assertNotNull(bank.getAllAccounts());
        assertEquals(0, bank.getTotalCash(), 0.000001);
        assertTrue(bank.getAllAccounts().isEmpty());
        assertEquals(0.0, bank.getSavingsDailyWithdrawalLimit(), 0.000001);
        assertEquals(0.0, bank.getSavingsAnnualInterestRate(), 0.000001);
    }

    @Test
    public void testSetSavingsDailyWithdrawalLimit() {
        double limit = 500.0;
        bank.setSavingsDailyWithdrawalLimit(limit);
        assertEquals(limit, bank.getSavingsDailyWithdrawalLimit(), 0.000001);
    }

    @Test
    public void testSetSavingsDailyWithdrawalLimitThrowsWhenNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> bank.setSavingsDailyWithdrawalLimit(-1.0));
    }

    @Test
    public void testSetSavingsAnnualInterestRate() {
        double rate = 0.02;
        bank.setSavingsAnnualInterestRate(rate);
        assertEquals(rate, bank.getSavingsAnnualInterestRate(), 0.000001);
    }

    @Test
    public void testSetSavingsAnnualInterestRateThrowsWhenNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> bank.setSavingsAnnualInterestRate(-0.01));
    }

    @Test
    public void testAddAccount() {
        bank.addAccount(savings1);

        assertEquals(1, bank.getAllAccounts().size());
        assertEquals(1000.0, bank.getTotalCash(), 0.000001);
        assertTrue(bank.getAllAccounts().contains(savings1));
    }

    @Test
    public void testAddMultipleAccounts() {
        bank.addAccount(savings1);
        bank.addAccount(savings2);

        assertEquals(2, bank.getAllAccounts().size());
        assertEquals(1500.0, bank.getTotalCash(), 0.000001);
    }

    @Test
    public void testAddAccountThrowsWhenNull() {
        assertThrows(IllegalArgumentException.class, () -> bank.addAccount(null));
        assertEquals(0, bank.getAllAccounts().size());
        assertEquals(0.0, bank.getTotalCash(), 0.000001);
    }

    @Test
    public void testAddAccountThrowsWhenDuplicateAccountNumber() {
        bank.addAccount(savings1);

        BankAccount duplicate = new BankAccount() {
            @Override
            public void deposit(double amount) { }

            @Override
            public void withdraw(double amount) { }

            @Override
            public void transfer(BankAccount targetAccount, double amount) { }

            @Override
            public double checkBalance() {
                return 200.0;
            }

            @Override
            public java.util.List<Transaction> getSuspiciousActivity() {
                return java.util.Collections.emptyList();
            }

            @Override
            public java.util.List<Transaction> getTransactionHistory() {
                return java.util.Collections.emptyList();
            }

            @Override
            public boolean isFrozen() {
                return false;
            }

            @Override
            public Integer getAccountNumber() {
                return savings1.getAccountNumber();
            }

            @Override
            public String toString() {
                return "Duplicate Account";
            }
        };

        assertThrows(IllegalArgumentException.class, () -> bank.addAccount(duplicate));
        assertEquals(1, bank.getAllAccounts().size());
        assertEquals(1000.0, bank.getTotalCash(), 0.000001);
    }

    @Test
    public void testGetAccountReturnsCorrectAccount() {
        bank.addAccount(savings1);
        bank.addAccount(savings2);

        BankAccount found = bank.getAccount(savings1.getAccountNumber());

        assertNotNull(found);
        assertEquals(savings1, found);
    }

    @Test
    public void testGetAccountReturnsNullWhenMissing() {
        assertNull(bank.getAccount(999999));
    }

    @Test
    public void testGetAccountThrowsWhenNullAccountNumber() {
        assertThrows(IllegalArgumentException.class, () -> bank.getAccount(null));
    }

    @Test
    public void testRemoveAccountRemovesExistingAccount() {
        bank.addAccount(savings1);
        bank.addAccount(savings2);

        bank.removeAccount(savings1.getAccountNumber());

        assertEquals(1, bank.getAllAccounts().size());
        assertNull(bank.getAccount(savings1.getAccountNumber()));
        assertEquals(savings2, bank.getAccount(savings2.getAccountNumber()));
        assertEquals(500.0, bank.getTotalCash(), 0.000001);
    }

    @Test
    public void testRemoveAccountDoesNothingWhenMissing() {
        bank.addAccount(savings1);

        bank.removeAccount(999999);

        assertEquals(1, bank.getAllAccounts().size());
        assertEquals(1000.0, bank.getTotalCash(), 0.000001);
    }

    @Test
    public void testRemoveAccountThrowsWhenNullAccountNumber() {
        assertThrows(IllegalArgumentException.class, () -> bank.removeAccount(null));
    }

    @Test
    public void testGetAllAccountsIsUnmodifiable() {
        bank.addAccount(savings1);

        assertThrows(UnsupportedOperationException.class, () ->
                bank.getAllAccounts().add(savings2));
    }
}