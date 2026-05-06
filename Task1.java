public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {
    // FIX: initialize result list to avoid NullPointerException when adding accounts
    List<LoanAccount> result = new ArrayList<>();

    // FIX: handle null input list to avoid NullPointerException during iteration
    if (accounts == null) {
        return result;
    }

    for (LoanAccount account : accounts) {
        // FIX: check dueDate for null because restructured accounts may not have a due date
        if (account.getDueDate() != null && account.getDueDate().before(new Date())) {
            // FIX: only accounts with positive outstanding balance should be added
            if (account.getOutstandingBalance() > 0) {
                result.add(account);
            }
        }
    }
    return result;
}