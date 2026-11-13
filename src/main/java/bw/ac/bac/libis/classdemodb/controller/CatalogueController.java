package bw.ac.bac.libis.classdemodb.controller;

import bw.ac.bac.libis.classdemodb.dao.BookDAO;
import bw.ac.bac.libis.classdemodb.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@WebServlet("/catalogue")
public class CatalogueController extends HttpServlet {

    private BookDAO bookDAO;

    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        //reads the term from the form
        String searchTerm = request.getParameter("searchTerm");

        if (searchTerm == null) {
            searchTerm = "";
        }

        //collects the book list from the application scope
        List<Book> books = bookDAO.getAllBooks();

        //handle a missing book list
        if  (books == null) {
            books = new ArrayList<>();
        }

        //prepare a list for matching books
        List<Book> searchResults = new ArrayList<>();

        //filter books by title
        String searchLower = searchTerm.toLowerCase(Locale.ROOT);

        for (Book book : books) {
            if (book.getTitle() != null && book.getTitle().toLowerCase().contains(searchLower)) {
                searchResults.add(book);
            }
        }

        //send the results and search-term to the JSP
        request.setAttribute("searchResults", searchResults);
        request.setAttribute("searchTerm", searchTerm);

        //forward it to the view
        request.getRequestDispatcher("/WEB-INF/views/catalogue.jsp").forward(request, response);

    }
}
