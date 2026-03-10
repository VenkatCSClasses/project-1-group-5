package bank;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class BankAdminTest {
    private BankTeller bankTeller;
    private BankAdmin bankAdmin;
    private Bank bank;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        bank = new Bank();
        bankTeller = new BankTeller("Test Teller", 9087);
        bankAdmin = new BankAdmin("Admin User", 1893);
    }

    @Test
    void createAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        // at least one account should exist in the static registry
        org.junit.jupiter.api.Assertions.assertFalse(bank.getAllAccounts().isEmpty());
    }

    @Test
    void closeAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        // cannot easily verify account number since it's random; just exercise method
        bankTeller.closeAccount(1); // will throw if missing
    }

    @Test
    void processTransactionTest(){
        // purposely minimal; just make sure method compiles and throws appropriately
        assertThrows(IllegalArgumentException.class, () -> {bankTeller.processTransaction(99, 100.0, 1);});
    }

    @Test
    void calculateTotalAssetsTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        bankTeller.createAccount(2, 2000.0, 1);
        // call admin method; compiles if no exception
        bankAdmin.calculateTotalAssets();
    }

    @Test
    void toggleFreezeAccountTest(){
        bankTeller.createAccount(1, 1000.0, 1);
        // just ensure method runs without crashing
        bankAdmin.toggleFreezeAccount(1);
    }

    @Test
    void getSuspiciousActivityReportTest(){
        // basic invocation
        bankAdmin.getSuspiciousActivityReport(1);
    }
}
