package bank;

interface UserInterface {
    //implemented by Customer, BankTeller, and BankAdmin
    //I'm not sure if this is needed, because the attributes will be public, static, and final

    String username = "";
    int userID = 0;
    int accountNum = 0;
    int pin = 0;

    public String getUsername();
    public int getUserID();
    public int getAccountNum();
    public int getPin();

 
}
