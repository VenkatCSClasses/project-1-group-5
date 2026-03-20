package bank;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
 




public class CheckingTest {
    @Test
    public void testCheckingConstructor(){
        Checking acct = new Checking(12345, 0.0);
        org.junit.jupiter.api.Assertions.assertNotNull(acct);
    }
    
    @Test
    public void testDepositWithdrawCheckBalance(){
        Checking testCheckingAccount1 = new Checking(189432, 0.0);
        testCheckingAccount1.deposit(100.0);
        assertEquals(100.0, testCheckingAccount1.checkBalance(), 0.001, "Balance is 100.0"); //equivalence class
        testCheckingAccount1.deposit(50.0);
        assertEquals(150.0, testCheckingAccount1.checkBalance(), 0.001, "Balance is 150.0"); //equivalence class

        // test invalid deposit amounts
        assertThrows(IllegalArgumentException.class, () -> testCheckingAccount1.deposit(-50.0), "Deposit amount must be greater than 0!");//equivalence class
        assertThrows(IllegalArgumentException.class, () -> testCheckingAccount1.deposit(0.0), "Deposit amount must be greater than 0!"); //boundary value

     

        testCheckingAccount1.withdraw(50.0);
        assertEquals(100.0, testCheckingAccount1.checkBalance(), 0.001, "Balance is 100.0");//equivalence class
        testCheckingAccount1.withdraw(25.0);
        assertEquals(75.0, testCheckingAccount1.checkBalance(), 0.001, "Balance is 75.0");//equivalence class
        
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(100.0), "Insufficient funds!"); //equivalence class

