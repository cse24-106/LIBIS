package bw.ac.bac.libis.classdemodb.data;

import bw.ac.bac.libis.classdemodb.model.Loan;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LoanDAO {

    private DataSource dataSource;

    public LoanDAO() {
        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:comp/env");
            this.dataSource = (DataSource) envContext.lookup("jdbc/libis");
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DataSource from JNDI", e);
        }
    }

    // map a ResultSet row to a Loan object
    private Loan mapRowToLoan(ResultSet rs) throws SQLException {
        int loanId = rs.getInt("loan_id");
        int memberId = rs.getInt("member_id");
        int bookId = rs.getInt("book_id");
        LocalDate borrowedOn = rs.getDate("borrowed_on").toLocalDate();

        Date returnedOnSql = rs.getDate("returned_on");
        LocalDate returnedOn = (returnedOnSql != null) ? returnedOnSql.toLocalDate() : null;

        return new Loan(loanId, memberId, bookId, borrowedOn, returnedOn);
    }

    // create new loan
    public void create(Loan loan) {
        String sql = "INSERT INTO loans (member_id, book_id, borrowed_on, returned_on) VALUES (?, ?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, loan.getMemberId());
            stmt.setInt(2, loan.getBookId());
            stmt.setDate(3, Date.valueOf(loan.getBorrowedOn()));

            if (loan.getReturnedOn() != null) {
                stmt.setDate(4, Date.valueOf(loan.getReturnedOn()));
            } else {
                stmt.setNull(4, Types.DATE);
            }

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error creating loan record", e);
        }
    }

    // mark loan as returned
    public void markReturned(int loanId, LocalDate returnedOn) {
        String sql = "UPDATE loans SET returned_on = ? WHERE loan_id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(returnedOn));
            stmt.setInt(2, loanId);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error marking loan as returned for loan ID: " + loanId, e);
        }
    }

    // counts outstanding loans for a member
    public int countOpenByMember(int memberId) {
        String sql = "SELECT COUNT(*) FROM loans WHERE member_id = ? AND returned_on IS NULL";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error counting open loans for member ID: " + memberId, e);
        }
        return 0;
    }

    // find outstanding loans for a member
    public List<Loan> findOpenByMember(int memberId) {
        String sql = "SELECT loan_id, member_id, book_id, borrowed_on, returned_on " +
                "FROM loans WHERE member_id = ? AND returned_on IS NULL";
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapRowToLoan(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding open loans for member ID: " + memberId, e);
        }
        return loans;
    }

    // find complete borrowing history for a member
    public List<Loan> findHistoryByMember(int memberId) {
        String sql = "SELECT loan_id, member_id, book_id, borrowed_on, returned_on " +
                "FROM loans WHERE member_id = ?";
        List<Loan> loans = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, memberId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    loans.add(mapRowToLoan(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding borrowing history for member ID: " + memberId, e);
        }
        return loans;
    }

    // checks if a book currently has an open loan
    public boolean hasOpenLoan(int bookId) {
        String sql = "SELECT COUNT(*) FROM loans WHERE book_id = ? AND returned_on IS NULL";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bookId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking open loan status for book ID: " + bookId, e);
        }
        return false;
    }
}
