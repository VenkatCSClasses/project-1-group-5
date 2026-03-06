package bank;

/**
 * Signals that a withdrawal or transfer would exceed the daily limit for a
 * savings account.  Unchecked to simplify usage in business logic and tests.
 */
public class DailyLimitExceededException extends RuntimeException {
    public DailyLimitExceededException() {}
    public DailyLimitExceededException(String message) { super(message); }
    public DailyLimitExceededException(String message, Throwable cause) { super(message, cause); }
}
