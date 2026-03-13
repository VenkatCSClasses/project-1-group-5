package bank;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class SavingsSystemTest {

    @Test
    void fullSavingsSystemTest() {
        Bank bank = new Bank();
        bank.setSavingsAnnualInterestRate(0.365);
        bank.setSavingsDailyWithdrawalLimit(500.0);

        Savings savings1 = new Savings(1, 1000.0);
        Savings savings2 = new Savings(2, 200.0);

        bank.addAccount(savings1);
        bank.addAccount(savings2);

        LocalDate day1 = LocalDate.of(2026, 3, 3);
        LocalDate day2 = day1.plusDays(1);

        savings1.withdraw(100.0, day1);
        savings1.transfer(savings2, 200.0, day1);

        savings1.compoundDailyInterest(day2);
        savings2.compoundDailyInterest(day2);

        assertEquals(700.7, savings1.checkBalance(), 0.000001);
        assertEquals(400.4, savings2.checkBalance(), 0.000001);
    }
}