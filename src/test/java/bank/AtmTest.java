package bank;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.IllegalArgumentException;

public class AtmTest {
    private Atm atm = new Atm("Main Street", 10);

    @Test
    void validateCredentialsTest(){
        Customer customer = new Customer("Dena", 12345, 1234);
        // valid credentials
        boolean result1 = atm.validateCredentials(12345, 1234);
        assert(result1);
        // invalid credentials
        boolean result2 = atm.validateCredentials(12345, 4321);
        assert(!result2);
        assertThrows(IllegalArgumentException.class, () -> {
            atm.validateCredentials(12345, -1234); //negative pin
        });
        assertThrows(IllegalArgumentException.class, () -> {
            atm.validateCredentials(12345, 12345); //pin with more than 4 digits
        });
        assertThrows(IllegalArgumentException.class, () -> {
            atm.validateCredentials(12345, 0); //pin with less than 4 digits
        });
    }

    @Test
    void processTransactionTest(){
        BankTeller bankTeller = new BankTeller();
        Customer customer = new Customer("Dena", 12345, 1234);
        bankTeller.createAccount(12345, 1000.0, 1);
        // get the generated account number from customer
        int accountNum = customer.getAccounts().values().iterator().next().getAccountNumber();
        atm.processTransaction(accountNum, 500.0, 1); // deposit
        assertEquals(1500.0, customer.checkBalance(accountNum));
        atm.processTransaction(accountNum, 200.0,0); // withdrawal
        assertEquals(1300.0, customer.checkBalance(accountNum));
        //invalid cases
        assertThrows(IllegalArgumentException.class, () -> {
            atm.processTransaction(11111, 100.0, 1); //different account number
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            atm.processTransaction(67890, -100.0, 1); //negative amount
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            atm.processTransaction(67890, 100.0, 99); //invalid transaction type
        });
}
    }
