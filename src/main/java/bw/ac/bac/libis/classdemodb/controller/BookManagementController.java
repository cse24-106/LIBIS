package bw.ac.bac.libis.classdemodb.controller;

import bw.ac.bac.libis.classdemodb.dao.BookDAO;
import bw.ac.bac.libis.classdemodb.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "bookManagement", value = "/librarian/books")
public class BookManagementController extends HttpServlet {

    private BookDAO bookDAO;

    @Override
    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "list":
                listBooks(request, response);
                break;
            case "addForm":
                request.getRequestDispatcher("/WEB-INF/views/librarian/book-form.jsp").forward(request, response);
                break;
            case "editForm":
                showEditForm(request, response);
                break;
            case "delete":
                deleteBook(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            addBook(request, response);
        } else if ("update".equals(action)) {
            updateBook(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }

    private void listBooks(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String keyword = request.getParameter("search");
        List<Book> books;
        if (keyword != null && !keyword.trim().isEmpty()) {
            books = bookDAO.search(keyword);
        } else {
            books = bookDAO.getAllBooks();
        }
        request.setAttribute("books", books);
        request.getRequestDispatcher("/WEB-INF/views/librarian/book-list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Book book = bookDAO.findById(id);
        request.setAttribute("book", book);
        request.getRequestDispatcher("/WEB-INF/views/librarian/book-form.jsp").forward(request, response);
    }

    private void addBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        int publishedYear = Integer.parseInt(request.getParameter("publishedYear"));
        String synopsis = request.getParameter("synopsis");
        String isbn = request.getParameter("isbn");

        // this prevents no future years
        if (publishedYear > LocalDate.now().getYear()) {
            request.setAttribute("errorMessage", "Published year cannot be in the future.");
            request.getRequestDispatcher("/WEB-INF/views/librarian/book-form.jsp").forward(request, response);
            return;
        }

        Book book = new Book(0, title, author, genre, publishedYear, synopsis, isbn);
        book.setIsbn(isbn);

        try {
            bookDAO.create(book);
            response.sendRedirect("books?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error adding book: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/views/librarian/book-form.jsp").forward(request, response);
        }
    }

    private void updateBook(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("bookId"));
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String genre = request.getParameter("genre");
        int publishedYear = Integer.parseInt(request.getParameter("publishedYear"));
        String synopsis = request.getParameter("synopsis");
        String isbn = request.getParameter("isbn");

        Book book = new Book(id, title, author, genre, publishedYear, synopsis, isbn);
        book.setIsbn(isbn);

        try {
            bookDAO.update(book);
            response.sendRedirect("books?action=list");
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error updating book: " + e.getMessage());
            request.setAttribute("book", book);
            request.getRequestDispatcher("/WEB-INF/views/librarian/book-form.jsp").forward(request, response);
        }
    }

    private void deleteBook(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        int id = Integer.parseInt(request.getParameter("id"));

        Book book = bookDAO.findById(id);
        if (book != null && !book.isAvailable()) {
            request.setAttribute("errorMessage", "Cannot delete '" + book.getTitle() + "' because it is currently out on loan.");
            listBooks(request, response);
            return;
        }

        bookDAO.delete(id);
        response.sendRedirect("books?action=list");
    }
}
