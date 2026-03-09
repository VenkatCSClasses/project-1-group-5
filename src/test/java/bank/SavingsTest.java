package bank;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class SavingsTest {

    private Bank bank;
    private Savings savings;
    private Checking checking;
    private LocalDate day1;
    private LocalDate day2;

    @BeforeEach
    void setUp() {
        day1 = LocalDate.of(2026, 3, 3);
        day2 = day1.plusDays(1);

        bank = new Bank();

        Savings.setSavingsAnnualInterestRate(0.365);
        Savings.setSavingsDailyWithdrawalLimit(500.0);

        savings = new Savings(1, 1000.0, bank);
        checking = new Checking(2, 0.0, bank);
    }

    @Test
    void withdrawReducesBalancewhenAllowed() {
        savings.withdraw(100.0, day1);
        assertEquals(900.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void withdrawThrowsIllegalArgumentExceptionWhenAmountNonPositive() {
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(0.0, day1));
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(-5.0, day1));
        assertEquals(1000.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void withdrawThrowsIllegalArgumentExceptionWhenWouldOverdraw() {
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(1000.01, day1));
        assertEquals(1000.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void withdrawEnforcesDailyLimitsameDay() {
        savings.withdraw(300.0, day1);
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(250.0, day1));
        assertEquals(700.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void dailyLimitresetsOnNewDay() {
        savings.withdraw(500.0, day1);
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(1.0, day1));

        savings.withdraw(500.0, day2);
        assertEquals(0.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void transfer_movesMoney_whenAllowed() {
        savings.transfer(checking, 200.0, day1);
        assertEquals(800.0, savings.checkBalance(), 0.000001);
        assertEquals(200.0, checking.checkBalance(), 0.000001);
    }

    @Test
    void transfer_throwsIllegalArgumentException_whenTargetNull() {
        assertThrows(IllegalArgumentException.class, () -> savings.transfer(null, 10.0, day1));
        assertEquals(1000.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void transfer_throwsIllegalArgumentException_whenWouldOverdraw() {
        assertThrows(IllegalArgumentException.class, () -> savings.transfer(checking, 1000.01, day1));
        assertEquals(1000.0, savings.checkBalance(), 0.000001);
        assertEquals(0.0, checking.checkBalance(), 0.000001);
    }

    @Test
    void transferCountsTowardDailyLimitwithdrawThenTransferSameDay() {
        savings.withdraw(400.0, day1);

        assertThrows(IllegalArgumentException.class,
                () -> savings.transfer(checking, 200.0, day1));

        assertEquals(600.0, savings.checkBalance(), 0.000001);
        assertEquals(0.0, checking.checkBalance(), 0.000001);
    }

    @Test
    void transferCountsTowardDailyLimittransferThenWithdrawSameDay() {
        savings.transfer(checking, 400.0, day1);

        assertThrows(IllegalArgumentException.class,
                () -> savings.withdraw(200.0, day1));

        assertEquals(600.0, savings.checkBalance(), 0.000001);
        assertEquals(400.0, checking.checkBalance(), 0.000001);
    }

    @Test
    void compoundDailyInterest_usesBankRate_andAppliesOncePerDay() {
        savings.compoundDailyInterest(day1);
        assertEquals(1001.0, savings.checkBalance(), 0.000001);

        savings.compoundDailyInterest(day1);
        assertEquals(1001.0, savings.checkBalance(), 0.000001);

        savings.compoundDailyInterest(day2);
        assertEquals(1002.001, savings.checkBalance(), 0.000001);
    }

    @Test
    void compoundDailyInterest_reflectsRateChanges_nextDay() {
        savings.compoundDailyInterest(day1);
        assertEquals(1001.0, savings.checkBalance(), 0.000001);

        Savings.setSavingsAnnualInterestRate(0.730);

        savings.compoundDailyInterest(day2);
        assertEquals(1003.002, savings.checkBalance(), 0.000001);
    }
}