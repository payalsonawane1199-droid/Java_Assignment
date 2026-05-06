import java.sql.*;
import java.util.*;

public class ReportDAO {

    private DataSource dataSource;

    public List<ReportEntry> fetchMonthlyReport(String accountId,
                                                int month, int year)
            throws SQLException {

        // FIX: try-with-resources prevents connection, statement, resultset leak
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     "SELECT * FROM report_entries " +
                     "WHERE account_id = ? AND MONTH(entry_date) = ? " +
                     "AND YEAR(entry_date) = ?")) {

            ps.setString(1, accountId);
            ps.setInt(2, month);
            ps.setInt(3, year);

            try (ResultSet rs = ps.executeQuery()) {

                List<ReportEntry> entries = new ArrayList<>();

                while (rs.next()) {
                    entries.add(mapRow(rs));
                }

                return entries;
            }
        }
    }

    private ReportEntry mapRow(ResultSet rs) {
        return new ReportEntry();
    }
}