        // test invalid withdrawal amounts
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(-50.0), "Withdrawal amount must be greater than 0!");//equivalence class
        assertThrows( IllegalArgumentException.class, () -> testCheckingAccount1.withdraw(0.0), "Withdrawal amount must be greater than 0!"); //boundary value


        Checking testCheckingAccount2 = new Checking(189432, 100.0);
        testCheckingAccount2.deposit(300.0);
        assertEquals(400.0, testCheckingAccount2.checkBalance(), 0.001, "Balance is 400.0"); //equivalence class

        //test floating point precision
        testCheckingAccount2.deposit(14.02);
        assertEquals(414.02, testCheckingAccount2.checkBalance(), 0.001, "Balance is 414.02"); //equivalence class
        testCheckingAccount2.withdraw(50.78);
        assertEquals(363.24, testCheckingAccount2.checkBalance(), 0.001, "Balance is 363.24"); //equivalence class
        testCheckingAccount2.withdraw(0.24);
        assertEquals(363.0, testCheckingAccount2.checkBalance(), 0.001, "Balance is 363.0"); //equivalence class



    }
    @Test
    public void testTransfer(){
        //equivalence classes for valid transfer amounts and valid target accounts
        Checking testsCheckingAccount1 = new Checking(189432, 190.0);
        Checking testsCheckingAccount2 = new Checking(19837, 100.0);

        testsCheckingAccount1.transfer(testsCheckingAccount2, 50.0);
        assertEquals(140.0, testsCheckingAccount1.checkBalance(), 0.001, "Balance is 140.0");
        assertEquals(150.0, testsCheckingAccount2.checkBalance(), 0.001, "Balance is 150.0");

    }

    @Test
    public void testsTransferInvalidAmounts(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);

        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, -50.0), "Transfer amount must be greater than 0!"); //equivalence class
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, 0.0), "Transfer amount must be greater than 0!"); //boundary value
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, 500.0), "Insufficient funds!"); //equivalence class

    }
    
    @Test
    public void testTransferInvalidTargetAccount(){
        //equivalence class for invalid target accounts (null or same account)
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        BankAccount invalidTargetAccount = null;
        assertThrows(NullPointerException.class, () -> testsCheckingAccount1.transfer(invalidTargetAccount, 50.0), "Target account cannot be null!");

    }

    @Test

    public void testGetSuspiciousActivity(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);

        // equivalence class for triggering suspicious activity for deposit
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.deposit(6000.0), "Deposit amount exceeds the limit of $5000.0!");
        List<Transaction> suspiciousActivity1 = testsCheckingAccount1.getSuspiciousActivity();
        assertEquals(1, suspiciousActivity1.size(), "There should be 1 suspicious activity recorded");
        assertEquals("Deposit", suspiciousActivity1.get(0).type, "The suspicious activity should be a deposit");

        // equivalence class for triggering suspicious activity for withdrawal
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.withdraw(6000.0), "Withdrawal amount exceeds the limit of $5000.0!");
        List<Transaction> suspiciousActivity2 = testsCheckingAccount1.getSuspiciousActivity();
        assertEquals(2, suspiciousActivity2.size(), "There should be 2 suspicious activities recorded");
        assertEquals("Withdrawal", suspiciousActivity2.get(1).type, "The second suspicious activity should be a withdrawal");

        // equivalence class for triggering suspicious activity for transfer
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
    
    @Test
    public void testGetTransactionHistory(){
        //equivalence class for valid transactions being recorded in transaction history
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

    @Test
    public void testIsFrozen(){
        //equivalence class for new accounts not being frozen
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        assertFalse(testsCheckingAccount1.isFrozen(), "The account should not be frozen");

        //new account should not be frozen
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);
        assertFalse(testsCheckingAccount2.isFrozen(), "The account should not be frozen");

    }   

    @Test
    public void suspiciousActivityTrumpsInsufficientFunds(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);

        // equivalence class for triggering suspicious activity for transfer with insufficient funds
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, 6000.0), "Transfer amount exceeds the limit of $5000.0!");
        List<Transaction> suspiciousActivity = testsCheckingAccount1.getSuspiciousActivity();
        assertEquals(1, suspiciousActivity.size(), "There should be 1 suspicious activity recorded");
        assertEquals("Transfer", suspiciousActivity.get(0).type, "The suspicious activity should be a transfer");

    }

    @Test
    public void testFrozenAccount(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        testsCheckingAccount1.setFrozen(true);

        //equivalence class for performing transactions on a frozen account
        assertThrows(IllegalStateException.class, () -> testsCheckingAccount1.deposit(50.0), "Account is frozen!");
        assertThrows(IllegalStateException.class, () -> testsCheckingAccount1.withdraw(20.0), "Account is frozen!");
        assertThrows(IllegalStateException.class, () -> testsCheckingAccount1.transfer(new Checking(194303, 100.0), 30.0), "Account is frozen!");

    }
    
    @Test
    public void testUniqueTransactionIDs(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);

        testsCheckingAccount1.deposit(50.0);
        testsCheckingAccount1.withdraw(20.0);
        testsCheckingAccount1.transfer(testsCheckingAccount2, 30.0);

        List<Transaction> transactionHistory = testsCheckingAccount1.getTransactionHistory();
        //equivalence class for ensuring that each transaction has a unique ID
        assertEquals(3, transactionHistory.size(), "There should be 3 transactions recorded");
        assertFalse(transactionHistory.get(0).transactionID == transactionHistory.get(1).transactionID);
        assertFalse(transactionHistory.get(1).transactionID == transactionHistory.get(2).transactionID);
        assertFalse(transactionHistory.get(0).transactionID == transactionHistory.get(2).transactionID);
  
    }

    @Test
    public void testLimitboundaries(){
        Checking testsCheckingAccount1 = new Checking(148920, 190.0);
        Checking testsCheckingAccount2 = new Checking(194303, 100.0);

        //boundary values for transactions exactly at the limit of $5000.0 should trigger suspicious activity and throw an exception

        // test deposit at limit
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.deposit(5000.0), "Deposit amount exceeds the limit of $5000.0!");

        // test withdrawal at limit
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.withdraw(5000.0), "Withdrawal amount exceeds the limit of $5000.0!");

        // test transfer at limit
        assertThrows(IllegalArgumentException.class, () -> testsCheckingAccount1.transfer(testsCheckingAccount2, 5000.0), "Transfer amount exceeds the limit of $5000.0!");

        assertEquals(3, testsCheckingAccount1.getSuspiciousActivity().size(), "There should be 3 suspicious activity recorded");

    }

    //System test checking account creation by Bank Admin and teller
    @Test
    public void testAccountCreationAndFunctionality(){
        Bank bank = new Bank();
        BankAdmin admin = new BankAdmin("admin", 1234);
        BankTeller teller = new BankTeller("teller", 5678);

        //Create customer IDS
        Customer customer1 = new Customer("customer1", 1111);
        int customer1ID = customer1.getUserID();
        Customer customer2 = new Customer("customer2", 2222);
        int customer2ID = customer2.getUserID();

        //test account creation by admin
        admin.createAccount(customer1ID, 100.0, 1);
        assertEquals(1, Bank.allAccounts.size(), "There should be 1 account created");
        int accountNumber = Bank.allAccounts.entrySet().iterator().next().getKey();
        assertEquals(1, Bank.allAccounts.size(), "There should be 1 account in the bank");
      
       
        //test account creation by teller
        teller.createAccount(customer2ID, 200.0, 1);
        int aaccountNumber2 = Bank.allAccounts.entrySet().stream().filter(entry -> entry.getKey() != accountNumber).findFirst().get().getKey();
        assertEquals(2, Bank.allAccounts.size(), "There should be 2 accounts created");
        assertEquals(2, Bank.allAccounts.size(), "There should be 2 accounts in the bank");

        //test that the accounts are functional
        //deposit into account 1
        teller.processTransaction(accountNumber, 50.0, 1);
        assertEquals(150.0, Bank.allAccounts.get(accountNumber).checkBalance(), 0.001, "Balance should be 150.0");

        //withdraw from account 2
        teller.processTransaction(aaccountNumber2, 50.0, 2);
        assertEquals(150.0, Bank.allAccounts.get(aaccountNumber2).checkBalance(), 0.001, "Balance should be 150.0");


    }

    


}
   

      












    
    




    

