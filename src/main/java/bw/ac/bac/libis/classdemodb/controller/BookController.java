package bw.ac.bac.libis.classdemodb.controller;

import bw.ac.bac.libis.classdemodb.dao.BookDAO;
import bw.ac.bac.libis.classdemodb.dao.LoanDAO;
import bw.ac.bac.libis.classdemodb.model.Book;

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

    private BookDAO bookDAO;
    private LoanService loanService;

    public void init() {
        bookDAO = new BookDAO();
        LoanDAO loanDao = new LoanDAO();
        this.loanService = new LoanService(loanDao);
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

        try {
            List<Book> books = bookDAO.getAllBooks();
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

        String id = request.getParameter("id");

        int bookId = Integer.parseInt(id);

        List<Book> books = bookDAO.getAllBooks();

        for (Book book : books) {

            if (book.getBook_Id() == bookId) {

                request.setAttribute("book", book);

                request.getRequestDispatcher("/WEB-INF/views/book-details.jsp").forward(request, response);

                return;
            }
        }

        response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "Book not found"
        );
    }

}