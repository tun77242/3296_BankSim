# BankSim by JinHui and Hamsa

**UML Class Diagram:**
This project has 5 classes, which is the main class is called BankSimMain, and Bank, Account, TestThread, and TransferThread. The relationship between Bank and Account should be aggregation and association because the bank owns these accounts and accounts associated with the bank. Two thread classes extend the Thread class. The relationship between Bank and TestThread is association because they call each other when need to run the thread. Also, the TransferThread associated with the bank, when the bank is starting transfer, and the TransferThread will call the bank.transfer method. Finally, the main will call the bank object to open the Bank and will call the TransferThread to start the program.
![image](https://user-images.githubusercontent.com/89947770/157093827-6d5fb7b6-bde0-4fe5-a9bc-0794c0e8ca7b.png)

**First UML sequence:**
The race condition in banksim may occur when two threads get to the point of working on same methods at the same time and without any protected ways, which is either in withdraw or deposit. The current balances of the account will be returned wrong after the deposits due to the threads overwriting each other and from there the whole calculations later on in the accounts will be disturbed.

Here is the initial UML sequence diagram for banksim where a race condition may occur.

![Initial UML](https://user-images.githubusercontent.com/78066498/154828588-8b9ec512-e24c-4dad-bbb5-e92209dcb24f.png)

To avoid this race condition we prefer using synchronization technique for the methods withdraw, deposit, and waitForSufficientFunds in the Accounts class.


**Second UML Sequence:**
There is another race condition arising when some of the transfer threads try to start using test() while another transfer thread is running the test() function. The test() function iterates over the array of accounts, fetching their balances and adding them up. But after it has fetched and summed the first several accounts' balances, one of the other threads might withdraw money from a later account, which is not yet accessed by the tester and deposit that money into one of the earlier accounts, whose current balance had previously been read by the tester. In the same way, another thread might withdraw money from an earlier account, whose current balance had previously been read by the test and then deposit that money in a later account, whose balance hadn't yet been read by the tester. Basically, because the test function only checks an account's balance once, it's blind to changes in the earlier accounts' balances after it had accessed them. As a result, it will sometimes incorrectly think that money has either spontaneously disappeared from or been added to the bank.

![image](https://user-images.githubusercontent.com/89947770/157093897-737bc3e8-0eb1-4a59-8fea-e2739fe5143d.png)
