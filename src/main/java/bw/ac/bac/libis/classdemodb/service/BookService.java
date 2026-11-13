package bw.ac.bac.libis.classdemodb.service;

import bw.ac.bac.libis.classdemodb.dao.BookDAO;
import bw.ac.bac.libis.classdemodb.model.Book;

import java.time.LocalDate;
import java.util.List;

public class BookService {

    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAO();
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Book getBookById(int id) {
        return bookDAO.findById(id);
    }

    public List<Book> searchBooks(String keyword) {
        return bookDAO.search(keyword);
    }

    public void addBook(Book book) {
        validateBook(book);
        bookDAO.create(book);
    }

    public void updateBook(Book book) {
        validateBook(book);
        bookDAO.update(book);
    }

    public void deleteBook(int bookId) {
        Book book = bookDAO.findById(bookId);
        if (book != null && !book.isAvailable()) {
            throw new IllegalStateException("Cannot delete '" + book.getTitle() + "' because it is currently out on loan.");
        }
        bookDAO.delete(bookId);
    }

    private void validateBook(Book b) {
        if (b.getTitle() == null || b.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Book title is required.");
        }
        if (b.getAuthor() == null || b.getAuthor().trim().isEmpty()) {
            throw new IllegalArgumentException("Author is required.");
        }
        int currentYear = LocalDate.now().getYear();
        if (b.getPublished_year() > currentYear) {
            throw new IllegalArgumentException("Published year cannot be in the future.");
        }
    }
}
