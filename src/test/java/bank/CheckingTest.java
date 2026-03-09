package bank;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
 




public class CheckingTest {
    @Test
    public void testCheckingConstructor(){
        assertEquals ( new Checking(12345, 0.0), "Customer ID 12345 has a checking account of 0.0 balance");
        assertEquals( new Checking(12345, 100.0), "Customer ID 12345 has a checking account of 100.0 balance");
        assertEquals( new Checking(19837, 100.0), "Customer ID 19837 has a checking account of 100.0 balance");

    }

    public void testDepositWithdrawCheckBalance(){
        Checking testCheckingAccount1 = new Checking(189432, 0.0);
        testCheckingAccount1.deposit(100.0);
        assertEquals(100.0, testCheckingAccount1.checkBalance(), 0.001, "Balance is 100.0");
        testCheckingAccount1.deposit(50.0);
        assertEquals(150.0, testCheckingAccount1.checkBalance(), 0.001, "Balance is 150.0");

        // test invalid deposit amounts
        assertThrows(IllegalArgumentException.class, () -> testCheckingAccount1.deposit(-50.0), "Deposit amount must be greater than 0!");
        assertThrows(IllegalArgumentException.class, () -> testCheckingAccount1.deposit(0.0), "Deposit amount must be greater than 0!");

     

        testCheckingAccount1.withdraw(50.0);
        assertEquals(100.0, testCheckingAccount1.checkBalance(), 0.001, "Balance is 100.0");
        testCheckingAccount1.withdraw(25.0);
        assertEquals(75.0, testCheckingAccount1.checkBalance(), 0.001, "Balance is 75.0");
        
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(100.0), "Insufficient funds!");

        // test invalid withdrawal amounts
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(-50.0), "Withdrawal amount must be greater than 0!");
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(0.0), "Withdrawal amount must be greater than 0!");


        Checking testCheckingAccount2 = new Checking(189432, 100.0);
        testCheckingAccount2.deposit(300.0);
        assertEquals(400.0, testCheckingAccount2.checkBalance(), 0.001, "Balance is 400.0");

        //test floating point precision
        testCheckingAccount2.deposit(14.02);
        assertEquals(414.02, testCheckingAccount2.checkBalance(), 0.001, "Balance is 414.02");
        testCheckingAccount2.withdraw(50.78);
        assertEquals(363.24, testCheckingAccount2.checkBalance(), 0.001, "Balance is 363.24");
        testCheckingAccount2.withdraw(0.24);
        assertEquals(363.0, testCheckingAccount2.checkBalance(), 0.001, "Balance is 363.0");



    }

    public void testTransfer(){
        Checking testsCheckingAccount1 = new Checking(189432, 190.0);
        Checking testsCheckingAccount2 = new Checking(19837, 100.0);

        testsCheckingAccount1.transfer(testsCheckingAccount2, 50.0);
        assertEquals(140.0, testsCheckingAccount1.checkBalance(), 0.001, "Balance is 140.0");
        assertEquals(150.0, testsCheckingAccount2.checkBalance(), 0.001, "Balance is 150.0");

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

    public void testGetSuspiciousActivity(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);

        // trigger suspicious activity for deposit
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.deposit(6000.0), "Deposit amount exceeds the limit of $5000.0!");
        List<Transaction> suspiciousActivity1 = testsCheckingAccount1.getSuspiciousActivity();
        assertEquals(1, suspiciousActivity1.size(), "There should be 1 suspicious activity recorded");
        assertEquals("Deposit", suspiciousActivity1.get(0).type, "The suspicious activity should be a deposit");

        // trigger suspicious activity for withdrawal
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.withdraw(6000.0), "Withdrawal amount exceeds the limit of $5000.0!");
        List<Transaction> suspiciousActivity2 = testsCheckingAccount1.getSuspiciousActivity();
        assertEquals(2, suspiciousActivity2.size(), "There should be 2 suspicious activities recorded");
        assertEquals("Withdrawal", suspiciousActivity2.get(1).type, "The second suspicious activity should be a withdrawal");

        // trigger suspicious activity for transfer
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, 6000.0), "Transfer amount exceeds the limit of $5000.0!");
        List<Transaction> suspiciousActivity3 = testsCheckingAccount1.getSuspiciousActivity();
        assertEquals(3, suspiciousActivity3.size(), "There should be 3 suspicious activities recorded");
        assertEquals("Transfer", suspiciousActivity3.get(2).type, "The third suspicious activity should be a transfer");

        //test that failed transactions are also recorded in transaction history
        List<Transaction> transactionHistory = testsCheckingAccount1.getTransactionHistory();
        assertEquals(3, transactionHistory.size(), "There should be 3 transactions recorded");
        assertEquals("Deposit", transactionHistory.get(0).type, "The first transaction should be a deposit");
        assertEquals("Withdrawal", transactionHistory.get(1).type, "The second transaction should be a withdrawal");
        assertEquals("Transfer", transactionHistory.get(2).type, "The third transaction should be a transfer"); 

    }

    public void testGetTransactionHistory(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);

        testsCheckingAccount1.deposit(50.0);
        testsCheckingAccount1.withdraw(20.0);
        testsCheckingAccount1.transfer(testsCheckingAccount2, 30.0);

        List<Transaction> transactionHistory = testsCheckingAccount1.getTransactionHistory();
        assertEquals(3, transactionHistory.size(), "There should be 3 transactions recorded");
        assertEquals("Deposit", transactionHistory.get(0).type, "The first transaction should be a deposit");
        assertEquals("Withdrawal", transactionHistory.get(1).type, "The second transaction should be a withdrawal");
        assertEquals("Transfer", transactionHistory.get(2).type, "The third transaction should be a transfer");

    }   


    public void testIsFrozen(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        assertFalse(testsCheckingAccount1.isFrozen(), "The account should not be frozen");

        //new account should not be frozen
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);
        assertFalse(testsCheckingAccount2.isFrozen(), "The account should not be frozen");

    }   









    
    




    
}
