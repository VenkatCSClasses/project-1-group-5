package bank;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.lang.IllegalArgumentException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerTest {

    BankTeller bankTeller = new BankTeller();

    @Test
    void processTransactionTest(){
        Customer customer = new Customer("Dena", 12345, 1234);
        bankTeller.createAccount(12345, 1000.0, 1);
        customer.processTransaction(12345, 500.0, 1); // deposit
        assertEquals(1500.0, customer.checkBalance(67890));
        customer.processTransaction(67890, 200.0,0); // withdrawal
        assertEquals(1300.0, customer.checkBalance(67890));
        //invalid cases
        assertThrows(IllegalArgumentException.class, () -> {
            customer.processTransaction(11111, 100.0, 1); //different account number
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            customer.processTransaction(67890, -100.0, 1); //negative amount
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            customer.processTransaction(67890, 100.0, 99); //invalid transaction type
        });
    }

    @Test
    void checkBalanceTest(){
        Customer customer = new Customer("Dena", 12345, 1234);
        assertEquals(0.0, customer.checkBalance(67890));
        bankTeller.createAccount(12345, 1000.0, 1);
        assertEquals(1000.0, customer.checkBalance(67890));
        int accountNum = customer.getAccounts().get(0).getAccountNumber();
        customer.processTransaction(accountNum, 500.0, 1); // deposit
        assertEquals(1500.0, customer.checkBalance(67890));
        customer.processTransaction(accountNum, 200.0,0); // withdrawal
        assertEquals(1300.0, customer.checkBalance(67890));
    }

    @Test
    void getAccountDetailsTest(){
        Customer customer1 = new Customer("Dena", 12345, 1234);
        String expected1 = "Username: Dena, UserID: 12345, Accounts: []";
        String actual1 = customer1.getAccountDetails();
        assertEquals(expected1, actual1);

        Customer customer2 = new Customer("Guy", 54321, 4321);
        bankTeller.createAccount("54321", 1000.0, 1);
        String expected2 = "Username: Guy, UserID: 54321, Accounts: [98765]";
        String actual2 = customer2.getAccountDetails();
        assertEquals(expected2, actual2);
        bankTeller.createAccount("54321", 200, 0);
        String expected3 = "Username: Guy, UserID: 54321, Accounts: [98765, 98766]";
        String actual3 = customer2.getAccountDetails();
        assertEquals(expected3, actual3);
    }

    @Test
    void changePinTest(){
        Customer customer = new Customer("Dena", 12345, 1234);
        customer.changePin(1234,5678);
        assertEquals(5678, customer.getPin());
        //invalid cases
        assertThrows(IllegalArgumentException.class, () -> {
            customer.changePin(1111, 2222); //different original pin
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            customer.changePin(5678, 12345); //new pin is not 4 digits
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            customer.changePin(5678, 123); //new pin is not 4 digits
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            customer.changePin(5678, 5678); //new pin is the same as original pin
        });
        assertThrows(IllegalArgumentException.class, ()-> {
            customer.changePin(5678, -1234); //new pin is negative
        });
    }

    @Test
    void constructorTest(){
        Customer customer = new Customer("Dena", 12345, 1234);
        assertEquals("Dena", customer.getUsername());
        assertEquals(12345, customer.getUserID());
        assertEquals(1234, customer.getPin());
        //invalid cases
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Dena", 54321, 4321); //duplicate username
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Guy", 12345, 4321); //duplicate userID
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Dudde", 67890, 123); //pin is not 4 digits
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Customer("Bro", 67891, -1234); //pin is negative
        });
    }
}
