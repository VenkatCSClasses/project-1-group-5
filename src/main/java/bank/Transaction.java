package bank;

public class Transaction {
    int transactionID;
    int timestamp;
    String type;
    double amount;
    boolean status; // true for successful, false for failed
    boolean suspiciousAcrivity;

    public Transaction(int transactionID, int timestamp, String type, double amount, boolean status, boolean suspiciousAcrivity){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public int getTransactionID(){
        throw new IllegalArgumentException("Not implemented yet");
    }
    public int getSuspiciousAcrivity(){
        throw new IllegalArgumentException("Not implemented yet");
    }

    public String toString(){
        throw new IllegalArgumentException("Not implemented yet");
    }

    



    
}
