package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    void createAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        assertEquals(1, bank.getAllAccounts().get(0).getCustomerID());
        assertEquals(1000.0, bank.getAccount(1).checkBalance());
        assertTrue(bank.getAccount(1) instanceof Checking);
        assertFalse(bank.getAccount(1).isFrozen());
        assertThrows(IllegalArgumentException.class, () -> bankTeller.createAccount(0, 1000.0, 1));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.createAccount(1, -1000.0, 1));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.createAccount(2, 1000.0, 99));
    }

    @Test
    void closeAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        bankTeller.closeAccount(1);
        assert bank.getAccount(1) == null;
        assertThrows(IllegalArgumentException.class, () -> bankTeller.closeAccount(99));
    }

    @Test
    void processTransactionTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        bankTeller.processTransaction(1, 500.0, 1); // deposit
        assert bank.getAccount(1).checkBalance() == 1500.0;
        bankTeller.processTransaction(1, 200.0, 2); // withdrawal
        assert bank.getAccount(1).checkBalance() == 1300.0;
        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(99, 100.0, 1));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(1, -100.0, 1));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(1, 100.0, 99));
    }
}
