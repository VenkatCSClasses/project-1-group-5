package bank;

public class Transaction {
    int transactionID;
    int timestamp;
    String type;
    double amount;
    boolean status; // true for successful, false for failed
    boolean suspiciousActivity;

    public Transaction(int transactionID, int timestamp, String type, double amount, boolean status, boolean suspiciousActivity){
        this.transactionID = transactionID;
        this.timestamp = timestamp;
        this.type = type;
        this.amount = amount;
        this.status = status;
        this.suspiciousActivity = suspiciousActivity;
    }

    public int getTransactionID(){
        return this.transactionID;
    }

    public boolean getTransactionSuspiciousActivity(){
        return this.suspiciousActivity ;
    }

    public int getTimestamp(){
        return this.timestamp;
    }

    public String getType(){
        return this.type;
    }

    public double getAmount(){
        return this.amount;
    }

    public boolean getStatus(){
        return this.status;
    }

    public String toString(){
        return "Transaction ID: " + this.transactionID + ", Timestamp: " + this.timestamp + ", Type: " + this.type + ", Amount: $" + String.format("%.2f", this.amount) + ", Status: " + this.status + ", Suspicious Activity: " + this.suspiciousActivity;
    }

    



    
}
