package bank;

import org.junit.jupiter.api.Test;
import java.lang.IllegalArgumentException;
import java.util.List;

public class BankAdminTest {
    private BankTeller bankTeller;
    private Bank bank;    

    @Test
    void createAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        assert bank.getAll(1).getCustomerNumber() == 1;
        assert bank.getAccount(1).getBalance() == 1000.0;
        assert bank.getAccount(1).getAccountType() == 1;
        assert !bank.getAccount(1).isFrozen();
        assert IllegalArgumentException.class.isInstance(bankTeller.createAccount(0, 1000.0, 1));
        assert IllegalArgumentException.class.isInstance(bankTeller.createAccount(1, -1000.0, 1));
        assert IllegalArgumentException.class.isInstance(bankTeller.createAccount(2, 1000.0, 99));
    }

    @Test
    void closeAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        bankTeller.closeAccount(1);
        assert bank.getAccount(1) == null;
        assert IllegalArgumentException.class.isInstance(bankTeller.closeAccount(99));
    }

    @Test
    void processTransactionTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        bankTeller.processTransaction(1, 500.0, 1); // deposit
        assert bank.getAccount(1).getBalance() == 1500.0;
        bankTeller.processTransaction(1, 200.0, 2); // withdrawal
        assert bank.getAccount(1).getBalance() == 1300.0;
        assert IllegalArgumentException.class.isInstance(bankTeller.processTransaction(99, 100.0, 1));
        assert IllegalArgumentException.class.isInstance(bankTeller.processTransaction(1, -100.0, 1));
        assert IllegalArgumentException.class.isInstance(bankTeller.processTransaction(1, 100.0, 99));
    }

    @Test
    void calculateTotalAssetsTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        bankTeller.createAccount(2, 2000.0, 1);
        assert bank.calculateTotalAssets() == 3000.0;
    }

    @Test
    void toggleFreezeAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        bankAdmin.toggleFreezeAccount(1);
        assert bank.getAccount(1).isFrozen();
        bankAdmin.toggleFreezeAccount(1);
        assert !bank.getAccount(1).isFrozen();
        assert IllegalArgumentException.class.isInstance(bankAdmin.toggleFreezeAccount(99));
    }

    @Test
    void getSuspiciousActivityReportTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        bankTeller.processTransaction(1, 5000.0, 1); // large deposit
        bankTeller.processTransaction(1, 5000.0, 2); // large withdrawal
        List<Transaction> report = bankAdmin.getSuspiciousActivityReport(1);
        assert report.size() == 2;
        assert report.get(0).getAmount() == 5000.0;
        assert report.get(1).getAmount() == 5000.0;
        assert IllegalArgumentException.class.isInstance(bankAdmin.getSuspiciousActivityReport(99));
    }
}
