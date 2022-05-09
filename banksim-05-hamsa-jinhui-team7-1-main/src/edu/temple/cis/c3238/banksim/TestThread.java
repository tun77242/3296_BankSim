package edu.temple.cis.c3238.banksim;

/**
 * @author JinHui Liu
 * @author Modified by Hamsa Shaik
 */

/**
 * This is a test class for the test function of the threads
 */
public class TestThread extends Thread{
    private final Account[] accounts;
    private final int initialBalance;
    private final int numAccounts;
    private final Bank bank;

    /**
     * This is a contructor method for the TestThread Class
     * @param bank
     * @param accounts
     * @param initialBalance
     * @param numAccounts
     */
    public TestThread(Bank bank, Account[] accounts, int initialBalance, int numAccounts){
        this.bank = bank;
        this.accounts = accounts;
        this.initialBalance = initialBalance;
        this.numAccounts = numAccounts;
    }

    /**
     * This method is see how the threads work and observe the transactions and balances of accounts
     * @exception InterruptedException
     */
    @Override
    public void run() {
        int totalBalance = 0;
        try {
            bank.semaphore.acquire(numAccounts);

            for (Account account : accounts) {
                System.out.printf("%s %s%n", Thread.currentThread().toString(), account.toString());
                totalBalance += account.getBalance();
            }
        }
        catch (InterruptedException e){
            System.out.println(e);
        }
        finally {
            bank.semaphore.release(numAccounts);
        }
        System.out.println(Thread.currentThread().toString() + " Total Balance: " + totalBalance);

        if (totalBalance != numAccounts * initialBalance) {
                System.out.printf("%-30s Total balance changed!\n", Thread.currentThread().toString());
                System.exit(1);
        }
        else
        {
            System.out.printf("%-30s Total balance unchanged.\n", Thread.currentThread().toString());
        }
    }
}
