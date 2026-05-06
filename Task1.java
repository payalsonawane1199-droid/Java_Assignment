public List<LoanAccount> getOverdueLoans(List<LoanAccount> accounts) {
    // FIX: initialize result list to avoid NullPointerException
    List<LoanAccount> result = new ArrayList<>();

    // FIX: null check for input list
    if (accounts == null) {
        return result;
    }

    Date today = new Date(); // FIX

    for (LoanAccount account : accounts) {
        // FIX: null check for account and dueDate
        if (account != null &&
            account.getDueDate() != null &&
            account.getDueDate().before(today)) {

            // FIX: ensure positive balance only
            if (account.getOutstandingBalance() > 0) {
                result.add(account);
            }
        }
    }
    return result;
}
