package bank;

import java.util.List;
import java.util.Scanner;


public class Cli{


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
       // Create some test data
       //Bank
        Bank testBank = new Bank();


        //Test Customers
        Customer testCustomer1 = new Customer("Luke Skywalker",8903);
        int testCustomer1ID = testCustomer1.getUserID();
        System.out.println("" + testCustomer1.getUsername() + " Customer ID: " + testCustomer1ID);


        Customer testCustomer2 = new Customer("Chandeler", 2345);
        int testCustomer2ID = testCustomer2.getUserID();
        System.out.println("" + testCustomer2.getUsername() + " Customer ID: " + testCustomer2ID);


        Customer testCustomer3 = new Customer("Rob Johnson", 4321);
        int testCustomer3ID = testCustomer3.getUserID();
        System.out.println("" + testCustomer3.getUsername() + " Customer ID: " + testCustomer3ID);


        Customer testCustomer4 = new Customer("Emily Davis", 7890);
        int testCustomer4ID = testCustomer4.getUserID();
        System.out.println("" + testCustomer4.getUsername() + " Customer ID: " + testCustomer4ID);



        Customer testCustomer5 = new Customer("Sophia Lee", 8765);
        int testCustomer5ID = testCustomer5.getUserID();
        System.out.println("" + testCustomer5.getUsername() + " Customer ID: " + testCustomer5ID);

        //Test Tellers
        BankTeller testTeller1 = new BankTeller("Teller1", 1901);
        int testTeller1ID = testTeller1.getUserID();
        System.out.println("" + testTeller1.getUsername() + " Teller ID: " + testTeller1ID);


        BankTeller testTeller2 = new BankTeller("Teller2",9201);
        int testTeller2ID = testTeller2.getUserID(); 
        System.out.println("" + testTeller2.getUsername() + " Teller ID: " + testTeller2ID);


        BankTeller testTeller3 = new BankTeller("Teller3", 9213);   
        int testTeller3ID = testTeller3.getUserID(); 
        System.out.println("" + testTeller3.getUsername() + " Teller ID: " + testTeller3ID);
        
        //Test Admins
        BankAdmin testAdmin1 = new BankAdmin("Admin1", 4923);
        int testAdmin1ID = testAdmin1.getUserID();
        System.out.println("" + testAdmin1.getUsername() + " Admin ID: " + testAdmin1ID);

        BankAdmin testAdmin2 = new BankAdmin("Admin2", 6234);
        int testAdmin2ID = testAdmin2.getUserID();
        System.out.println("" + testAdmin2.getUsername() + " Admin ID: " + testAdmin2ID);

        BankAdmin testAdmin3 = new BankAdmin("Admin3", 9245); 
        int testAdmin3ID = testAdmin3.getUserID();     
        System.out.println("" + testAdmin3.getUsername() + " Admin ID: " + testAdmin3ID); 

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
        System.out.println("" + testCustomer1.getUsername() + " Checking Account Number: " + customer1CheckingNum);

        BankAccount customer1Savings = new Savings(testCustomer1ID, 5000.00);
        int customer1SavingsNum = customer1Savings.getAccountNumber();
        System.out.println("" + testCustomer1.getUsername() + " Savings Account Number: " + customer1SavingsNum);

        BankAccount customer2Checking = new Checking(testCustomer2ID, 2000.00);
        int customer2CheckingNum = customer2Checking.getAccountNumber();
        System.out.println("" + testCustomer2.getUsername() + " Checking Account Number: " + customer2CheckingNum);

        BankAccount customer3Savings = new Savings(testCustomer3ID, 3000.00);
        int customer3SavingsNum = customer3Savings.getAccountNumber();
        System.out.println("" + testCustomer3.getUsername() + " Savings Account Number: " + customer3SavingsNum);

        BankAccount customer4Checking = new Checking(testCustomer4ID, 4000.00);
        int customer4CheckingNum = customer4Checking.getAccountNumber();
        System.out.println("" + testCustomer4.getUsername() + " Checking Account Number: " + customer4CheckingNum);

