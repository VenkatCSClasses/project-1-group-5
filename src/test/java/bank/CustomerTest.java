package bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    void constructorTest() {
        Customer customer = new Customer("Dena6", 1234);

        assertEquals("Dena6", customer.getUsername());
        assertEquals(1234, customer.getPin());

        Customer customer2 = new Customer("Guy2", 4321);
        assertEquals(customer.getUserID() + 1, customer2.getUserID());

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Dena6", 4321); // duplicate username
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Dudde", 123); // pin not 4 digits
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Bro", -1234); // pin negative
        });
    }

    @Test
    void changePinTest() {
        Customer customer = new Customer("Dena5", 1234);

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

        assertThrows(IllegalArgumentException.class, () -> {
            customer.changePin(5678, -1234); // negative pin
        });
    }

    @Test
    void getAccountDetailsTest() {
        Customer customer1 = new Customer("Dena3", 1234);
        String expected1 = "Username: Dena3, UserID: " + customer1.getUserID() + ", Accounts: []";
        assertEquals(expected1, customer1.getAccountDetails());

        Customer customer2 = new Customer("Guy1", 4321);

        Checking checking = new Checking(customer2.getUserID(), 1000.0);
        customer2.addAccount(checking);

        int firstAccountNum = checking.getAccountNumber();
        String expected2 = "Username: Guy1, UserID: " + customer2.getUserID()
                + ", Accounts: [" + firstAccountNum + "]";
        assertEquals(expected2, customer2.getAccountDetails());

        Savings savings = new Savings(customer2.getUserID(), 200.0);
        customer2.addAccount(savings);

        int secondAccountNum = savings.getAccountNumber();
        String expected3 = "Username: Guy1, UserID: " + customer2.getUserID()
                + ", Accounts: [" + firstAccountNum + ", " + secondAccountNum + "]";
        assertEquals(expected3, customer2.getAccountDetails());
    }

    @Test
    void addAccountTest() {
        Customer customer = new Customer("Dena7", 1234);

        Checking checking = new Checking(customer.getUserID(), 1000.0);
        customer.addAccount(checking);

        assertEquals(1, customer.getAccounts().size());
        assertEquals(checking, customer.getAccounts().get(0));

        assertThrows(IllegalArgumentException.class, () -> {
            customer.addAccount(null);
        });
    }

    @Test
    void checkBalanceTest() {
        Customer customer = new Customer("Dena2", 1234);

        assertEquals(0.0, customer.checkBalance(0));

        Checking checking = new Checking(customer.getUserID(), 1000.0);
        customer.addAccount(checking);

        int accountNum = checking.getAccountNumber();
        assertEquals(1000.0, customer.checkBalance(accountNum));
    }

    @Test
    void processTransactionTest() {
        Customer customer = new Customer("Dena1", 1234);

        Checking checking = new Checking(customer.getUserID(), 1000.0);
        customer.addAccount(checking);

        int accountNum = checking.getAccountNumber();

        customer.processTransaction(accountNum, 500.0, 1); // deposit
        assertEquals(1500.0, customer.checkBalance(accountNum));

        //customer.processTransaction(accountNum, 200.0, 2); // withdrawal
    //    assertEquals(1300.0, customer.checkBalance(accountNum));

        assertThrows(IllegalArgumentException.class, () -> {
            customer.processTransaction(accountNum, -100.0, 1); // negative amount
        });

        assertThrows(IllegalArgumentException.class, () -> {
            customer.processTransaction(99999, 100.0, 1); // invalid account number
        });

        assertThrows(IllegalArgumentException.class, () -> {
            customer.processTransaction(accountNum, 100.0, 99); // invalid transaction type
        });
    }

    @Test
    void bankTellerConstructorTest() {
        BankTeller teller = new BankTeller("Teller1", 2222);

        assertEquals("Teller1", teller.getUsername());
        assertEquals(2222, teller.getPin());
        assertTrue(teller.getUserID() > 0);

        assertThrows(IllegalArgumentException.class, () -> {
            new BankTeller("Teller1", 3333); // duplicate username
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new BankTeller("BadPin1", 123); // pin not 4 digits
        });

        assertThrows(IllegalArgumentException.class, () -> {
            new BankTeller("BadPin2", -1234); // negative pin
        });
    }
}