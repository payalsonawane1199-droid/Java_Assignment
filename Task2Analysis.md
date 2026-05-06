# Java_Assignment
# Task 2

1. ConcurrentModificationException is thrown when an ArrayList is structurally modified while it is being traversed using an Iterator or enhanced for-loop. Java iterators are fail-fast and compare the collection modification count during iteration. Any direct add/remove operation on the same list causes this mismatch and throws the exception.

2. Line 142 in filterTransactions() most likely contains a loop iterating over the transactions list and removing elements directly from that same list. A likely pattern is:

   for (Transaction txn : transactions) {
       if (condition) {
           transactions.remove(txn);
       }
   }

   This modifies the ArrayList during iterator traversal.

3. Minimal safe fix:
   Replace direct list removal with iterator-based removal.

   Iterator<Transaction> it = transactions.iterator();
   ...
   it.remove();

   Using Iterator.remove() keeps iterator state synchronized with the collection and safely prevents ConcurrentModificationException.

   ----------------------------------------------------------------------------------------------------------------------------------------------


   1. What is the exact cause of ConcurrentModificationException in Java?

ConcurrentModificationException happens when an ArrayList (or similar collection) is modified while it is being traversed using an iterator or enhanced for-loop.

In Java, these iterators are fail-fast, which means they keep checking whether the list has been structurally changed after the loop starts. If code removes or adds elements directly to that same list during iteration, the iterator detects that the collection state is no longer the same and throws this exception.

So in simple terms, the exception is caused by changing the list while looping through it.


2. What code pattern at line 142 most likely triggered this error?

From the stack trace, the failure occurs inside filterTransactions() while ArrayList$Itr.next() is executing. This strongly suggests that line 142 contains a loop over the transaction list, and inside that loop the same list is being modified.

A likely pattern is:

for (Transaction txn : transactions) {
    if (condition) {
        transactions.remove(txn);
    }
}

or any similar case where transactions.add() or transactions.remove() is called on the list currently being iterated.

Because the iterator is still active, this direct modification breaks the iteration and leads to ConcurrentModificationException.


3. Provide the minimal code change (one or two lines) that resolves this safely.

The minimal safe correction is to use an iterator explicitly and remove elements through the iterator instead of the list.

Iterator<Transaction> it = transactions.iterator();

and where removal is needed:

it.remove();

Iterator.remove() updates the iterator and the list together, so the traversal remains valid and the exception is avoided.
