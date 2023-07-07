package com.Keval;

import java.util.ArrayList;

public class Account {

    private String name;                                //account name = saving,current etc
 //   private double balance;                             //current balance of account
    private String uuid;                                //the account id
    private User holder;                                //the user who owns the account
    private ArrayList<Transaction> transactions;        //the list of transactions




    /*
        Creation of new account
        -> name = the name of account
        -> holder = the user object who holds the account ownership
        -> theBank = the bank form where the account belongs
     */
    //Constructor
    public Account(String name,User holder,Bank theBank){
        //set the account name and holder
        this.name = name;
        this.holder = holder;

        //get the new account uuid
        this.uuid = theBank.getNewAccountUUID();

        //initialize the transactions
        this.transactions = new ArrayList<Transaction>();

    }

    /**
     * Get the account UUID
     * @return  returns the UUID
     */
    public String getUUID(){
        return this.uuid;
    }

    /**
     * Get the summary of the account
     * @return the string summary
     */
    public String getSummaryLine(){
        //get the account's balance
        double balance = this.getBalance();

        //format the summary line,depends on the whether the balance -ve
        if(balance >= 0){
            return String.format("%s : $%.02f : %s",this.uuid,balance,this.name);
        }else {
            return String.format("%s : $(%.02f) : %s",this.uuid,balance,this.name);
        }
    }


    /**
     * Returns the balance of account holder
     * @return      return tha balance
     */
    public double getBalance(){

        double bal = 0;
        for(Transaction t:this.transactions){
            bal += t.getAmount();
        }

        return bal;
    }


    /**
     * print the transaction history for the account
     */
    public void printTransHistory(){
        System.out.printf("\nTransaction History for account %s",this.uuid);
        System.out.println();
        for (int i = this.transactions.size()-1;i>=0;i--){
            System.out.println(this.transactions.get(i).getSummaryLine());
        }
        System.out.println();
    }


    public void addTransaction(double amnt,String memo) {
        Transaction newTrans = new Transaction(amnt,memo,this);
        this.transactions.add(newTrans);


    }


}
