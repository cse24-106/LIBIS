package bw.ac.bac.libis.classdemodb.dao;

import bw.ac.bac.libis.classdemodb.config.DatabaseConfig;
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

        String sql = "SELECT b.book_Id, b.isbn, b.title, b.author, b.genre, b.published_year, b.synopsis, " +
                "(SELECT COUNT(*) FROM loans l WHERE l.book_id = b.book_Id AND l.returned_on IS NULL) = 0 AS available " +
                "FROM books b";

        try {
            Context context = new InitialContext();

            DataSource dataSource = (DataSource)
                    context.lookup("java:comp/env/jdbc/libis");

            try (Connection conn = DatabaseConfig.getDataSource().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql);
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {

                    Book book = new Book(
                            rs.getInt("book_Id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getInt("published_year"),
                            rs.getString("synopsis"),
                            rs.getString("isbn")
                    );

                    book.setAvailable(rs.getBoolean("available"));
                    books.add(book);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve books from the database.", e);
        }

        return books;
    }

    public Book findById(int bookId) {
        String sql = "SELECT b.book_Id, b.isbn, b.title, b.author, b.genre, b.published_year, b.synopsis, " +
                "(SELECT COUNT(*) FROM loans l WHERE l.book_id = b.book_Id AND l.returned_on IS NULL) = 0 AS available " +
                "FROM books b WHERE b.book_Id = ?";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = DatabaseConfig.getDataSource().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, bookId);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        Book book = new Book(
                                rs.getInt("book_Id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("genre"),
                                rs.getInt("published_year"),
                                rs.getString("synopsis"),
                                rs.getString("isbn")
                        );

                        book.setAvailable(rs.getBoolean("available"));
                        try { book.setIsbn(rs.getString("isbn")); } catch (Exception ignored) {}
                        return book;
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to find book by ID.", e);
        }
        return null;
    }

    public List<Book> search(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT b.book_Id, b.isbn, b.title, b.author, b.genre, b.published_year, b.synopsis, " +
                "(SELECT COUNT(*) FROM loans l WHERE l.book_id = b.book_Id AND l.returned_on IS NULL) = 0 AS available " +
                "FROM books b WHERE b.title LIKE ? OR b.author LIKE ?";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = DatabaseConfig.getDataSource().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                String pattern = "%" + keyword + "%";
                pst.setString(1, pattern);
                pst.setString(2, pattern);
                try (ResultSet rs = pst.executeQuery()) {
                    while (rs.next()) {
                        Book book = new Book(
                                rs.getInt("book_Id"),
                                rs.getString("title"),
                                rs.getString("author"),
                                rs.getString("genre"),
                                rs.getInt("published_year"),
                                rs.getString("synopsis"),
                                rs.getString("isbn")
                        );

                        book.setAvailable(rs.getBoolean("available"));
                        try { book.setIsbn(rs.getString("isbn")); } catch (Exception ignored) {}
                        books.add(book);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to search books.", e);
        }
        return books;
    }

    public void create(Book book) {
        String sql = "INSERT INTO books (isbn, title, author, genre, published_year, synopsis) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = DatabaseConfig.getDataSource().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, book.getIsbn());
                pst.setString(2, book.getTitle());
                pst.setString(3, book.getAuthor());
                pst.setString(4, book.getGenre());
                pst.setInt(5, book.getPublished_year());
                pst.setString(6, book.getSynopsis());
                pst.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create book.", e);
        }
    }

    public void update(Book book) {
        String sql = "UPDATE books SET isbn = ?, title = ?, author = ?, genre = ?, published_year = ?, synopsis = ? WHERE book_Id = ?";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = DatabaseConfig.getDataSource().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setString(1, book.getIsbn());
                pst.setString(2, book.getTitle());
                pst.setString(3, book.getAuthor());
                pst.setString(4, book.getGenre());
                pst.setInt(5, book.getPublished_year());
                pst.setString(6, book.getSynopsis());
                pst.setInt(7, book.getBook_Id());
                pst.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update book.", e);
        }
    }

    public void delete(int bookId) {
        String sql = "DELETE FROM books WHERE book_Id = ?";
        try {
            Context context = new InitialContext();
            DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/libis");
            try (Connection conn = DatabaseConfig.getDataSource().getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, bookId);
                pst.executeUpdate();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete book.", e);
        }
    }
}

