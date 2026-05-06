public class ReportDAO {

    private DataSource dataSource;

    public List<ReportEntry> fetchMonthlyReport(String accountId,
                                                int month, int year)
            throws SQLException {

        // FIX: use try-with-resources so ResultSet, PreparedStatement and Connection close automatically
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM report_entries " +
                     "WHERE account_id = ? AND MONTH(entry_date) = ? " +
                     "AND YEAR(entry_date) = ?");
             ResultSet rs = ps.executeQuery()) {

            ps.setString(1, accountId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            List<ReportEntry> entries = new ArrayList<>();

            while (rs.next()) {
                entries.add(mapRow(rs));
            }
            return entries;
        }
    }
}