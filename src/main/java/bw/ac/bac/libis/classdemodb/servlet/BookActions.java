package bw.ac.bac.libis.classdemodb.servlet;

//TODO: Put this into BookServlet once it is working

import bw.ac.bac.libis.classdemodb.data.BookDAO;
import bw.ac.bac.libis.classdemodb.model.Book;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet (name = "bookActions", value = "/bookact")
public class BookActions extends HttpServlet {

    private BookDAO bookDAO;

    public void init() {
        bookDAO = new BookDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String id = request.getParameter("id");

        if ("view".equals(action)) {

            int bookId = Integer.parseInt(id);

            List<Book> books = bookDAO.getAllBooks();

            for (Book book : books) {

                if (book.getBookId() == bookId) {

                    request.setAttribute("book", book);

                    request.getRequestDispatcher("/WEB-INF/views/book-details.jsp").forward(request, response);

                    return;
                }
            }
        }

        response.sendError(
                HttpServletResponse.SC_NOT_FOUND,
                "Book not found"
        );
    }
}
