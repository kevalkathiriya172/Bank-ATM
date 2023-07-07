package com.Keval;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;                    //Bank name
    private ArrayList<User> users;          //user list  for bank
    private ArrayList<Account> accounts;    //account list for bank

    /**
     * Create a new bank object with empty lists users and accounts
     * @param name      name of the bank
     */
    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();

    }

    /**
     * Generate a new universal unique ID for an User
     * @return uuid
     */
    public String getNewUserUUID(){
        //Initialization
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique = false;


        //continue the generation until we get the unique id
        do {
            //generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();

            }

            //check to make sure it is unique
            nonUnique = false;
            for(User u : this.users){
                if (uuid.compareTo(u.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }

    /**
     * Generate a new universal unique ID for an account
     * @return the uuid
     */
    public String getNewAccountUUID(){
        //Initialization
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique = false;


        //continue the generation until we get the unique id
        do {
            //generate the number
            uuid = "";
            for (int i = 0; i < len; i++) {
                uuid += ((Integer)rng.nextInt(10)).toString();

            }

            //check to make sure it is unique
            nonUnique = false;
            for(Account a : this.accounts){
                if (uuid.compareTo(a.getUUID()) == 0){
                    nonUnique = true;
                    break;
                }
            }
        } while (nonUnique);
        return uuid;
    }


    /*
        Add an account for the user
        anAcct = the account to add
     */
    public void addAccount(Account anAcct){
        this.accounts.add(anAcct);
    }


    /**
     * Creation on a new user of the bank
     * @param firstName     firstname of the user
     * @param lastName      lastname of the user
     * @param pin           user's pin
     * @return              the new user onject
     */
    public User addUser(String firstName,String lastName,String pin) throws NoSuchAlgorithmException {
        //create a new user object and add it to our list
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);

        //create a savings an account for the user
        //and add it to user and bank account lists
        Account newAccount = new Account("Savings",newUser,this);
        //to connect the holder and bank lists
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }

    /**
     * Get the connect with user if that user id and pin is valid which is provided by yser
     * @param userID       the UUID of th euser by which he/she can login
     * @param pin           the pin of the user
     * @return              the user object if the login is successful, or null if not
     */
    public User userLogin(String userID,String pin) throws NoSuchAlgorithmException {
        //search through list of users

        for (User u:this.users ){
            //check the user ID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;
            }
        }

        //if we wouldn't find the user
        return null;

    }


    public String getName(){
        return this.name;
    }

}
