package bw.ac.bac.libis.classdemodb.servlet;

import bw.ac.bac.libis.classdemodb.data.BookDAO;
import bw.ac.bac.libis.classdemodb.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@WebServlet(name = "bookServlet", value = "/book")
public class BookServlet extends HttpServlet {

    private BookDAO bookDAO;

    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

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

        System.out.println("Going to Welcome.jsp");
        request.getRequestDispatcher("/WEB-INF/views/welcome.jsp").forward(request, response);
    }

}