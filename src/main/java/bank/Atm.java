package bank;

public class Atm {
    
    private String location;
    private int atmID;

    public Atm(String location, int atmID){
        this.location = location;
        this.atmID = atmID;
    }

    //log in for users, specifically customers but tellers and admins can use this as well
    public boolean validateCredentials(int accountNum, int pin){
        return false;
    }

    //does the deposit, withdraw, transfer
    public void processTransaction(){

    }
}
