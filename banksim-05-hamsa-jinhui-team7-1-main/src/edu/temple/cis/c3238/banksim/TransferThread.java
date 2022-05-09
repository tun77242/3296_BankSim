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
 * This class extends Thread and contains methods to run the transfers using threads
 */
class TransferThread extends Thread {

    private final Bank bank;
    private final int fromAccount;
    private final int maxAmount;

    /**
     * This method is the constructor for the TransferThread class
     * @param b
     * @param from
     * @param max
     */
    public TransferThread(Bank b, int from, int max) {
        this.bank = b;
        fromAccount = from;
        maxAmount = max;
    }

    /**
     * This method checks if bank is open for every loop and makes transfers and close the bank at the end
     */
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            if(!bank.isOpen()){
                break;
            }
            int toAccount = (int) (bank.size() * Math.random());
            int amount = (int) (maxAmount * Math.random());
            bank.transfer(fromAccount, toAccount, amount);
        }
        bank.closeBank();

        System.out.printf("%-30s Account[%d] has finished with its transactions.\n", Thread.currentThread().toString(), fromAccount);
    }
}