        BankAccount customer5Savings = new Savings(testCustomer5ID, 6000.00);
        int customer5SavingsNum = customer5Savings.getAccountNumber();
        System.out.println("" + testCustomer5.getUsername() + " Savings Account Number: " + customer5SavingsNum);

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

        System.out.println("Welcome to the Bank CLI!");
       
        // Main loop for the CLI
        while (true){
        
            // Start by validating credentials for either a customer, teller, or admin
            int userID = readInt(scanner, "Enter Customer ID:");
            int userPin = readInt(scanner, "Enter PIN:");

            while (!testBank.validateCredentials(userID, userPin)) {
                System.out.println("Invalid credentials. Please try again.");
                userID = readInt(scanner, "Enter Customer ID:");
                userPin = readInt(scanner, "Enter PIN:");
            }

            // If credentials are valid, log in the customer and display their account information

            System.out.println("Login successful! Welcome, " + testBank.getCustomer(userID).getUsername() + "!");
            Customer customerObject = testBank.getCustomer(userID);

            //Make this a loop that continues until the customer chooses to log out

            while (true) {

                int choiceInt;
                while (true) {
                    System.out.println("Where do you want to log in?. Select a number:");
                    System.out.println("1.Customer ATM Login");
                    System.out.println("2.Bank Admin Login");
                    System.out.println("3.Bank Teller Login");
                    System.out.println("4.Exit");
                    choiceInt = readInt(scanner, "Enter choice:");
                    if (choiceInt >= 1 && choiceInt <= 4) break;
                    System.out.println("Invalid choice. Please try again.");
                }

                // CUSTOMER LOGIN

                if (choiceInt == 4){
                    System.out.println("Exiting...");
                    System.out.println("Thank you for choosing us! Goodbye!");
                    System.exit(0);
                } else if (choiceInt == 1){
                    System.out.println("Customer ATM Login selected.");
                    Atm atmObject = testAtm1;
                    System.out.println("You are logged into the ATM at " + atmObject.getLocation() + ".");

                    //Customer menu
                    boolean leaveCustomerMenu = false;
                    while (true) {

                        int accountChoiceInt;
                        while (true) {
                            System.out.println("Where do you want to log in?. Select a number:");
                            System.out.println("1.Checkings Account");
                            System.out.println("2.Savings Account");
                            System.out.println("3.Log out");
                            accountChoiceInt = readInt(scanner, "Enter choice:");
                            if (accountChoiceInt >= 1 && accountChoiceInt <= 3) break;
                            System.out.println("Invalid choice. Please try again.");
                        }

                        if(accountChoiceInt == 3){
                            System.out.println("Logging out...");
                            System.out.println("Thank you for choosing us! Goodbye!");
                            System.exit(0);
                            
                        }else if(accountChoiceInt == 1){ // Checkings account menu
                            List <BankAccount> customerAccounts = customerObject.getAccounts();
                            List <BankAccount> checkingAccounts = customerAccounts.stream().filter(account -> account instanceof Checking).toList();
                            if(checkingAccounts.size() == 0){
                                System.out.println("You have no checking accounts.");
                            }else{
                                System.out.println("Your checking accounts:");
                                for (BankAccount account : checkingAccounts) {
                                    System.out.println("You have " + checkingAccounts.size() + " checking account(s).");
                                }        
                            } 

                            //Access specific checkings account by account number
                            int accountNumInt = readInt(scanner, "Enter an account number to access:");

                            //get account object using the account number
                            BankAccount selectedAccount = findAccountByNumber(customerAccounts, accountNumInt);
                            if (selectedAccount == null) {
                                System.out.println("No account found with that number. Returning to account menu.");
                                continue;
                            }

                            int customerChoiceInt;
                            while (true) {
                                System.out.println("What would you like to do? Select a number:");
                                System.out.println("1. Deposit");
                                System.out.println("2. Withdraw");
                                System.out.println("3. Log out");
                                customerChoiceInt = readInt(scanner, "Enter choice:");
                                if (customerChoiceInt >= 1 && customerChoiceInt <= 3) break;
                                System.out.println("Invalid choice. Please try again.");
                            }

                            // Perform the selected action
                            if (customerChoiceInt == 1) {
                                double amountDouble = readDouble(scanner, "Enter amount to deposit:");

                                try{
                                    Atm.processTransaction(selectedAccount, amountDouble, 1);
                                    System.out.println("Deposit successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                                }catch(IllegalArgumentException e){
                                    System.out.println("Transaction error:" + e.getMessage());
                                     // skip the rest of the loop and start over
                                }

                                int _cNext = postActionChoice(scanner);
                                if (_cNext == 1) {
                                    break; // return to account menu
                                } else if (_cNext == 2) {
                                    leaveCustomerMenu = true; // return to main menu
                                    break;
                                }

                            } else if (customerChoiceInt == 2) {
                                double amountDouble = readDouble(scanner, "Enter amount to withdraw:");
                                try{
                                    Atm.processTransaction(selectedAccount, amountDouble, 0);
                                    System.out.println("Withdrawal successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                                }catch(IllegalArgumentException e){
                                    System.out.println("Transaction error:" + e.getMessage());
                                }

                                int _cNext = postActionChoice(scanner);
                                if (_cNext == 1) {
                                    break; // return to account menu
                                } else if (_cNext == 2) {
                                    leaveCustomerMenu = true; // return to main menu
                                    break;
                                }
                            } else if (customerChoiceInt == 3) {
                                System.out.println("Logging out...");
                                System.out.println("Thank you for choosing us! Goodbye!");
                                System.exit(0);
                            }

                        }else if(accountChoiceInt == 2){ // Savings account menu
                            List <BankAccount> customerAccounts = customerObject.getAccounts();
                            List <BankAccount> savingsAccounts = customerAccounts.stream().filter(account -> account instanceof Savings).toList();
                            if(savingsAccounts.size() == 0){
                                System.out.println("You have no savings accounts.");
                            }else{
                                System.out.println("Your savings accounts:");
                                for (BankAccount account : savingsAccounts) {
                                    System.out.println("You have " + savingsAccounts.size() + " savings account(s).");
                                }        
                            }

                            int accountNumInt = readInt(scanner, "Enter an account number to access:");
                            BankAccount selectedAccount = findAccountByNumber(customerAccounts, accountNumInt);
                            if (selectedAccount == null) {
                                System.out.println("No account found with that number. Returning to account menu.");
                                continue;
                            }

                            System.out.println("Account selected: " + selectedAccount);

                            int customerChoiceInt;
                            while (true) {
                                System.out.println("What would you like to do? Select a number:");
                                System.out.println("1. Deposit");
                                System.out.println("2. Withdraw");
                                System.out.println("3. Log out");
                                customerChoiceInt = readInt(scanner, "Enter choice:");
                                if (customerChoiceInt >= 1 && customerChoiceInt <= 3) break;
                                System.out.println("Invalid choice. Please try again.");
                            }

                            if (customerChoiceInt == 1) {
                                double amountDouble = readDouble(scanner, "Enter amount to deposit:");
                                try{
                                    Atm.processTransaction(selectedAccount, amountDouble, 1);
                                    System.out.println("Deposit successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                                }catch(IllegalArgumentException e){
                                    System.out.println("Transaction error:" + e.getMessage());
                                }
                                

                                int _cNext = postActionChoice(scanner);
                                if (_cNext == 1) {
                                    break; // return to account menu
                                } else if (_cNext == 2) {
                                    leaveCustomerMenu = true; // return to main menu
                                    break;
                                }
                            } else if (customerChoiceInt == 2) {
                                double amountDouble = readDouble(scanner, "Enter amount to withdraw:");
                                try{
                                    Atm.processTransaction(selectedAccount, amountDouble, 0);
                                    System.out.println("Withdrawal successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                                }catch(IllegalArgumentException e){
                                    System.out.println("Transaction error:" + e.getMessage());
                                }

                                int _cNext = postActionChoice(scanner);
                                if (_cNext == 1) {
                                    break; // return to account menu
                                } else if (_cNext == 2) {
                                    leaveCustomerMenu = true; // return to main menu
                                    break;
                                }
                            } else if (customerChoiceInt == 3) {
                                System.out.println("Logging out...");
                                System.out.println("Thank you for choosing us! Goodbye!");
                                System.exit(0);
                            }

                        }
                        if (leaveCustomerMenu) {
                            break;
                        }
                    }

                    }else if (choiceInt == 2){
                    System.out.println("Admin Login selected.");
                    // Admin menu
                    // get customer object and verify it's an admin before casting
                    Customer possibleAdmin = testBank.getCustomer(userID);
                    if (!(possibleAdmin instanceof BankAdmin)) {
                        System.out.println("You are not an admin. Returning to role selection.");
                        continue;
                    }
                    BankAdmin adminObject = (BankAdmin) possibleAdmin;

                    int employeeChoiceInt;
                    while (true) {
                        System.out.println("What would you like to do? Select a number:");
                        System.out.println("1. Create Account");
                        System.out.println("2. Close Account");
                        System.out.println("3. Process Transaction");       
                        System.out.println("4. Calculate Total Assets");
                        System.out.println("5. Toggle Freeze Account");
                        System.out.println("6. Get Suspicious Activity Report");
                        System.out.println("7. Log out");
                        employeeChoiceInt = readInt(scanner, "Enter choice:");
                        if (employeeChoiceInt >= 1 && employeeChoiceInt <= 7) break;
                        System.out.println("Invalid choice. Please try again.");
                    }

                    if(employeeChoiceInt == 1){
                        System.out.println("Create Account selected.");

                        int customerIdInt = readInt(scanner, "Enter customer ID for the new account:");
                        double initialDepositDouble = readDouble(scanner, "Enter initial deposit amount:");

                        int accountTypeInt;
                        while (true) {
                            accountTypeInt = readInt(scanner, "Enter account type (1 for Checking, 2 for Savings):");
                            if (accountTypeInt == 1 || accountTypeInt == 2) break;
                            System.out.println("Invalid choice. Please try again.");
                        }

                        try{
                            adminObject.createAccount(customerIdInt, initialDepositDouble, accountTypeInt);
                            System.out.println("Account created successfully!");
                        }
                        catch(IllegalArgumentException e){
                            System.out.println("Error creating account: " + e.getMessage());
                        }

                        int _next = postActionChoice(scanner);
                        if (_next == 2) {
                            System.out.println("Returning to main menu...");
                            break;
                        }
                    }else if (employeeChoiceInt == 2){
                        System.out.println("Close account");
                        int accountNumCloseInt = readInt(scanner, "Enter account number of the account to close:");
                        try{
                            adminObject.closeAccount(accountNumCloseInt);
                        }catch(IllegalArgumentException e){  
                            System.out.println("Error closing account: " + e.getMessage());
                        }

                        int _nextClose = postActionChoice(scanner);
                        if (_nextClose == 2) {
                            System.out.println("Returning to main menu...");
                            break;
                        }

                    }else if (employeeChoiceInt == 3){
                        System.out.println("Process Transaction selected.");
                        int accountNumInt = readInt(scanner, "Enter an account number to access:");
                        double amountDouble = readDouble(scanner, "Enter amount:");

                        int transactionTypeInt;
                        while (true) {
                            transactionTypeInt = readInt(scanner, "Enter transaction type (1 for Deposit, 2 for Withdraw):");
                            if (transactionTypeInt == 1 || transactionTypeInt == 2) break;
                            System.out.println("Invalid transaction type. Must be 1 for Deposit or 2 for Withdraw.");
                        }

                        try{
                            adminObject.processTransaction(accountNumInt, amountDouble, transactionTypeInt);
                            System.out.println("Transaction successful!");
                        }catch(IllegalArgumentException e){
                            System.out.println("Error processing transaction: " + e.getMessage());
                        }

                        int _nextTxn = postActionChoice(scanner);
                        if (_nextTxn == 2) {
                            System.out.println("Returning to main menu...");
                            break;
                        }
                    }else if(employeeChoiceInt == 4){
                        System.out.println("Calculate Total Assets selected.");
                        double totalAssets = adminObject.calculateTotalAssets();
                        System.out.println("Total assets in the bank: $" + String.format("%.2f", totalAssets)); 

                        int _nextAssets = postActionChoice(scanner);
                        if (_nextAssets == 2) {
                            System.out.println("Returning to main menu...");
                            break;
                        }
                    }else if(employeeChoiceInt == 5){
                        System.out.println("Toggle Freeze Account selected.");
                        int accountNumFreezeInt = readInt(scanner, "Enter account number to toggle freeze:");
                        try{
                            adminObject.toggleFreezeAccount(accountNumFreezeInt);
                            System.out.println("Account freeze toggled successfully!");
                        }catch(IllegalArgumentException e){   
                            System.out.println("Error toggling account freeze: " + e.getMessage());
                        }

                        int _nextToggle = postActionChoice(scanner);
                        if (_nextToggle == 2) {
                            System.out.println("Returning to main menu...");
                            break;
                        }

                    }else if(employeeChoiceInt == 6){
                        System.out.println("Get Suspicious Activity Report selected.");
                        int accountNumReportInt = readInt(scanner, "Enter account number to view report:");
                        try{
                            List<Transaction> suspiciousActivity = adminObject.getSuspiciousActivityReport(accountNumReportInt);
                            if(suspiciousActivity.size() == 0){
                                System.out.println("No suspicious activity found for this account.");
                            }else{  
                                System.out.println("Suspicious activity for account " + accountNumReportInt + ":");
                                for (Transaction transaction : suspiciousActivity) {
                                    System.out.println(transaction);
                                }
                            }
                        }catch(IllegalArgumentException e){  
                            System.out.println("Error generating suspicious activity report: " + e.getMessage());
                        }

                        int _nextReport = postActionChoice(scanner);
                        if (_nextReport == 2) {
                            System.out.println("Returning to main menu...");
                            break;
                        }
                    }else if(employeeChoiceInt == 7){
                        System.out.println("Logging out...");
                        System.out.println("Thank you for choosing us! Goodbye!");
                        System.exit(0);    
                    }
                
        
                }else if(choiceInt == 3){
                    System.out.println("Teller Login selected.");
                    // Teller menu

                    // get customer object and verify it's a teller before casting
                    Customer possibleTeller = testBank.getCustomer(userID);
                    if (!(possibleTeller instanceof BankTeller)) {
                        System.out.println("You are not a teller. Returning to role selection.");
                        continue;
                    }
                    BankTeller tellerObject = (BankTeller) possibleTeller;

                    int tellerChoiceInt;
                    while (true) {
                        System.out.println("What would you like to do? Select a number:");
                        System.out.println("1. Create Account");
                        System.out.println("2. Close Account");
                        System.out.println("3. Process Transaction");       
                        System.out.println("4. Log out");
                        tellerChoiceInt = readInt(scanner, "Enter choice:");
                        if (tellerChoiceInt >= 1 && tellerChoiceInt <= 4) break;
                        System.out.println("Invalid choice. Please try again.");
                    }

                    if(tellerChoiceInt == 1){
                        System.out.println("Create Account selected.");

                        int tellerCustomerId = readInt(scanner, "Enter customer ID for the new account:");
                        double initialDepositDouble = readDouble(scanner, "Enter initial deposit amount:");

                        int accountTypeInt;
                        while (true) {
                            accountTypeInt = readInt(scanner, "Enter account type (1 for Checking, 2 for Savings):");
                            if (accountTypeInt == 1 || accountTypeInt == 2) break;
                            System.out.println("Invalid choice. Please try again.");
                        }

                        tellerObject.createAccount(tellerCustomerId, initialDepositDouble, accountTypeInt);

                        int _tNext = postActionChoice(scanner);
                        if (_tNext == 2) {
                            System.out.println("Returning to main menu...");
                            break;
                        }
                    }else if(tellerChoiceInt == 2){
                        System.out.println("Close Account selected.");
                        int tellerAccountNumber = readInt(scanner, "Enter account number to close:");
                        try{
                            tellerObject.closeAccount(tellerAccountNumber);
                        } catch (IllegalArgumentException e) {
                            System.out.println("Error closing account: " + e.getMessage());
                        }

                        int _tClose = postActionChoice(scanner);
                        if (_tClose == 2) {
                            System.out.println("Returning to main menu...");
                            break;
                        }
                    }else if(tellerChoiceInt == 3){
                        System.out.println("Process Transaction selected.");
                        int accountNumInt = readInt(scanner, "Enter an account number to access:");

                        int customerChoiceInt;
                        while (true) {
                            System.out.println("What would you like to do? Select a number:");
                            System.out.println("1. Deposit");
                            System.out.println("2. Withdraw");
                            System.out.println("3. Log out");
                            customerChoiceInt = readInt(scanner, "Enter choice:");
                            if (customerChoiceInt >= 1 && customerChoiceInt <= 3) break;
                            System.out.println("Invalid choice. Please try again.");
                        }

                        if (customerChoiceInt == 1) {
                            double amountDouble = readDouble(scanner, "Enter amount to deposit:");
                            try{
                                tellerObject.processTransaction(accountNumInt, amountDouble, 1);
                                System.out.println("Deposit successful!");
                            }catch(IllegalArgumentException e){
                                System.out.println("Error processing transaction: " + e.getMessage());
                            }

                            int _tNextTxn = postActionChoice(scanner);
                            if (_tNextTxn == 2) {
                                System.out.println("Returning to main menu...");
                                break;
                            }

                        } else if (customerChoiceInt == 2) {
                            double amountDouble = readDouble(scanner, "Enter amount to withdraw:");
                            try{
                                tellerObject.processTransaction(accountNumInt, amountDouble, 2);
                                System.out.println("Withdrawal successful!");
                            }catch(IllegalArgumentException e){
                                System.out.println("Error processing transaction: " + e.getMessage());
                            }   

                            int _tNextWithdraw = postActionChoice(scanner);
                            if (_tNextWithdraw == 2) {
                                System.out.println("Returning to main menu...");
                                break;
                            }
                        
                        } else if (customerChoiceInt == 3) {
                            System.out.println("Logging out...");
                            System.out.println("Thank you for choosing us! Goodbye!");
                            System.exit(0);
                        }
                    
                    }else{
                        System.out.println("Logging out...");
                        System.out.println("Thank you for choosing us! Goodbye!");
                        System.exit(0);
                    }
                
            }
        }
    }

    }
    private static boolean hasAccountNumber(List<BankAccount> accounts, int accountNum) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNum) {
                return true;
            }
        }
        return false;
    }

    private static BankAccount findAccountByNumber(List<BankAccount> accounts, int accountNum) {
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNum) {
                return account;
            }
        }
        return null;
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

    private static double readDouble(Scanner scanner, String prompt) {
        System.out.println(prompt);
        while (true) {
            String s = scanner.nextLine();
            try {
                return Double.parseDouble(s);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }
    
    private static int postActionChoice(Scanner scanner) {
        while (true) {
            System.out.println("Choose next action:");
            System.out.println("1. Return to previous menu");
            System.out.println("2. Return to main menu");
            int c = readInt(scanner, "Enter choice:");
            if (c == 1 || c == 2) return c;
            System.out.println("Invalid choice. Please try again.");
        }
    }



}

