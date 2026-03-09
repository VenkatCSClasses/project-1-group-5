package bank;

import java.util.List;

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
                Customer customerObject = testBank.getCustomerById(UserId);


                //Customer menu
                while (true) {

                    System.out.println("Where do you want to log in?. Select a number:");
                    System.out.println("1.Checkings Account");
                    System.out.println("2.Savings Account");
                    System.out.println("3.Log out");
                    String accountChoice = scanner.nextLine();
                    int accountChoiceInt = Integer.parseInt(accountChoice);

                    // Validate account choice
                    while(accountChoiceInt < 1 || accountChoiceInt > 3){
                        System.out.println("Invalid choice. Please try again.");
                        System.out.println("Where do you want to log in?. Select a number:");
                        System.out.println("1.Checkings Account");
                        System.out.println("2.Savings Account");
                        System.out.println("3.Log out");
                        accountChoice = scanner.nextLine();
                        accountChoiceInt = Integer.parseInt(accountChoice);
                    }

                    if(accountChoiceInt == 3){
                        System.out.println("Logging out...");
                        break;
                    }else if(accountChoiceInt == 1){ // Checkings account menu
                        List <BankAccount> customerAccounts = customerObject.getAccounts();
                        List <BankAccount> checkingAccounts = customerAccounts.stream().filter(account -> account instanceof Checking).toList();
                        if(checkingAccounts.size() == 0){
                            System.out.println("You have no checking accounts.");
                        }else{
                            System.out.println("Your checking accounts:");
                            for (BankAccount account : checkingAccounts) {
                                System.out.println(account);
                            }        
                        } 

                        //Access specific checkings account by account number
                        System.out.println("Enter an account number to access:");
                        String accountNum = scanner.nextLine();
                        int accountNumInt = Integer.parseInt(accountNum);

                        // Validate account number
                        //while the account number is not in the customer's accounts list, ask for a valid account number
                        while(!customerAccounts.contains(customerAccounts.stream().filter(a -> a.getAccountNumber() == accountNumInt).findFirst().orElse(null))){
                            System.out.println("Invalid account number. Please try again.");
                            System.out.println("Enter an account number to access:");
                            accountNum = scanner.nextLine();
                            accountNumInt = Integer.parseInt(accountNum);
                        }

                        //Selected Account
                        BankAccount selectedAccount = customerAccounts.stream().filter(a -> a.getAccountNumber() == accountNumInt).findFirst().orElse(null);
                        System.out.println("Account selected: " + selectedAccount);

                        //Once a valid account number is entered..
                        System.out.println("What would you like to do? Select a number:");
                        System.out.println("1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("4. Log out");
                        String customerChoice = scanner.nextLine();
                        int customerChoiceInt = Integer.parseInt(customerChoice);

                        // Validate customer choice
                        while(customerChoiceInt < 1 || customerChoiceInt > 3){
                            System.out.println("Invalid choice. Please try again.");
                            System.out.println("What would you like to do? Select a number:");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Log out");
                            customerChoice = scanner.nextLine();
                            customerChoiceInt = Integer.parseInt(customerChoice);
                        }

                        // Perform the selected action
                        //Check balance
                        if (customerChoiceInt == 1) {
                            System.out.println("Enter amount to deposit:");
                            String amount = scanner.nextLine();
                            double amountDouble = Double.parseDouble(amount);
                            Atm.processTransaction(selectedAccount, amountDouble, 1);
                            System.out.println("Deposit successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                        } else if (customerChoiceInt == 2) {
                            System.out.println("Enter amount to withdraw:");
                            String amount = scanner.nextLine();
                            double amountDouble = Double.parseDouble(amount);
                            Atm.processTransaction(selectedAccount, amountDouble, 0);
                            System.out.println("Withdrawal successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                        } else if (customerChoiceInt == 3) {
                            System.out.println("Logging out...");
                            break;
                        }

                    }else if(accountChoiceInt == 2){ // Savings account menu
                        List <BankAccount> customerAccounts = customerObject.getAccounts();
                        List <BankAccount> savingsAccounts = customerAccounts.stream().filter(account -> account instanceof Savings).toList();
                        if(savingsAccounts.size() == 0){
                            System.out.println("You have no savings accounts.");
                        }else{
                            System.out.println("Your savings accounts:");
                            for (BankAccount account : savingsAccounts) {
                                System.out.println(account);
                            }        
                        }
                        // Access specific savings account by account number
                        System.out.println("Enter an account number to access:");
                        String accountNum = scanner.nextLine();
                        int accountNumInt = Integer.parseInt(accountNum);       

                        // Validate account number
                        //while the account number is not in the customer's accounts list, ask for a valid account number
                        while(!customerAccounts.contains(customerAccounts.stream().filter(a -> a.getAccountNumber() == accountNumInt).findFirst().orElse(null))){
                            System.out.println("Invalid account number. Please try again.");
                            System.out.println("Enter an account number to access:");
                            accountNum = scanner.nextLine();
                            accountNumInt = Integer.parseInt(accountNum);
                        }

                        //Selected Account
                        BankAccount selectedAccount = customerAccounts.stream().filter(a -> a.getAccountNumber() == accountNumInt).findFirst().orElse(null);
                        System.out.println("Account selected: " + selectedAccount); 

                        //Once a valid account number is entered..
                        System.out.println("What would you like to do? Select a number:");
                        System.out.println("1. Deposit");
                        System.out.println("2. Withdraw");
                        System.out.println("3. Log out");
                        String customerChoice = scanner.nextLine();
                        int customerChoiceInt = Integer.parseInt(customerChoice);           

                        // Validate customer choice
                        while(customerChoiceInt < 1 || customerChoiceInt > 3){
                            System.out.println("Invalid choice. Please try again.");
                            System.out.println("What would you like to do? Select a number:");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Log out");
                            customerChoice = scanner.nextLine();
                            customerChoiceInt = Integer.parseInt(customerChoice);
                        }

                        // Perform the selected action
                        if (customerChoiceInt == 1) {
                            System.out.println("Enter amount to deposit:");
                            String amount = scanner.nextLine();
                            double amountDouble = Double.parseDouble(amount);
                            Atm.processTransaction(selectedAccount, amountDouble, 1);
                            System.out.println("Deposit successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                        } else if (customerChoiceInt == 2) {
                            System.out.println("Enter amount to withdraw:");
                            String amount = scanner.nextLine();
                            double amountDouble = Double.parseDouble(amount);           
                            Atm.processTransaction(selectedAccount, amountDouble, 0);
                            System.out.println("Withdrawal successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                        } else if (customerChoiceInt == 3) {
                            System.out.println("Logging out...");
                            break;
                        }

                        


                            


                    

                    











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
