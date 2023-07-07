package com.Keval;


import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Interface for ATM
 */
public class ATM {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        Scanner input = new Scanner(System.in);

        //Initialize bank
        Bank theBank = new Bank("Bank of India");

        //add a user which also creates a savings account
        User aUser = theBank.addUser("Keval","Kathiriya","4017");

        //add a checking account for our user
        Account newAccount = new Account("Checking",aUser,theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User currUSer;
        while (true){
            //stay in login prompt until successful login
            currUSer = ATM.mainMenu(theBank,input);

            //stay in main menu until user quits
            ATM.printUserMenu(currUSer,input);

        }


    }

    public static User mainMenu(Bank theBank,Scanner input) throws NoSuchAlgorithmException {
        //Inits
        String userID;
        String pin;
        User authUser;

        //prompt the user till he/she entered the right one
        do {
            System.out.printf("\nWelcome to %s\n", theBank.getName());
            System.out.printf("Enter the USER ID: ");
            userID = input.nextLine();
            System.out.printf("Enter the PIN:");
            pin = input.nextLine();;

            //try to get the user object corresponding to the id and pin combo
            authUser = theBank.userLogin(userID,pin);{
                if (authUser == null){
                    System.out.println("Incorrect Details...\nPlease Retry...");
                }
            }
        }while (authUser == null);      //continue loopin until successful login
        return authUser;
    }


    public static void printUserMenu(User theUser,Scanner input){
        //print a summary of account summary
        theUser.printAccountsSummary();

        //init
        int choice;

        //user menu
        do{
            System.out.printf("Welcome %s,\nWhat would you like to do?\n",
                    theUser.getFirstName());
            System.out.println("    1.Show account transaction history ");
            System.out.println("    2.Withdrawal ");
            System.out.println("    3.Deposit ");
            System.out.println("    4.Transfer ");
            System.out.println("    5.Quit ");
            System.out.println();
            System.out.print("Enter the Choice:");
            choice = input.nextInt();

            if(choice < 1 || choice > 5){
                System.out.println("Oops Invalid choice.\nPlease Choose 1-5.");
            }

        }while (choice < 1 || choice > 5);


        //processing the choice
        switch (choice) {
            case 1:
                ATM.showTransactionHistory(theUser, input);
                break;
            case 2:
                ATM.withdrawMoney(theUser, input);
                break;
            case 3:
                ATM.depositMoney(theUser, input);
                break;
            case 4:
                ATM.transferMoney(theUser, input);
                break;
            case 5:
                //gobble up rest of previous input
                input.nextLine();
        }

        //redisplay the menu unless the user wants to quit
        if (choice!=5){
            ATM.printUserMenu(theUser,input);
        }


    }


    /**
     * Shows the transaction history for an account
     * @param theUser       the logged in user
     * @param input         the input used for user
     */
    public static void showTransactionHistory(User theUser,Scanner input){
        int theAccount;
        //get the account whose transaction history we want
        do {
            System.out.printf("Enter the number (1 -%d) of the account" +
                    "whose transactions who want to see: ",theUser.numAccount());
            theAccount = input.nextInt() - 1;
            if(theAccount < 0 || theAccount >= theUser.numAccount()){
                System.out.println("Sorry we don't have that account.");
            }
        } while (theAccount < 0 || theAccount >= theUser.numAccount());


        //print the transaction history
        theUser.printAccountTransactionHistory(theAccount);
    }


    /**
     * Get the money transaferred
     * @param theUser       account user
     * @param input         to take inputs from user
     */
    public static void transferMoney(User theUser,Scanner input){

        //inits
        int fromAct;
        int toAct;
        double Amnt;
        double acctBal;

        //get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ",theUser.numAccount());
            fromAct = input.nextInt() - 1;
            if(fromAct < 0 || fromAct >= theUser.numAccount()){
                System.out.println("Invalid Account.\nPlease try again.");
            }
        } while (fromAct < 0 || fromAct >= theUser.numAccount());
        acctBal = theUser.getAcctBalance(fromAct);

        //get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to transfer from: ",theUser.numAccount());
            toAct = input.nextInt() - 1;
            if(toAct < 0 || toAct >= theUser.numAccount()){
                System.out.println("Invalid Account.\nPlease try again.");
            }
        } while (toAct < 0 || toAct >= theUser.numAccount());
        acctBal = theUser.getAcctBalance(fromAct);

        //get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer(max $%.02f): $",
                    acctBal);
            Amnt = input.nextDouble();
            if(Amnt < 0){
                System.out.println("Amount must be greater than zero.");
            }else if(Amnt > acctBal){
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n",acctBal);
            }
        }   while (Amnt < 0 || Amnt > acctBal);


        //finally , do the transfer
        theUser.addAcctTransaction(fromAct,-1*Amnt,String.format(
                "Transfer to account %s",theUser.getAcctUUID(toAct)));
        theUser.addAcctTransaction(toAct,Amnt,String.format(
                "Transfer to account %s",theUser.getAcctUUID(fromAct)));
    }


    /**
     * Process for withdraw
     * @param theUser       account user
     * @param input         to take inputs from user
     */
    public static void withdrawMoney(User theUser,Scanner input){
        //initializations
        int fromAct;
        double Amnt;
        double acctBal;
        String memo;

        //get the account to withdraw from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to withdraw from: ",theUser.numAccount());
            fromAct = input.nextInt() - 1;
            if(fromAct < 0 || fromAct >= theUser.numAccount()){
                System.out.println("Invalid Account.\nPlease try again.");
            }
        } while (fromAct < 0 || fromAct >= theUser.numAccount());
        acctBal = theUser.getAcctBalance(fromAct);

        //to get the amount to transfer
        do {
            System.out.printf("Enter the amount to withdraw(max $%.02f): $",
                    acctBal);
            Amnt = input.nextDouble();
            if(Amnt < 0){
                System.out.println("Amount must be greater than zero.");
            }else if(Amnt > acctBal){
                System.out.printf("Amount must not be greater than\n" +
                        "balance of $%.02f.\n",acctBal);
            }
        }   while (Amnt < 0 || Amnt > acctBal);

        //gobble up rest of previous input
        input.nextLine();

        //get a memo
        System.out.print("Enter a memo: ");
        memo = input.nextLine();

        //to do withdraw
        theUser.addAcctTransaction(fromAct,-1*Amnt,memo);
    }

    /**
     * Process to get deposit in bank acount
     * @param theUser       account user
     * @param input         scanner object to take inputs
     */

    public static void depositMoney(User theUser,Scanner input){
        //initializations
        int toAct;
        double Amnt;
        double acctBal;
        String memo;

        //get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account\n" +
                    "to deposit in: ",theUser.numAccount());
            toAct = input.nextInt() - 1;
            if(toAct < 0 || toAct >= theUser.numAccount()){
                System.out.println("Invalid Account.\nPlease try again.");
            }
        } while (toAct < 0 || toAct >= theUser.numAccount());
        acctBal = theUser.getAcctBalance(toAct);

        //to get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f): $",
                    acctBal);
            Amnt = input.nextDouble();
            if(Amnt < 0){
                System.out.println("Amount must be greater than zero.");
            }
        }   while (Amnt < 0 );

        //gobble up rest of previous input
        input.nextLine();

        //get a memo
        System.out.print("Enter a memo: ");
        memo = input.nextLine();

        //to do withdraw
        theUser.addAcctTransaction(toAct,Amnt,memo);
    }
}
