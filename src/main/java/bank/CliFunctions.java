package bank;

import java.util.Scanner;

public class CliFunctions {
    private static Scanner scanner = new Scanner(System.in);

    public int getCustomerID() {
        int UserId = readInt(scanner, "Enter Customer ID:");
        return UserId;
    
    }

    public int getCustomerPin() {
        int UserPin = readInt(scanner, "Enter Customer Pin:");
        return UserPin;
    }

    private static int readInt(Scanner scanner, String prompt) {
        System.out.println(prompt);
        while (true) {
            String s = scanner.nextLine();
            try {
                return Integer.parseInt(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter an integer.");
            }
        }
    }

    

    

}
