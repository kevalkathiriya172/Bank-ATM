package com.Keval;

import java.util.ArrayList;
import java.util.Date;

public class Transaction {

    private double amount;                  //the amount of transaction
    private Date timestamp;                 //the time and date of trancsaction
    private String memo;                    //the receipt of transaction
    private Account inAccount;              //the account in which transaction occurs


    /**
     * Create a new transaction
     * @param amount        the amount transacted
     * @param inAccount     the account whose transaction it belongs
     */
    public Transaction(double amount,Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";

    }

    /**
     * Create a new transaction
     * @param amount        the amount transacted
     * @param inAccount     the account whose transaction it belongs
     * @param memo          the memo or receipt for the transaction
     */
    public Transaction(double amount,String memo,Account inAccount){
        //call the two-ara constructor
        this(amount,inAccount);

        //set the memo
        this.memo = memo;
    }


    /**
     * Return the current amounnt present in it
     * @return  return amount
     */
    public double getAmount(){
        return this.amount;
    }


    /**
     * get a string summarizing the transaction
     * @return  the summary string
     */

    public String getSummaryLine(){
        if(this.amount >= 0 ){
            return String.format("%s : $%.02f : %s",this.timestamp.toString(),
                    this.amount,this.memo);
        }else{
            return String.format("%s : $(%.02f) : %s",this.timestamp.toString(),
                    -this.amount,this.memo);
        }
    }








}
