package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AtmTest {

    private Bank bank;
    private Atm atm;

    @BeforeEach
    void setUp() {
        bank = new Bank();
        atm = new Atm("Main Street", 10, bank);
    }

    @Test
    void validateCredentialsTest() {
        Customer customer = new Customer("DenaATM2", 4321);
        bank.addCustomer(customer);

        assertTrue(atm.validateCredentials(customer.getUserID(), 4321));
        assertFalse(atm.validateCredentials(customer.getUserID(), 1111));
        assertFalse(atm.validateCredentials(customer.getUserID(), -1234));
        assertFalse(atm.validateCredentials(customer.getUserID(), 12345));
        assertFalse(atm.validateCredentials(customer.getUserID(), 0));
    }

    @Test
    void processTransactionTest() {
        Customer customer = new Customer("DenaATM1", 1234);
        bank.addCustomer(customer);

        Checking checking = new Checking(customer.getUserID(), 1000.0);
        customer.addAccount(checking);

        Atm.processTransaction(checking, 500.0, 1); // deposit
        assertEquals(1500.0, customer.checkBalance(checking.getAccountNumber()));

        Atm.processTransaction(checking, 200.0, 0); // withdraw
        assertEquals(1300.0, customer.checkBalance(checking.getAccountNumber()));

        assertThrows(IllegalArgumentException.class, () -> {
            Atm.processTransaction(checking, 100.0, 99); // invalid transaction type
        });
    }
}