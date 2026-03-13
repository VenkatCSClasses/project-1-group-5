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
        System.out.println("Test Customer 1 ID: " + testCustomer1ID);


        Customer testCustomer2 = new Customer("Chandeler", 2345);
        int testCustomer2ID = testCustomer2.getUserID();
        System.out.println("Test Customer 2 ID: " + testCustomer2ID);


        Customer testCustomer3 = new Customer("Rob Johnson", 4321);
        int testCustomer3ID = testCustomer3.getUserID();
        System.out.println("Test Customer 3 ID: " + testCustomer3ID);


        Customer testCustomer4 = new Customer("Emily Davis", 7890);
        int testCustomer4ID = testCustomer4.getUserID();
        System.out.println("Test Customer 4 ID: " + testCustomer4ID);



        Customer testCustomer5 = new Customer("Sophia Lee", 8765);
        int testCustomer5ID = testCustomer5.getUserID();
        System.out.println("Test Customer 5 ID: " + testCustomer5ID);

        //Test Tellers
        BankTeller testTeller1 = new BankTeller("Teller1", 1901);
        int testTeller1ID = testTeller1.getUserID();
        System.out.println("Test Teller 1 ID: " + testTeller1ID);


        BankTeller testTeller2 = new BankTeller("Teller2",9201);
        int testTeller2ID = testTeller2.getUserID(); 
        System.out.println("Test Teller 2 ID: " + testTeller2ID);


        BankTeller testTeller3 = new BankTeller("Teller3", 9213);   
        int testTeller3ID = testTeller3.getUserID(); 
        System.out.println("Test Teller 3 ID: " + testTeller3ID);
        
        //Test Admins
        BankAdmin testAdmin1 = new BankAdmin("Admin1", 4923);
        int testAdmin1ID = testAdmin1.getUserID();
        System.out.println("Test Admin 1 ID: " + testAdmin1ID);

        BankAdmin testAdmin2 = new BankAdmin("Admin2", 6234);
        int testAdmin2ID = testAdmin2.getUserID();
        System.out.println("Test Admin 2 ID: " + testAdmin2ID);

        BankAdmin testAdmin3 = new BankAdmin("Admin3", 9245); 
        int testAdmin3ID = testAdmin3.getUserID();     
        System.out.println("Test Admin 3 ID: " + testAdmin3ID); 

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
        System.out.println("Customer 1 Checking Account Number: " + customer1CheckingNum);

        BankAccount customer1Savings = new Savings(testCustomer1ID, 5000.00);
        int customer1SavingsNum = customer1Savings.getAccountNumber();
        System.out.println("Customer 1 Savings Account Number: " + customer1SavingsNum);

        BankAccount customer2Checking = new Checking(testCustomer2ID, 2000.00);
        int customer2CheckingNum = customer2Checking.getAccountNumber();
        System.out.println("Customer 2 Checking Account Number: " + customer2CheckingNum);

        BankAccount customer3Savings = new Savings(testCustomer3ID, 3000.00);
        int customer3SavingsNum = customer3Savings.getAccountNumber();
        System.out.println("Customer 3 Savings Account Number: " + customer3SavingsNum);

        BankAccount customer4Checking = new Checking(testCustomer4ID, 4000.00);
        int customer4CheckingNum = customer4Checking.getAccountNumber();
        System.out.println("Customer 4 Checking Account Number: " + customer4CheckingNum);

        BankAccount customer5Savings = new Savings(testCustomer5ID, 6000.00);
        int customer5SavingsNum = customer5Savings.getAccountNumber();
        System.out.println("Customer 5 Savings Account Number: " + customer5SavingsNum);

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

            System.out.println("Login successful! Welcome, Customer " + userID + "!");
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
                if (choiceInt == 1){
                    System.out.println("Customer ATM Login selected.");
                    Atm atmObject = testAtm1;

                    //Customer menu
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
                                    System.out.println(e.getMessage());
                                    continue; // skip the rest of the loop and start over
                                }

                            } else if (customerChoiceInt == 2) {
                                double amountDouble = readDouble(scanner, "Enter amount to withdraw:");
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
                                Atm.processTransaction(selectedAccount, amountDouble, 1);
                                System.out.println("Deposit successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                            } else if (customerChoiceInt == 2) {
                                double amountDouble = readDouble(scanner, "Enter amount to withdraw:");
                                Atm.processTransaction(selectedAccount, amountDouble, 0);
                                System.out.println("Withdrawal successful! New balance: $" + String.format("%.2f", selectedAccount.checkBalance()));
                            } else if (customerChoiceInt == 3) {
                                System.out.println("Logging out...");
                                break;
                            }

                        }   
                }  
        
                }else if (choiceInt == 2){
                    System.out.println("Admin Login selected.");
                    // Admin menu
                    //get admin object using the userID
                    BankAdmin adminObject = (BankAdmin) testBank.getCustomer(userID);

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
                            System.out.println(e.getMessage());
                        }
                    }else if (employeeChoiceInt == 2){
                        System.out.println("Close account");
                        int accountNumCloseInt = readInt(scanner, "Enter account number of the account to close:");
                        try{
                            adminObject.closeAccount(accountNumCloseInt);
                        }catch(IllegalArgumentException e){  
                            System.out.println(e.getMessage());
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
                            System.out.println(e.getMessage());
                        }
                    }


                    else if(employeeChoiceInt == 4){
                        System.out.println("Calculate Total Assets selected.");
                        double totalAssets = adminObject.calculateTotalAssets();
                        System.out.println("Total assets in the bank: $" + String.format("%.2f", totalAssets)); 
                    }else if(employeeChoiceInt == 5){
                        System.out.println("Toggle Freeze Account selected.");
                        int accountNumFreezeInt = readInt(scanner, "Enter account number to toggle freeze:");
                        try{
                            adminObject.toggleFreezeAccount(accountNumFreezeInt);
                            System.out.println("Account freeze toggled successfully!");
                        }catch(IllegalArgumentException e){   
                            System.out.println(e.getMessage());
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
                            System.out.println(e.getMessage());
                        }
                    }else if(employeeChoiceInt == 7){
                        System.out.println("Logging out...");
                        break;        
                    }
                
        
                }else if(choiceInt == 3){
                    System.out.println("Teller Login selected.");
                    // Teller menu

                    //get teller object using the userID
                    BankTeller tellerObject = (BankTeller) testBank.getCustomer(userID);

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
                    }else if(tellerChoiceInt == 2){
                        System.out.println("Close Account selected.");
                        int tellerAccountNumber = readInt(scanner, "Enter account number to close:");
                        tellerObject.closeAccount(tellerAccountNumber);
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
                                System.out.println(e.getMessage());
                            }

                        } else if (customerChoiceInt == 2) {
                            double amountDouble = readDouble(scanner, "Enter amount to withdraw:");
                            try{
                                tellerObject.processTransaction(accountNumInt, amountDouble, 2);
                                System.out.println("Withdrawal successful!");
                            }catch(IllegalArgumentException e){
                                System.out.println(e.getMessage());
                            }   
                        
                        } else if (customerChoiceInt == 3) {
                            System.out.println("Logging out...");
                        }
                    
                    }else{
                        System.out.println("Logging out...");
                        break;
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

}

