package bw.ac.bac.libis.classdemodb.controller;

import bw.ac.bac.libis.classdemodb.dao.MemberDAO;
import bw.ac.bac.libis.classdemodb.model.Book;
import bw.ac.bac.libis.classdemodb.model.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "loanController", value = "/loans")
public class LoanController extends HttpServlet {

    private DataSource getDataSource() {
        try {
            Context context = new InitialContext();
            return (DataSource) context.lookup("java:comp/env/jdbc/libis");
        } catch (Exception e) {
            throw new RuntimeException("Failed to lookup DataSource", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "myLoans";
        }

        if ("myLoans".equals(action)) {
            showMyLoans(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("borrow".equals(action)) {
            borrowBook(request, response);
        } else if ("return".equals(action)) {
            returnBook(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void showMyLoans(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get logged-in member using jakarta security container email
        String email = request.getRemoteUser();
        MemberDAO memberDAO = new MemberDAO();
        Member member = memberDAO.findByEmail(email);

        if (member == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // get current open loans for this member
        List<Book> myLoans = new ArrayList<>();
        String sql = "SELECT b.* FROM books b JOIN loans l ON b.book_Id = l.book_id WHERE l.member_id = ? AND l.returned_on IS NULL";

        try (Connection conn = getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, member.getMember_Id());
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    myLoans.add(new Book(
                            rs.getInt("book_Id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getInt("published_year"),
                            rs.getString("synopsis"),
                            rs.getString("isbn")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching member loans", e);
        }

        request.setAttribute("myLoans", myLoans);
        request.getRequestDispatcher("/WEB-INF/views/my-loans.jsp").forward(request, response);
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String email = request.getRemoteUser();
        MemberDAO memberDAO = new MemberDAO();
        Member member = memberDAO.findByEmail(email);

        try (Connection conn = getDataSource().getConnection()) {
            // checking if member has 3 open loans
            String countSql = "SELECT COUNT(*) FROM loans WHERE member_id = ? AND returned_on IS NULL";
            try (PreparedStatement pst = conn.prepareStatement(countSql)) {
                pst.setInt(1, member.getMember_Id());
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next() && rs.getInt(1) >= 3) {
                        request.setAttribute("errorMessage", "Borrowing limit reached. You can only have 3 active loans.");
                        response.sendRedirect("book?action=getBooks");
                        return;
                    }
                }
            }

            // inserting a new loan
            String insertSql = "INSERT INTO loans (member_id, book_id, borrowed_on) VALUES (?, ?, ?)";
            try (PreparedStatement pst = conn.prepareStatement(insertSql)) {
                pst.setInt(1, member.getMember_Id());
                pst.setInt(2, bookId);
                pst.setDate(3, Date.valueOf(LocalDate.now()));
                pst.executeUpdate();
            }

            response.sendRedirect("loans?action=myLoans");
        } catch (Exception e) {
            throw new RuntimeException("Error processing book borrowing", e);
        }
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        String email = request.getRemoteUser();
        MemberDAO memberDAO = new MemberDAO();
        Member member = memberDAO.findByEmail(email);

        String sql = "UPDATE loans SET returned_on = ? WHERE member_id = ? AND book_id = ? AND returned_on IS NULL";
        try (Connection conn = getDataSource().getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setDate(1, Date.valueOf(LocalDate.now()));
            pst.setInt(2, member.getMember_Id());
            pst.setInt(3, bookId);
            pst.executeUpdate();

            response.sendRedirect("loans?action=myLoans");
        } catch (SQLException e) {
            throw new RuntimeException("Error returning book", e);
        }
    }
}
