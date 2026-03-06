package bank;
import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsTest {

    private Savings savings;
    private Checking checking;
    private LocalDate day1;
    private LocalDate day2;

    @BeforeEach
    void setUp() {
        day1 = LocalDate.of(2026, 3, 3);
        day2 = day1.plusDays(1);

        // configure parameters statically
        Savings.setSavingsAnnualInterestRate(0.365);   // daily = 0.001
        Savings.setSavingsDailyWithdrawalLimit(500.0);

        savings = new Savings(1000.0);
        checking = new Checking(0.0);
    }

    
    // Withdraw rules (counts toward daily limit!!!!)
    

    @Test
    void withdrawReducesBalancewhenAllowed() {
        savings.withdraw(100.0, day1);
        assertEquals(900.0, savings.getBalance(), 0.000001);
    }

    @Test
    void withdrawThrowsIllegalArgumentExceptionWhenAmountNonPositive() {
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(0.0, day1));
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(-5.0, day1));
        assertEquals(1000.0, savings.getBalance(), 0.000001);
    }

    @Test
    void withdrawThrowsIllegalStateExceptionWhenFrozen() {
        savings.freeze(); // or savings.setFrozen(true)
        assertThrows(IllegalStateException.class, () -> savings.withdraw(10.0, day1));
        assertEquals(1000.0, savings.getBalance(), 0.000001);
    }

    @Test
    void withdrawThrowsInsufficientFundsWhenWouldOverdraw() {
        assertThrows(InsufficientFundsException.class, () -> savings.withdraw(1000.01, day1));
        assertEquals(1000.0, savings.getBalance(), 0.000001);
    }

    @Test
    void withdrawEnforcesDailyLimitsameDay() {
        savings.withdraw(300.0, day1);
        assertThrows(DailyLimitExceededException.class, () -> savings.withdraw(250.0, day1)); // 300+250>500
        assertEquals(700.0, savings.getBalance(), 0.000001);
    }

    @Test
    void dailyLimitresetsOnNewDay() {
        savings.withdraw(500.0, day1);
        assertThrows(DailyLimitExceededException.class, () -> savings.withdraw(1.0, day1));

        savings.withdraw(500.0, day2); // new day => reset
        assertEquals(0.0, savings.getBalance(), 0.000001);
    }

    
    // Transfer rules (counts toward daily limit!!!!)
    

    @Test
    void transfer_movesMoney_whenAllowed() {
        savings.transfer(checking, 200.0, day1);
        assertEquals(800.0, savings.getBalance(), 0.000001);
        assertEquals(200.0, checking.getBalance(), 0.000001);
    }

    @Test
    void transfer_throwsIllegalArgumentException_whenTargetNull() {
        assertThrows(IllegalArgumentException.class, () -> savings.transfer(null, 10.0, day1));
        assertEquals(1000.0, savings.getBalance(), 0.000001);
    }

    @Test
    void transfer_throwsInsufficientFunds_whenWouldOverdraw() {
        assertThrows(InsufficientFundsException.class, () -> savings.transfer(checking, 1000.01, day1));
        assertEquals(1000.0, savings.getBalance(), 0.000001);
        assertEquals(0.0, checking.getBalance(), 0.000001);
    }

    @Test
    void transferCountsTowardDailyLimitwithdrawThenTransferSameDay() {
        savings.withdraw(400.0, day1);

        assertThrows(DailyLimitExceededException.class,
                () -> savings.transfer(checking, 200.0, day1)); // 400+200>500

        assertEquals(600.0, savings.getBalance(), 0.000001);
        assertEquals(0.0, checking.getBalance(), 0.000001);
    }

    @Test
    void transferCountsTowardDailyLimittransferThenWithdrawSameDay() {
        savings.transfer(checking, 400.0, day1);

        assertThrows(DailyLimitExceededException.class,
                () -> savings.withdraw(200.0, day1)); // 400+200>500

        assertEquals(600.0, savings.getBalance(), 0.000001);
        assertEquals(400.0, checking.getBalance(), 0.000001);
    }

    
    // Interest compounding (uses Bank’s current rate that is applied once per day)
    

    @Test
    void compoundDailyInterest_usesBankRate_andAppliesOncePerDay() {
        // 0.365 APR => daily 0.001
        savings.compoundDailyInterest(day1);
        assertEquals(1001.0, savings.getBalance(), 0.000001);

        savings.compoundDailyInterest(day1); // same day: no double apply
        assertEquals(1001.0, savings.getBalance(), 0.000001);

        savings.compoundDailyInterest(day2); // next day: 1001 * 0.001 = 1.001
        assertEquals(1002.001, savings.getBalance(), 0.000001);
    }

    @Test
    void compoundDailyInterest_reflectsBankRateChanges_nextDay() {
        savings.compoundDailyInterest(day1);
        assertEquals(1001.0, savings.getBalance(), 0.000001);

        bank.setSavingsAnnualInterestRate(0.730); // daily 0.002

        savings.compoundDailyInterest(day2); // 1001 * 0.002 = 2.002
        assertEquals(1003.002, savings.getBalance(), 0.000001);
    }
}