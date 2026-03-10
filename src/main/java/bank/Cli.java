package bank;

import java.util.List;
import java.util.Scanner;


public class Cli{


    public static void main(String[] args) {
        
        
       // Create some test data
       //Bank
        Bank testBank = new Bank();


        //Test Customers
        Customer testCustomer1 = new Customer("Luke Skywalker",78903);
        int testCustomer1ID = testCustomer1.getUserID();


        Customer testCustomer2 = new Customer("Chandeler", 12345);
        int testCustomer2ID = testCustomer2.getUserID();


        Customer testCustomer3 = new Customer("Rob Johnson", 54321);
        int testCustomer3ID = testCustomer3.getUserID();


        Customer testCustomer4 = new Customer("Emily Davis", 67890);
        int testCustomer4ID = testCustomer4.getUserID();



        Customer testCustomer5 = new Customer("Sophia Lee", 98765);
        int testCustomer5ID = testCustomer5.getUserID();

        //Test Tellers
        BankTeller testTeller1 = new BankTeller("Teller1", 11901);
        int testTeller1ID = testTeller1.getUserID();


        BankTeller testTeller2 = new BankTeller("Teller2",89201);
        int testTeller2ID = testTeller2.getUserID(); 


        BankTeller testTeller3 = new BankTeller("Teller3", 89213);   
        int testTeller3ID = testTeller3.getUserID(); 
        
        //Test Admins
        BankAdmin testAdmin1 = new BankAdmin("Admin1", 64923);
        int testAdmin1ID = testAdmin1.getUserID();

        BankAdmin testAdmin2 = new BankAdmin("Admin2", 46234);
        int testAdmin2ID = testAdmin2.getUserID();

        BankAdmin testAdmin3 = new BankAdmin("Admin3", 49245); 
        int testAdmin3ID = testAdmin3.getUserID();     

        //Test Atm
        Atm testAtm1 = new Atm("Main Street", 19301, testBank);
        Atm testAtm2 = new Atm("Second Street", 76923, testBank);
        Atm testAtm3 = new Atm("Third Street", 23624, testBank);


        //Add customers, tellers, and admin objects  to the bank
        testBank.addCustomer(testCustomer1);
        testBank.addCustomer(testCustomer2);
        testBank.addCustomer(testCustomer3);        
        testBank.addCustomer(testCustomer4);
        testBank.addCustomer(testCustomer5);    
        testBank.addCustomer(testTeller1);
        testBank.addCustomer(testTeller2);
        testBank.addCustomer(testTeller3);
        testBank.addCustomer(testAdmin1);
        testBank.addCustomer(testAdmin2);
        testBank.addCustomer(testAdmin3);   

       
        // Account Objects for customers
        BankAccount customer1Checking = new Checking(testCustomer1ID, 1000.00);
        int customer1CheckingNum = customer1Checking.getAccountNumber();

        BankAccount customer1Savings = new Savings(testCustomer1ID, 5000.00);
        int customer1SavingsNum = customer1Savings.getAccountNumber();

        BankAccount customer2Checking = new Checking(testCustomer2ID, 2000.00);
        int customer2CheckingNum = customer2Checking.getAccountNumber();

        BankAccount customer3Savings = new Savings(testCustomer3ID, 3000.00);
        int customer3SavingsNum = customer3Savings.getAccountNumber();

        BankAccount customer4Checking = new Checking(testCustomer4ID, 4000.00);
        int customer4CheckingNum = customer4Checking.getAccountNumber();

        BankAccount customer5Savings = new Savings(testCustomer5ID, 6000.00);
        int customer5SavingsNum = customer5Savings.getAccountNumber();

        // Add accounts to customers    
        testCustomer1.addAccount(customer1Checking);
        testCustomer1.addAccount(customer1Savings);     
        testCustomer2.addAccount(customer2Checking);
        testCustomer3.addAccount(customer3Savings);
        testCustomer4.addAccount(customer4Checking);
        testCustomer5.addAccount(customer5Savings); 

        // Add accounts to bank
        testBank.addAccount(customer1Checking);
        testBank.addAccount(customer1Savings);
        testBank.addAccount(customer2Checking);
        testBank.addAccount(customer3Savings);
        testBank.addAccount(customer4Checking);
        testBank.addAccount(customer5Savings);


        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Bank CLI!");
       
        // Main loop for the CLI
        while (true){
        
            //Start by validating credentials for either a customer, teller, or admin
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

                 
            System.out.println("Enter PIN:");
            String pin = scanner.nextLine();

            while (true){ //while loop to validate that the PIN is an integer
                try{
                    Integer.parseInt(pin);
                    break; //break out of the loop if parsing is successful
                }catch(NumberFormatException e){
                    System.out.println("Invalid PIN. Must be an integer.");
                    System.out.println("Enter PIN:");
                    pin = scanner.nextLine();
                }
            
            }

            //validate the customer's credentials

            int userID = Integer.parseInt(uid);
            int userPin = Integer.parseInt(pin);

            while (!testBank.validateCredentials(userID, userPin)) {
                System.out.println("Invalid credentials. Please try again.");
                // Ask for credentials again
                System.out.println("Enter Customer ID:");
                uid = scanner.nextLine();           
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

                System.out.println("Enter PIN:");
                pin = scanner.nextLine();
                while (true){ //while loop to validate that the PIN is an integer
                    try{
                        Integer.parseInt(pin);
                        break; //break out of the loop if parsing is successful
                    }catch(NumberFormatException e){
                        System.out.println("Invalid PIN. Must be an integer.");
                        System.out.println("Enter PIN:");
                        pin = scanner.nextLine();
                    }
                
                }

                userID = Integer.parseInt(uid);
                userPin = Integer.parseInt(pin);
              
            }

            // If credentials are valid, log in the customer and display their account information

            System.out.println("Login successful! Welcome, Customer " + userID + "!");
            Customer customerObject = testBank.getCustomer(userID);

             // Login as either a customer, teller, or admin
            System.out.println("Where do you want to log in?. Select a number:");
            System.out.println("1.Customer ATM Login");
            System.out.println("2.Bank Admin Login");
            System.out.println("3.Bank Teller Login");
            System.out.println("4.Exit");
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

                Customer adminObject = customerObject;

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
                    System.out.println("6. Get Suspicious Activity Report");
                    System.out.println("7. Log out");
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

                    //create account 
                    adminObject.createAccount(customerIdInt, initialDepositDouble, accountTypeInt);

                }else if (employeeChoiceInt == 2){
                    System.out.println("Close account");
                    //get Account number

                    System.out.println("Enter account number of the account to close:");
                    String accountNumCloseString = scanner.nextLine();

                    try{
                        Integer.parseInt(accountNumCloseString);
                    }catch(NumberFormatException e){
                        System.out.println("Invalid account number. Must be an integer.");
                        System.out.println("Enter account number of the account to close:");
                        accountNumCloseString = scanner.nextLine();
                    }
                    int accountNumCloseInt = Integer.parseInt(accountNumCloseString);

                    //close account
                    try{
                        adminObject.closeAccount(accountNumCloseInt);
                    }catch(NumberFormatException e){  
                        System.out.println("Account does not exist.");
                    }

                }else if (employeeChoiceInt == 3){
                    System.out.println("Process Transaction selected.");
                    // Process transaction logic here
                    System.out.println("Enter an account number to access:");
                    String accountNum = scanner.nextLine();

                    try{
                        int accountNumInt = Integer.parseInt(accountNum);
                    }catch(NumberFormatException e){
                        System.out.println("Invalid account number. Must be an integer.");
                        System.out.println("Enter an account number to access:");
                        accountNum = scanner.nextLine();        
                    }
                    
                    //get amount
                    System.out.println("Enter amount:");
                    String amount = scanner.nextLine();
                    try{
                        double amountDouble = Double.parseDouble(amount);
                    }catch(NumberFormatException e){    
                        System.out.println("Invalid amount. Must be a number.");
                        System.out.println("Enter amount:");
                        amount = scanner.nextLine();        
                    }

                    //get transaction type
                    System.out.println("Enter transaction type (1 for Deposit, 2 for Withdraw):");
                    String transactionType = scanner.nextLine();    
                    try{
                        int transactionTypeInt = Integer.parseInt(transactionType);
                    }catch(NumberFormatException e){
                        System.out.println("Invalid transaction type. Must be 1 for Deposit or 2 for Withdraw.");
                        System.out.println("Enter transaction type (1 for Deposit, 2 for Withdraw):");
                        transactionType = scanner.nextLine();       
                    }

                    //process transaction
                    try{
                        adminObject.processTransaction(accountNumInt, amountDouble, transactionTypeInt);
                        System.out.println("Transaction successful!");
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }catch(NumberFormatException e){
                        System.out.println("Invalid input. Please try again."); 
                    }

                else if(employeeChoiceInt == 4){
                    System.out.println("Calculate Total Assets selected.");
                    double totalAssets = adminObject.calculateTotalAssets();
                    System.out.println("Total assets in the bank: $" + String.format("%.2f", totalAssets)); 
                }else if(employeeChoiceInt == 5){
                    System.out.println("Toggle Freeze Account selected.");
                    System.out.println("Enter account number to toggle freeze:");
                    String accountNumFreezeString = scanner.nextLine();

                    try{
                        int accountNumFreezeInt = Integer.parseInt(accountNumFreezeString);
                        adminObject.toggleFreezeAccount(accountNumFreezeInt);
                        System.out.println("Account freeze status toggled successfully!");
                    }catch(NumberFormatException e){
                        System.out.println("Invalid account number. Must be an integer.");
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage()); 
                        System.out.println("Enter account number to toggle freeze:");
                        accountNumFreezeString = scanner.nextLine();
                    }

                }else if(employeeChoiceInt == 6){
                    System.out.println("Get Suspicious Activity Report selected.");
                    System.out.println("Enter account number to view report:");
                    String accountNumReportString = scanner.nextLine();
                    try{
                        int accountNumReportInt = Integer.parseInt(accountNumReportString);
                        List<Transaction> suspiciousActivity = adminObject.getSuspiciousActivityReport(accountNumReportInt);
                        if(suspiciousActivity.size() == 0){
                            System.out.println("No suspicious activity found for this account.");
                        }else{
                            System.out.println("Suspicious activity for account " + accountNumReportInt + ":");
                            for (Transaction transaction : suspiciousActivity) {
                                System.out.println(transaction);
                            }
                        }
                    }catch(NumberFormatException e){
                        System.out.println("Invalid account number. Must be an integer.");
                    }catch(IllegalArgumentException e){
                        System.out.println(e.getMessage());     
                    
                    }
                }else if(employeeChoiceInt == 7){
                    System.out.println("Logging out...");
                    break;        
                }
            }

              
            }else if (choiceInt == 3){
                System.out.println("Teller Login selected.");
                // Teller menu

                System.out.println("What would you like to do? Select a number:");
                System.out.println("1. Create Account");
                System.out.println("2. Close Account");
                System.out.println("3. Process Transaction");       
                System.out.println("4. Log out");
                String tellerChoice = scanner.nextLine();
    

                while(tellerChoice != "1" && tellerChoice != "2" && tellerChoice != "3" && tellerChoice != "4"){
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("What would you like to do? Select a number:");
                    System.out.println("1. Create Account");
                    System.out.println("2. Close Account");
                    System.out.println("3. Process Transaction");       
                    System.out.println("4. Log out");
                    tellerChoice = scanner.nextLine();

                }
                int tellerChoiceInt = Integer.parseInt(tellerChoice);

                if(tellerChoiceInt == 1){
                    System.out.println("Create Account selected.");
                    // Create account logic here

                    System.out.println("Enter customer ID for the new account:");
                    String customerId = scanner.nextLine();

                    while(true){ //while loop to validate that the customer ID is an integer
                        try{
                            int tellerCustomerId = Integer.parseInt(customerId);
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

                    BankTeller.createAccount(tellerCustomerId, initialDepositDouble, accountTypeInt);
                }else if(tellerChoiceInt == 2){
                    System.out.println("Close Account selected.");
                    // Create account logic here

                    System.out.println("Enter account number to close:");
                    String accountNumber = scanner.nextLine();

                    while(true){ //while loop to validate that the account number is an integer
                        try{
                            int tellerAccountNumber = Integer.parseInt(accountNumber);
                            break; //break out of the loop if parsing is successful
                        }catch(NumberFormatException e){
                            System.out.println("Invalid account number. Must be an integer.");
                            System.out.println("Enter account number to close:");
                            accountNumber = scanner.nextLine();
                        }
                    }

                    BankTeller.closeAccount(tellerAccountNumber);
                }else if(tellerChoiceInt == 3){
                    System.out.println("Process Transaction selected.");
                    // Process transaction logic here
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

                    }  
                  
                }else{
                    System.out.println("Logging out...");
                    break;
                }
            }
         
    }


}

