package edu.temple.cis.c3238.banksim;

import java.util.concurrent.Semaphore;

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
 * This class Bank is used for transfer management of amount between accounts
 */
public class Bank {

    public static final int NTEST = 10;
    private final Account[] accounts;
    private long numTransactions = 0;
    private final int initialBalance;
    private final int numAccounts;
    private boolean open;
    public final Semaphore semaphore;

    /**
     * This is the constructor for the Bank class
     * @param numAccounts
     * @param initialBalance
     */
    public Bank(int numAccounts, int initialBalance) {
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
        semaphore = new Semaphore(this.numAccounts);
        accounts = new Account[numAccounts];
        open = true;
        numTransactions = 0;
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = new Account(this, i, initialBalance);
        }
    }

    /**
     * This method transfers amount using withdraw and deposit in account class
     * @param from
     * @param to
     * @param amount
     * @return Nothing
     * @exception InterruptedException
     */
    public void transfer(int from, int to, int amount) {
        accounts[from].waitForSufficientFunds(amount);
        if (!open)
            return;
        try {
            semaphore.acquire();

            if (accounts[from].withdraw(amount)) {
                accounts[to].deposit(amount);
            }
        }
        catch (InterruptedException e){
            System.out.println(e);
        }
        finally {
            semaphore.release();
        }
            // Uncomment line when ready to start Task 3.
            if (shouldTest()) test();
    }

    /**
     * This boolean method is to know if the bank is opened or not
     * @return open
     */
    synchronized boolean isOpen(){
        return open;
    }

    /**
     * This method is to close the bank to avoid threads mess up
     */
    public void closeBank(){
        synchronized (this){
            open = false;
        }
        for(Account account : accounts){
            synchronized (account){
                account.notifyAll();
            }
        }
    }

    /**
     * This synchronized method is to test the threads functionality
     */
    public synchronized void test() {
        //task 4
        Thread testThread = new TestThread(this, accounts, initialBalance, numAccounts);
        testThread.start();
    }

    public int getNumAccounts() {
        return numAccounts;
    }

    /**
     * This boolean method is to test
     * @return ++numTransactions % NTEST == 0
     */
    public synchronized boolean shouldTest() {
        return ++numTransactions % NTEST == 0;
    }

    /**
     * This method gets the size of the accounts
     * @return accounts.length
     */
    public int size(){
        return accounts.length;
    }

}
