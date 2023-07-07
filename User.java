package com.Keval;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
    private String firstName;       //first name of user
    private String lastName;        //last name of user
    private String uuid;              //unique id of user
    private byte pinHash[];         //user's pin umber
    private ArrayList<Account> accounts;    //the list of accounts



    /*
    -> Creation of New User
    -> FirstName = user's firstname
    -> LastName = user's lastname
    -> pin = the user's account pin number.
    -> theBank = the bank object that the user is a customer of
     */
    //Constructor:
    public User(String firstName,String lastName,String pin,Bank theBank) throws NoSuchAlgorithmException {

        //set user's name
        this.firstName = firstName;
        this.lastName = lastName;

        //need to hash the pin
        //store the pin's MD5 hash,rather than the original value
        //for security reasons
        MessageDigest md = MessageDigest.getInstance("MD5");    //unhandle exception
        this.pinHash = md.digest(pin.getBytes());


        //get a new, unique universal ID for the user
        this.uuid = theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log message
        System.out.printf("New User %s, %s with ID %s created.\n" ,lastName, firstName, this.uuid);
    }


    //encapsulation
    //providing only specific work
    //like adding the account only not to make changes in it.
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }


    /**
     * Return the user's UUID
     * @return  the user uuid
     */
    public String getUUID(){
        return this.uuid;

    }


    /**
     * Check whether the pi is valid or not
     * @param aPin      the pin which is provided by user
     * @return          true if matches, false if not
     * @throws NoSuchAlgorithmException
     */
    public boolean validatePin(String aPin) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        return MessageDigest.isEqual(md.digest(aPin.getBytes()),this.pinHash);

    }

    /**
     * Return's user firstname
     * @return  the first name
     */
    public String getFirstName(){
        return this.firstName;
    }



    public void printAccountsSummary(){
        System.out.printf("\n\n%s Accounts Summary\n",this.firstName);
        for (int i = 0; i < this.accounts.size(); i++) {
            System.out.printf("     %d. %s \n",i+1,this.accounts.get(i).getSummaryLine());
        }
        System.out.println();
    }


    /**
     * Get the number of accounts of the user
     * @return the number of accounts
     */
    public int numAccount(){
        return this.accounts.size();
    }

    /**
     * prints the trasaction history of particular account
     * @param accId the index of the account to use
     */
    public void printAccountTransactionHistory(int accId){
        this.accounts.get(accId).printTransHistory();
    }


    /**
     * Get the particular balance of an account
     * @param accID     account id
     * @return
     */
    public double getAcctBalance(int accID){
        return this.accounts.get(accID).getBalance();
    }


    /**
     * Get the UUID of a particular account
     * @param accID     the index of the account to use
     * @return          the UUID of the account
     */
    public String getAcctUUID(int accID){
        return this.accounts.get(accID).getUUID();
    }


    public void addAcctTransaction(int accID,double amnt,String memo){
        this.accounts.get(accID).addTransaction(amnt,memo);
    }










}
