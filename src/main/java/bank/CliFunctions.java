package bank;

import java.util.Scanner;

public class CliFunctions {
    private static Scanner scanner = new Scanner(System.in);

    public int getCustomerID() {

        System.out.println("Enter Customer ID:");
        String uid = scanner.nextLine();

        while(true){ //while loop to validate that the customer ID is an integer
            try{
                Integer.parseInt(uid);
                break; //break out of the loop if parsing is successful
            }catch(NumberFormatException e){
                System.out.println("Invalid customer ID. Must be an integer.");
                System.out.println("Enter Customer ID:");   
                uid = scanner.nextLine();
            }

        }
        int UserId = Integer.parseInt(uid);
        return UserId;
    
    }

    public int getCustomerPin() {
        System.out.println("Enter Customer Pin:");
        String pin = scanner.nextLine();

        while(true){ //while loop to validate that the pin is an integer
            try{
                Integer.parseInt(pin);
                break; //break out of the loop if parsing is successful
            }catch(NumberFormatException e){
                System.out.println("Invalid pin. Must be an integer.");
                System.out.println("Enter Customer Pin:");   
                pin = scanner.nextLine();
            }

        }
        int UserPin = Integer.parseInt(pin);
        return UserPin;
    }

    

    

}
