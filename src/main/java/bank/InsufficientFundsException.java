package bank;

/**
 * Thrown when an account does not have enough balance to complete a
 * withdrawal or transfer.  This is intentionally an unchecked exception
 * to match the tests' expectations, but code may choose to catch it if
 * desired.
 */
public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException() {}
    public InsufficientFundsException(String message) { super(message); }
    public InsufficientFundsException(String message, Throwable cause) { super(message, cause); }
}
