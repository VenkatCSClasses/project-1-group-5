package bank;

import java.util.Scanner;

public class ATMCli {
    private static Atm tesAtm = new Atm("Main Street", 12345);

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the ATM CLI!");

    
        while (true) {
            System.out.println("1. Log in");
            System.out.println("Enter User ID:");
            String userId = scanner.nextLine();
            int testUserId = Integer.parseInt(userId); // Replace with actual user ID for testing
            if (!userId.equals(String.valueOf(testUserId))) {
                System.out.println("User ID not found. Please try again.");
                continue;
            }

            System.out.println("Enter PIN:");
            String pin = scanner.nextLine();
            int testUserPin = Integer.parseInt(pin); // Replace with actual PIN for testing

            BankAccount testAccount = new Checking(testUserId,1000.0);


            try {
                int userIdInt = Integer.parseInt(userId);
                int pinInt = Integer.parseInt(pin);
                if (tesAtm.validateCredentials(userIdInt, pinInt)) {
                    System.out.println("Login successful!");
                    System.out.println("Welcome, Customer " + userIdInt + "!");
                    System.out.println("What would you like to do? Enter 1 or 2:");
                    System.out.println("1. Withdraw");
                    System.out.println("2. Deposit");
                    String choice = scanner.nextLine();
                    switch (choice) {
                        case "1":
                            System.out.println("How much would you like to withdraw?");
                            String amount = scanner.nextLine();
                            try {
                                double amountDouble = Double.parseDouble(amount);

                                if (amountDouble <= 0) {
                                    System.out.println("Amount must be greater than zero. Please try again.");
                                    break;
                                }
                                if(choice.equals("1") && amountDouble > testAccount.checkBalance()){
                                    System.out.println("Insufficient funds. Please try again.");
                                    break;
                                }

                                if(choice.equals("1")){
                                    Atm.processTransaction(testAccount, amountDouble,0);
                                    System.out.println("Withdrawal successful! Your new balance is: " + testAccount.checkBalance());
                                
                                
                                }
                                else if(choice.equals("2")){
                                    Atm.processTransaction(testAccount, amountDouble,1);
                                    System.out.println("Deposit successful! Your new balance is: " + testAccount.checkBalance());
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid amount. Please try again.");
                            }
                            break;
                        case "2":
                            System.out.println("How much would you like to deposit?");
                            break;
                        default:
                            System.out.println("Invalid choice. Please try again.");
                    }
                    break;
                } else {
                    System.out.println("Invalid credentials. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("User ID and PIN must be numeric. Please try again.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
            }

            // loop back to login prompt
        }

        scanner.close();
    }

}


