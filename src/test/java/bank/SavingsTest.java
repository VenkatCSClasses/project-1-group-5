package bank;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {

    private Bank bank;
    private Savings savings;
    private Savings savings2;
    private LocalDate day1;
    private LocalDate day2;

    @BeforeEach
    void setUp() {
        day1 = LocalDate.of(2026, 3, 3);
        day2 = day1.plusDays(1);

        bank = new Bank();
        bank.setSavingsAnnualInterestRate(0.365);
        bank.setSavingsDailyWithdrawalLimit(500.0);

        savings = new Savings(1, 1000.0);
        savings2 = new Savings(2, 0.0);

        bank.addAccount(savings);
        bank.addAccount(savings2);
    }

    @Test
    void setupWorks() {
        assertEquals(1000.0, savings.checkBalance(), 0.000001);
        assertEquals(0.0, savings2.checkBalance(), 0.000001);
        assertEquals(1000.0, bank.getTotalCash(), 0.000001);
    }

    @Test
    void withdrawReducesBalanceWhenAllowed() {
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
    void withdrawEnforcesDailyLimitSameDay() {
        savings.withdraw(300.0, day1);
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(250.0, day1));
        assertEquals(700.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void dailyLimitResetsOnNewDay() {
        savings.withdraw(500.0, day1);
        assertThrows(IllegalArgumentException.class, () -> savings.withdraw(1.0, day1));

        savings.withdraw(500.0, day2);
        assertEquals(0.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void transferMovesMoneyWhenAllowed() {
        savings.transfer(savings2, 200.0, day1);
        assertEquals(800.0, savings.checkBalance(), 0.000001);
        assertEquals(200.0, savings2.checkBalance(), 0.000001);
    }

    @Test
    void transferThrowsIllegalArgumentExceptionWhenTargetNull() {
        assertThrows(IllegalArgumentException.class, () -> savings.transfer(null, 10.0, day1));
        assertEquals(1000.0, savings.checkBalance(), 0.000001);
    }

    @Test
    void transferThrowsIllegalArgumentExceptionWhenWouldOverdraw() {
        assertThrows(IllegalArgumentException.class, () -> savings.transfer(savings2, 1000.01, day1));
        assertEquals(1000.0, savings.checkBalance(), 0.000001);
        assertEquals(0.0, savings2.checkBalance(), 0.000001);
    }

    @Test
    void transferCountsTowardDailyLimitWithdrawThenTransferSameDay() {
        savings.withdraw(400.0, day1);

        assertThrows(IllegalArgumentException.class,
                () -> savings.transfer(savings2, 200.0, day1));

        assertEquals(600.0, savings.checkBalance(), 0.000001);
        assertEquals(0.0, savings2.checkBalance(), 0.000001);
    }

    @Test
    void transferCountsTowardDailyLimitTransferThenWithdrawSameDay() {
        savings.transfer(savings2, 400.0, day1);

        assertThrows(IllegalArgumentException.class,
                () -> savings.withdraw(200.0, day1));

        assertEquals(600.0, savings.checkBalance(), 0.000001);
        assertEquals(400.0, savings2.checkBalance(), 0.000001);
    }

    @Test
    void compoundDailyInterestUsesBankRateAndAppliesOncePerDay() {
        savings.compoundDailyInterest(day1);
        assertEquals(1001.0, savings.checkBalance(), 0.000001);

        savings.compoundDailyInterest(day1);
        assertEquals(1001.0, savings.checkBalance(), 0.000001);

        savings.compoundDailyInterest(day2);
        assertEquals(1002.001, savings.checkBalance(), 0.000001);
    }

    @Test
    void compoundDailyInterestReflectsRateChangesNextDay() {
        savings.compoundDailyInterest(day1);
        assertEquals(1001.0, savings.checkBalance(), 0.000001);

        bank.setSavingsAnnualInterestRate(0.730);

        savings.compoundDailyInterest(day2);
        assertEquals(1003.002, savings.checkBalance(), 0.000001);
    }
}