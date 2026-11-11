package bw.ac.bac.libis.classdemodb.data;

import bw.ac.bac.libis.classdemodb.model.Book;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() {

        List<Book> books = new ArrayList<>();

        String sql = "SELECT bookId, title, author, genre, year, synopsis, available FROM books";

        try {
            Context context = new InitialContext();

            DataSource dataSource = (DataSource)
                    context.lookup("java:comp/env/jdbc/libis");

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    Book book = new Book(
                            rs.getInt("bookId"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getInt("year"),
                            rs.getString("synopsis"),
                            rs.getBoolean("available")
                    );

                    books.add(book);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve books from the database.", e);
        }

        return books;
    }
}

