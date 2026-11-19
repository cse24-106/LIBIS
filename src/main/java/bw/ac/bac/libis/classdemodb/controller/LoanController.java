package bw.ac.bac.libis.classdemodb.controller;

import bw.ac.bac.libis.classdemodb.exception.LoanException;
import bw.ac.bac.libis.classdemodb.model.Book;
import bw.ac.bac.libis.classdemodb.model.Loan;
import bw.ac.bac.libis.classdemodb.model.Member;
import bw.ac.bac.libis.classdemodb.service.BookService;
import bw.ac.bac.libis.classdemodb.service.LoanService;
import bw.ac.bac.libis.classdemodb.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet(name = "loanController", value = "/loans")
public class LoanController extends HttpServlet {

    private LoanService loanService;
    private MemberService memberService;
    private BookService bookService;

    @Override
    public void init() {
        this.loanService = new LoanService();
        this.memberService = new MemberService();
        this.bookService = new BookService();
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

        Member member = resolveAuthenticatedMember(request, response);
        if (member == null) {
            return;
        }

        request.setAttribute("currentMember", member);

        try {
            List<Loan> openLoans = loanService.getCurrentLoans(member.getMember_Id());
            List<Book> myLoans = new ArrayList<>();
            for (Loan loan : openLoans) {
                Book book = bookService.getBookById(loan.getBookId());
                if (book != null) {
                    myLoans.add(book);
                }
            }
            request.setAttribute("myLoans", myLoans);
        } catch (RuntimeException e) {
            log("Failed to load loans for member ID: " + member.getMember_Id(), e);
            request.setAttribute("errorMessage", "Unable to load your loans right now. Please try again later.");
            request.setAttribute("myLoans", Collections.emptyList());
        }

        request.getRequestDispatcher("/WEB-INF/views/my-loans.jsp").forward(request, response);
    }

    private void borrowBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Member member = resolveAuthenticatedMember(request, response);
        if (member == null) {
            return;
        }

        String bookIdParam = request.getParameter("bookId");
        if (bookIdParam == null || bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing book ID");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdParam.trim());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID");
            return;
        }

        try {
            loanService.borrowBook(member.getMember_Id(), bookId);
            response.sendRedirect(request.getContextPath() + "/loans?action=myLoans");
        } catch (LoanException | IllegalStateException | IllegalArgumentException e) {
            request.setAttribute("currentMember", member);
            request.setAttribute("errorMessage", e.getMessage());
            request.setAttribute("books", bookService.getAllBooks());
            request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
        } catch (RuntimeException e) {
            log("Unexpected error while borrowing book ID " + bookId + " for member ID " + member.getMember_Id(), e);
            request.setAttribute("currentMember", member);
            request.setAttribute("errorMessage", "Borrowing limit reached. You can only have 3 active loans.");
            request.setAttribute("books", bookService.getAllBooks());
            request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
        }
    }

    private void returnBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Member member = resolveAuthenticatedMember(request, response);
        if (member == null) {
            return;
        }

        String bookIdParam = request.getParameter("bookId");
        if (bookIdParam == null || bookIdParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing book ID");
            return;
        }

        int bookId;
        try {
            bookId = Integer.parseInt(bookIdParam.trim());
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID");
            return;
        }

        try {
            List<Loan> openLoans = loanService.getCurrentLoans(member.getMember_Id());
            Loan matchingLoan = null;
            for (Loan loan : openLoans) {
                if (loan.getBookId() == bookId) {
                    matchingLoan = loan;
                    break;
                }
            }

            if (matchingLoan == null) {
                throw new LoanException("No active loan found for this book.");
            }

            loanService.returnBook(matchingLoan.getLoanId());
            response.sendRedirect(request.getContextPath() + "/loans?action=myLoans");
        } catch (LoanException | IllegalStateException | IllegalArgumentException e) {
            request.setAttribute("errorMessage", e.getMessage());
            showMyLoans(request, response);
        } catch (RuntimeException e) {
            log("Unexpected error while returning book ID " + bookId + " for member ID " + member.getMember_Id(), e);
            request.setAttribute("errorMessage", "Unable to return book right now. Please try again later.");
            showMyLoans(request, response);
        }
    }

    private Member resolveAuthenticatedMember(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String principal = request.getRemoteUser();
        if (principal == null || principal.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
            return null;
        }

        String trimmed = principal.trim();
        Member member = memberService.getMemberByUsername(trimmed);
        if (member == null) {
            member = memberService.getMemberByEmail(trimmed);
        }

        if (member == null) {
            log("Authenticated principal '" + trimmed + "' does not match a member record.");
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Member profile not found");
            return null;
        }

        return member;
    }
}
