# Java_Assignment
# Task 2

## 1. What is the exact cause of ConcurrentModificationException in Java?

ConcurrentModificationException occurs when a collection (like ArrayList) is modified while it is being iterated using an iterator or for-each loop. Java collections are fail-fast, meaning they detect such modifications and throw this exception.

## 2. What code pattern caused this error?

for (Transaction txn : transactions) {
    if (condition) {
        transactions.remove(txn);
    }
}

## 3. Minimal fix

Iterator<Transaction> it = transactions.iterator();
while (it.hasNext()) {
    if (condition) {
        it.remove();
    }
}
