package bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.lang.IllegalArgumentException;

public class BankTellerTest {

    private BankTeller bankTeller;
    private Bank bank;    

    @Test
    void createAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        assertEquals(1, bank.getAccountNumber(1).getCustomerNumber());
        assertEquals(1000.0, bank.getAccount(1).getBalance());
        assertEquals(1, bank.getAccount(1).getAccountType());
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
        assert bank.getAccount(1).getBalance() == 1500.0;
        bankTeller.processTransaction(1, 200.0, 2); // withdrawal
        assert bank.getAccount(1).getBalance() == 1300.0;
        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(99, 100.0, 1));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(1, -100.0, 1));
        assertThrows(IllegalArgumentException.class, () -> bankTeller.processTransaction(1, 100.0, 99));
    }
}
