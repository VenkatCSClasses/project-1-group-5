package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    BankTeller bankTeller = new BankTeller();

    @Test
    void processTransactionTest() {
        Customer customer = new Customer("Dena1", 12345, 1234);

        // assuming this creates an account for customer 12345
        bankTeller.createAccount(12345, 1000.0, 1);

        // with your current Customer code, accountNumber is being used like a list index
        customer.processTransaction(0, 500.0, 1); // deposit
        assertEquals(1500.0, customer.checkBalance(0));

        customer.processTransaction(0, 200.0, 0); // withdrawal
        assertEquals(1300.0, customer.checkBalance(0));

        assertThrows(IllegalArgumentException.class, () -> {
            customer.processTransaction(0, -100.0, 1); // negative amount
        });

        // this may throw IndexOutOfBoundsException instead of IllegalArgumentException
        assertThrows(IndexOutOfBoundsException.class, () -> {
            customer.processTransaction(5, 100.0, 1); // invalid account index
        });
    }

    @Test
    void checkBalanceTest() {
        Customer customer = new Customer("Dena2", 12346, 1234);

        assertEquals(0.0, customer.checkBalance(0));

        bankTeller.createAccount(12346, 1000.0, 1);
        assertEquals(1000.0, customer.checkBalance(0));

        customer.processTransaction(0, 500.0, 1); // deposit
        assertEquals(1500.0, customer.checkBalance(0));

        customer.processTransaction(0, 200.0, 0); // withdrawal
        assertEquals(1300.0, customer.checkBalance(0));
    }

    @Test
    void getAccountDetailsTest() {
        Customer customer1 = new Customer("Dena3", 12347, 1234);
        String expected1 = "Username: Dena3, UserID: 12347, Accounts: []";
        String actual1 = customer1.getAccountDetails();
        assertEquals(expected1, actual1);

        Customer customer2 = new Customer("Guy1", 54321, 4321);
        bankTeller.createAccount(54321, 1000.0, 1);

        int firstAccountNum = customer2.getAccounts().get(0).getAccountNumber();
        String expected2 = "Username: Guy1, UserID: 54321, Accounts: [" + firstAccountNum + "]";
        String actual2 = customer2.getAccountDetails();
        assertEquals(expected2, actual2);

        bankTeller.createAccount(54321, 200.0, 0);
        int secondAccountNum = customer2.getAccounts().get(1).getAccountNumber();

        String expected3 = "Username: Guy1, UserID: 54321, Accounts: [" + firstAccountNum + ", " + secondAccountNum + "]";
        String actual3 = customer2.getAccountDetails();
        assertEquals(expected3, actual3);
    }

    @Test
    void changePinTest() {
        Customer customer = new Customer("Dena5", 12348, 1234);

        customer.changePin(1234, 5678);
        assertEquals(5678, customer.getPin());

        assertThrows(IllegalArgumentException.class, () -> {
            customer.changePin(1111, 2222); // wrong original pin
        });

        assertThrows(IllegalArgumentException.class, () -> {
            customer.changePin(5678, 12345); // not 4 digits
        });

        assertThrows(IllegalArgumentException.class, () -> {
            customer.changePin(5678, 123); // not 4 digits
        });

        assertThrows(IllegalArgumentException.class, () -> {
            customer.changePin(5678, 5678); // same as original
        });

        // with your current code, negative pin will fail because length != 4
        assertThrows(IllegalArgumentException.class, () -> {
            customer.changePin(5678, -1234);
        });
    }

    @Test
    void constructorTest() {
        Customer customer = new Customer("Dena6", 12349, 1234);

        assertEquals("Dena6", customer.getUsername());
        assertEquals(12349, customer.getUserID());
        assertEquals(1234, customer.getPin());

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Dena6", 54321, 4321); // duplicate username
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Guy2", 12349, 4321); // duplicate userID
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Dudde", 67890, 123); // pin not 4 digits
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Bro", 67891, -1234); // pin negative
        });
    }
}