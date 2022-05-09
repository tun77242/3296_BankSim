package edu.temple.cis.c3238.banksim;

/**
 * @author Cay Horstmann
 * @author Modified by Paul Wolfgang
 * @author Modified by Charles Wang
 * @author Modified by Alexa Delacenserie
 * @author Modified by Tarek Elseify
 * @author Modified by Hamsa Shaik
 * @author Modified by JinHui Liu
 */

/**
 * This is the Account class that contains withdraw, deposit and other methods
 */
public class Account {

    private volatile int balance;
    private final int id;
    private Bank ourBank;

    /**
     * This the constructor for the Account class
     * @param ourBank
     * @param id
     * @param initialBalance
     * @return Nothing
     */
    public Account(Bank ourBank, int id, int initialBalance) {
        this.ourBank = ourBank;
        this.id = id;
        this.balance = initialBalance;
    }

    /**
     * This method gets the balance of the account
     * @return balance
     */
    public int getBalance() {
        return balance;
    }

    /**
     * This method is used to withdraw amount from a bank account
     * @param amount
     * @return true or false
     */
    public synchronized boolean withdraw(int amount) {
        if (amount <= balance) {
            int currentBalance = balance;
            Thread.yield(); // Try to force collision
            int newBalance = currentBalance - amount;
            balance = newBalance;
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method waits and checks if bank is opened and account has enough balance
     * @param amount
     * @return Nothing
     * @exception InterruptedException
     */
    public synchronized void waitForSufficientFunds(int amount){
        while(ourBank.isOpen() && amount >= balance){
            try{
                wait();
            }catch (InterruptedException e){
                System.out.println(e);
            }
        }
    }

    /**
     * This method is used to deposit amount into a bank account
     * @param amount
     * @return Nothing
     */
    public synchronized void deposit(int amount) {
        int currentBalance = balance;
        Thread.yield();   // Try to force collision
        int newBalance = currentBalance + amount;
        balance = newBalance;
        notifyAll();
    }

    /**
     * This method is used to get string representation of object
     * @return String.format("Account[%d] balance %d", id, balance)
     */
    @Override
    public String toString() {
        return String.format("Account[%d] balance %d", id, balance);
    }
}
