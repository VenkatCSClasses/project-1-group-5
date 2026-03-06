package bank;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import bank.Checking;


public class CheckingTest {
    @Test
    public void testCheckingConstructor(){
        assertTrue( new Checking(12345), "Customer ID 12345 has a checking account of 0.0 balance");
        assertTrue( new Checking(12345, 100.0), "Customer ID 12345 has a checking account of 100.0 balance");
        assertTrue( new Checking(19837, 100.0), "Customer ID 19837 has a checking account of 100.0 balance");

    }

    public void testDepositWithdraw&CheckBalance(){
        Checking testCheckingAccount1 = new Checking(189432, 0.0);
        testCheckingAccount1.deposit(100.0);
        assertEquals(100.0, testCheckingAccount1.checkBalance(), "Balance is 100.0", 0.001);
        testCheckingAccount1.deposit(50.0);
        assertEquals(150.0, testCheckingAccount1.checkBalance(), "Balance is 150.0", 0.001);

        // test invalid deposit amounts
        assertThrows(IllegalArgumentException.class, () -> testCheckingAccount1.deposit(-50.0), "Deposit amount must be greater than 0!");
        assertThrows(IllegalArgumentException.class, () -> testCheckingAccount1.deposit(0.0), "Deposit amount must be greater than 0!");

     

        testCheckingAccount1.withdraw(50.0);
        assertEquals(100.0, testCheckingAccount1.checkBalance(), "Balance is 100.0", 0.001);
        testCheckingAccount1.withdraw(25.0);
        assertEquals(75.0, testCheckingAccount1.checkBalance(), "Balance is 75.0", 0.001);
        
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(100.0), "Insufficient funds!");

        // test invalid withdrawal amounts
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(-50.0), "Withdrawal amount must be greater than 0!");
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(0.0), "Withdrawal amount must be greater than 0!");


        Checking testCheckingAccount2 = new Checking(189432, 100.0);
        testCheckingAccount2.deposit(300.0);
        assertEquals(400.0, testCheckingAccount2.checkBalance(), "Balance is 400.0", 0.001);

        //test floating point precision
        testCheckingAccount2.deposit(14.02);
        assertEquals(414.02, testCheckingAccount2.checkBalance(), "Balance is 414.02", 0.001);
        testCheckingAccount2.withdraw(50.78);
        assertEquals(363.24, testCheckingAccount2.checkBalance(), "Balance is 363.24", 0.001);
        testCheckingAccount2.withdraw(0.24);
        assertEquals(363.0, testCheckingAccount2.checkBalance(), "Balance is 363.0", 0.001);



    }

    public void testTransfer(){
        Checking testsCheckingAccount1 = new Checking(189432, 190.0);
        Checking testsCheckingAccount2 = new Checking(19837, 100.0);

        testsCheckingAccount1.transfer(testsCheckingAccount2, 50.0);
        assertEquals(140.0, testsCheckingAccount1.checkBalance(), "Balance is 140.0", 0.001);
        assertEquals(150.0, testsCheckingAccount2.checkBalance(), "Balance is 150.0", 0.001);

    }

    public void testsTransferInvalidAmounts(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);

        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, -50.0), "Transfer amount must be greater than 0!");
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, 0.0), "Transfer amount must be greater than 0!");
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, 500.0), "Insufficient funds!");

    }

    public void testTransferInvalidTargetAccount(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        BankAccount invalidTargetAccount = null;

        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(invalidTargetAccount, 50.0), "Target account cannot be null!");

    }









    
    




    
}
