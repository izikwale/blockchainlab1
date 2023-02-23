package lab2;


import javax.naming.ldap.LdapContext;

/**
 *   Ledger defines for each user the balance at a given time
     in the ledger model of bitcoins
     and contains methods for checking and updating the ledger
     including processing a transaction
 */

public class Ledger extends UserAmount{


    /**
     *
     *  Task 4: Fill in the method checkTransactionValid
     *          You need to replace the dummy value true by the correct calculation
     *
     * Check a transaction is valid:
     *    the sum of outputs is less than or equal the sum of inputs
     *    and the inputs can be deducted from the ledger.
     *
     */

    public boolean checkTxValid(Transaction tx) {
        boolean txValid = false;
        if (tx.toInputs().checkDeductableFromLedger(this) && tx.checkTransactionAmountsValid()) {
            txValid = true;
            System.out.println("\t\t\tThis transaction is valid!");
        } else System.out.println("\t\t\tThis transaction is not valid!");
        return txValid;
    };

    /**
     *
     *  Task 5: Fill in the method processTransaction
     *
     * Process a transaction
     *    by first deducting all the inputs
     *    and then adding all the outputs.
     *
     */


    public void processTransaction(Transaction tx) {
        if (checkTxValid(tx)) {
            tx.toInputs().subtractFromLedger(this);
            tx.toOutputs().addToLedger(this);
        } else System.out.println("\t\t\tinvalid transaction");
    };
    
    
    


    /** 
     *  Task 6: Fill in the testcases as described in the labsheet
     *    
     * Testcase
     */
    
    public static void test() {
        Ledger l = new Ledger();
//    add new users
        l.addAccount("Alice", 0);
        l.addAccount("Bob", 0);
        l.addAccount("Carol", 0);
        l.addAccount("David", 0);
        l.print();
//    set user balances
        System.out.println("\t\t\t set user balances");
        l.setBalance("Alice", 10);
        l.setBalance("Bob", 10);
//    give alice 10
        System.out.println("\t\t\t give alice 10");

        l.addBalance("Alice", 10);
//    take 5 from alice
        System.out.println("\t\t\ttake 5 from alice");

        l.subtractBalance("Alice", 5);
        l.print();
        EntryList el1 = new EntryList("Alice", 15, "Bob", 10);
//    check el1 deductible from ledger
        System.out.println("\t\t\tCheck el1 deductible from ledger: " + el1.checkDeductableFromLedger(l));
//    check el2 deductible from ledger
        EntryList el2 = new EntryList("Alice", 10, "Alice", 10, "Bob", 5);
        System.out.println("\t\t\tCheck el2 deductible from ledger: " + el2.checkDeductableFromLedger(l));
//    subtract el1 from ledger
        System.out.println("\t\t\tsubtract el1 from ledger");
        el1.subtractFromLedger(l);
        l.print();
//    add el2 to ledger
        System.out.println("\t\t\tadd el2 to ledger");
        el2.addToLedger(l);
        l.print();

//create transaction tx1
        System.out.println("\t\t\tcreate transaction tx1");

        Transaction tx1 = new Transaction(new EntryList("Alice", 30), new EntryList("Bob", 20, "Carol", 20));
        l.checkTxValid(tx1);
//    create transaction tx2
        System.out.println("\t\t\tcreate transaction tx2");

        Transaction tx2 = new Transaction(new EntryList("Alice", 20), new EntryList("Bob", 5, "Carol", 15));
        l.checkTxValid(tx2);
//    create transaction tx3
        System.out.println("\t\t\tcreate transaction tx3");

        Transaction tx3 = new Transaction(new EntryList("Alice", 25), new EntryList("Bob", 10, "Carol", 15));
        l.checkTxValid(tx3);
        System.out.println("\t\t\tprocess transaction tx3");
        l.processTransaction(tx3);
//    create transaction tx4
        System.out.println("\t\t\tcreate transaction tx4");

        Transaction tx4 = new Transaction(new EntryList("Alice", 5, "Alice", 5), new EntryList("Bob", 10));
        l.checkTxValid(tx4);
        System.out.println("\t\t\tprocess transaction tx4");

        l.processTransaction(tx4);
        l.print();
    }


    
    /** 
     * main function running test cases
     */            

    public static void main(String[] args) {
	Ledger.test();	
    }
}
