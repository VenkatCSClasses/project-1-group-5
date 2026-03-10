package bank;

import java.util.List;
import java.util.Scanner;

public class Cli{


    public static void main(String[] args) {
        
        
       // Create some test data
        //Test Customers
        Customer testCustomer1 = new Customer("John Doe",);
   

        //Test Accounts
        BankAccount testAccount1 = new Checking(12345, 1000.0);
        BankAccount testAccount2 = new Savings(54321, 500.0);
        BankAccount testAccount3 = new Checking(67890, 200.0);
        BankAccount testAccount4 = new Savings(98765, 1000.0);
       
        // Create a test bank and add the test accounts to it
        Bank testBank = new Bank();
        testBank.addAccount(testAccount1);
        testBank.addAccount(testAccount2);
        testBank.addAccount(testAccount3);
        testBank.addAccount(testAccount4);
       
        // Create a test ATM
        Atm tesAtm = new Atm("Main Street", 50921);

        //Create a Bank Teller

        //Create a Bank Admin


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Bank CLI!");
       
        // Main loop for the CLI
        while (true){
        
            //Start by validating credentials for either a customer, teller, or admin
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
            Customer customerObject = testBank.getCustomer(UserId);

             // Login as either a customer, teller, or admin
            System.out.println("Where do you want to log in?. Select a number:");
            System.out.println("1.Customer ATM Login");
            System.out.println("2.Bank Admin Login");
            System.out.println("3.Bank Teller Login");
            System.out.println("3.Exit");
            String choice = scanner.nextLine();

            while(choice!="1" && choice!="2" && choice!="3" && choice!="4"){
                System.out.println("Invalid choice. Please try again.");
                System.out.println("Where do you want to log in?. Select a number:");
                System.out.println("1.Customer ATM Login");
                System.out.println("2.Bank Admin Login");
                System.out.println("3.Bank Teller Login");
                System.out.println("4.Exit");
                choice = scanner.nextLine();      
            }

            int choiceInt = Integer.parseInt(choice);

            // CUSTOMER LOGIN
            if (choiceInt == 1){
                System.out.println("Customer ATM Login selected.");
                //Customer menu
                while (true) {

                    System.out.println("Where do you want to log in?. Select a number:");
                    System.out.println("1.Checkings Account");
                    System.out.println("2.Savings Account");
                    System.out.println("3.Log out");
                    String accountChoice = scanner.nextLine();
            
                    // Validate account choice
                    while(accountChoice != "1" && accountChoice != "2" && accountChoice != "3"){
                        System.out.println("Invalid choice. Please try again.");
                        System.out.println("Where do you want to log in?. Select a number:");
                        System.out.println("1.Checkings Account");
                        System.out.println("2.Savings Account");
                        System.out.println("3.Log out");
                        accountChoice = scanner.nextLine();
                    }

                    int accountChoiceInt = Integer.parseInt(accountChoice);

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
    

                        // Validate customer choice
                        while(customerChoice != "1" && customerChoice != "2" && customerChoice != "3"){
                            System.out.println("Invalid choice. Please try again.");
                            System.out.println("What would you like to do? Select a number:");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Log out");
                            customerChoice = scanner.nextLine();
                        }

                        int customerChoiceInt = Integer.parseInt(customerChoice);

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
    
                        // Validate customer choice
                        while(customerChoice != "1" && customerChoice != "2" && customerChoice != "3"){
                            System.out.println("Invalid choice. Please try again.");
                            System.out.println("What would you like to do? Select a number:");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Log out");
                            customerChoice = scanner.nextLine();
                        }

                        int customerChoiceInt = Integer.parseInt(customerChoice);

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

                    }    }  
    
            }else if (choiceInt == 2){
                System.out.println("Admin Login selected.");
                // Admin menu

                System.out.println("What would you like to do? Select a number:");
                System.out.println("1. Create Account");
                System.out.println("2. Close Account");
                System.out.println("3. Process Transaction");       
                System.out.println("4. Calculate Total Assets");
                System.out.println("5. Toggle Freeze Account");
                System.out.println("6. Log out");
                String employeeChoice = scanner.nextLine();
    

                while(employeeChoice != "1" && employeeChoice != "2" && employeeChoice != "3" && employeeChoice != "4" && employeeChoice != "5" && employeeChoice != "6"    ){
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("What would you like to do? Select a number:");
                    System.out.println("1. Create Account");
                    System.out.println("2. Close Account");
                    System.out.println("3. Process Transaction");       
                    System.out.println("4. Calculate Total Assets");
                    System.out.println("5. Toggle Freeze Account");
                    System.out.println("6. Log out");
                    employeeChoice = scanner.nextLine();

                }
                int employeeChoiceInt = Integer.parseInt(employeeChoice);

                if(employeeChoiceInt == 1){
                    System.out.println("Create Account selected.");
                    // Create account logic here

                    System.out.println("Enter customer ID for the new account:");
                    String customerId = scanner.nextLine();

                    while(true){ //while loop to validate that the customer ID is an integer
                        try{
                            int adminCustomerId = Integer.parseInt(customerId);
                            break; //break out of the loop if parsing is successful
                        }catch(NumberFormatException e){
                            System.out.println("Invalid customer ID. Must be an integer.");
                            System.out.println("Enter customer ID for the new account:");
                            customerId = scanner.nextLine();
                        }
                    }

                    System.out.println("Enter initial deposit amount:");
                    String initialDeposit = scanner.nextLine();


                    while(true){ //while loop to validate that the initial deposit is a double
                        try{
                            Double amountDouble = Double.parseDouble(initialDeposit);
                            break; //break out of the loop if parsing is successful
                        }catch(NumberFormatException e){
                            System.out.println("Invalid amount. Must be a number.");
                            System.out.println("Enter initial deposit amount:");
                            initialDeposit = scanner.nextLine();
                        }
                    }
                    


                    System.out.println("Enter account type (1 for Checking, 2 for Savings):");
                    String accountType = scanner.nextLine();
                    int accountTypeInt = Integer.parseInt(accountType);

                    // Validate account type
                    while(accountTypeInt != 1 && accountTypeInt != 2){
                        System.out.println("Invalid choice. Please try again.");
                        System.out.println("Enter account type (1 for Checking, 2 for Savings):");
                        accountType = scanner.nextLine();
                        accountTypeInt = Integer.parseInt(accountType);
                    }

                    BankAdmin.createAccount(customerIdInt, initialDepositDouble, accountTypeInt);










            }

                

                
















                }else if(employeeChoiceInt == 2){
                    System.out.println("Admin Login selected.");
                    // Admin login and menu logic here





                }





             
            } else if (choice.equals("3")){
                System.out.println("Exiting the CLI. Goodbye!");
                break;
            }




        





        }








    }


















}
