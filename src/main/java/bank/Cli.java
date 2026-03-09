package bank;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

//Use picocli to create a CLI for the banking application.


import java.util.Scanner;

public class Cli{

    //The Cli takes in a Bank object as a parameter

    public static void main(String[] args) {

        Customer testCustomer1 = new Customer("John Doe, 0,")





        BankAccount testAccount1 = new Checking(12345, 1000.0);
        BankAccount testAccount2 = new Savings(54321, 500.0, new Bank());
        BankAccount testAccount3 = new Checking(67890, 200.0);
        BankAccount testAccount4 = new Savings(98765, 1000.0, new Bank());

        Bank testBank = new Bank();
        testBank.addAccount(testAccount1);
        testBank.addAccount(testAccount2);
        testBank.addAccount(testAccount3);
        testBank.addAccount(testAccount4);

        Atm tesAtm = new Atm("Main Street", 50921);


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Bank CLI!");

        while (true){
            System.out.println("Where do you want to log in?. Select a number:");
            System.out.println("1.Customer ATM Login");
            System.out.println("2.Employee Login");
            System.out.println("3.Exit");
            String choice = scanner.nextLine();

            while(choice!="1" && choice!="2" && choice!="3"){
                System.out.println("Invalid choice. Please try again.");
                System.out.println("Where do you want to log in?. Select a number:");
                System.out.println("1.Customer ATM Login");
                System.out.println("2.Employee Login");
                 System.out.println("3.Exit");
                choice = scanner.nextLine();      
            }

            if(choice.equals("1")){
                // Log in 
                System.out.println("Enter Customer ID:");
                String uid = scanner.nextLine();
                int UserId = Integer.parseInt(uid);
            
               
                System.out.println("Enter PIN:");
                String pin = scanner.nextLine();
                int UserPin = Integer.parseInt(pin);

                //validate the customer's credentials
                while (!testBank.validateCustomerCredentials(UserId, UserPin)) {
                    System.out.println("Invalid credentials. Please try again.");
                    System.out.println("Enter Customer ID:");
                    uid = scanner.nextLine();
                    UserId = Integer.parseInt(uid);
                    System.out.println("Enter PIN:");
                    pin = scanner.nextLine();
                    UserPin = Integer.parseInt(pin);
                }

                // If credentials are valid, log in the customer and display their account information

                System.out.println("Login successful! Welcome, Customer " + UserId + "!");

                //Customer menu
                while (true) {
                    System.out.println("What would you like to do? Select a number:");
                    System.out.println("1. Check Account Balance");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. Transfer");








                


                








                // Implement customer login logic here
            } else if (choice.equals("2")){
                System.out.println("Employee Login selected.");
                // Implement employee login logic here
            } else if (choice.equals("3")){
                System.out.println("Exiting the CLI. Goodbye!");
                break;
            }




        





        }








    }


















}
