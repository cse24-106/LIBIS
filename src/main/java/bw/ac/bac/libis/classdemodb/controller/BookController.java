package bw.ac.bac.libis.classdemodb.controller;

import bw.ac.bac.libis.classdemodb.dao.LoanDAO;
import bw.ac.bac.libis.classdemodb.model.Book;

import bw.ac.bac.libis.classdemodb.model.Member;
import bw.ac.bac.libis.classdemodb.service.MemberService;
import bw.ac.bac.libis.classdemodb.service.BookService;
import bw.ac.bac.libis.classdemodb.service.LoanService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "bookServlet", value = "/book")
public class BookController extends HttpServlet {

    private LoanService loanService;
    private BookService bookService;
    private MemberService memberService;

    public void init() {
        loanService = new LoanService();
        bookService = new BookService();
        memberService = new MemberService();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        if (action == null) {
            action = "getBooks";
        }

        switch (action) {
            case "getBooks":
                getBooks(request, response);
                break;
            case "borrowBook":
                borrowBook(request, response);
                break;
            case "viewBook":
                viewBook(request, response);
                break;
            case "searchBook":
                searchBook(request, response);
                break;

            default:
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Unknown action"
                );
                break;
        }
    }

    // gets all books from the database to display on the welcome page.
    private  void getBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        attachCurrentMember(request);

        try {
            List<Book> books = bookService.getAllBooks();
            request.setAttribute("books", books);

        } catch (RuntimeException e) {
            log("Failed to retrieve books from the database.", e);

            request.setAttribute(
                    "errorMessage",
                    "Unable to load the book catalogue. Please try again later."
            );

            request.setAttribute("books", Collections.emptyList());
        }


        request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
    }

    private void attachCurrentMember(HttpServletRequest request) {
        String remoteUser = request.getRemoteUser();
        if (remoteUser == null || remoteUser.isEmpty()) {
            return;
        }
        try {
            Member member = memberService.getMemberByUsername(remoteUser.trim());
            if (member == null) {
                member = memberService.getMemberByEmail(remoteUser.trim());
            }
            if (member != null) {
                request.setAttribute("currentMember", member);
            }
        } catch (RuntimeException e) {
            log("Failed to resolve current member for user: " + remoteUser, e);
        }
    }

    // when the user clicks borrow book
    private void borrowBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int memberId = (int) request.getSession().getAttribute("memberId");
            int bookId = Integer.parseInt(request.getParameter("bookId"));

            loanService.borrowBook(memberId, bookId);
            response.sendRedirect("my-loans");

        } catch (IllegalStateException e) {
            request.setAttribute("errorMessage", e.getMessage());
            request.getRequestDispatcher("/books.jsp").forward(request, response);
        }
    }

    // when the user wants to see more details about a book
    private void viewBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing book ID");
            return;
        }

        try {
            int bookId = Integer.parseInt(idParam.trim());
            Book book = bookService.getBookById(bookId);

            if (book == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Book not found");
                return;
            }

            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/views/book-details.jsp").forward(request, response);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid book ID");
        } catch (RuntimeException e) {
            log("Failed to retrieve book details.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load book details");
        }
    }

    //search books
    private void searchBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        attachCurrentMember(request);

        String searchTerm = request.getParameter("searchTerm");
        String trimmedTerm = (searchTerm == null) ? "" : searchTerm.trim();

        List<Book> searchResults;
        try {
            searchResults = trimmedTerm.isEmpty()
                    ? bookService.getAllBooks()
                    : bookService.searchBooks(trimmedTerm);
        } catch (IllegalStateException e) {
            log("Failed to search books for term: " + trimmedTerm, e);
            searchResults = Collections.emptyList();
            request.setAttribute("errorMessage", "Unable to search books right now. Please try again.");
        }

        request.setAttribute("books", searchResults);
        request.setAttribute("searchTerm", trimmedTerm);

        request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
        }
    }