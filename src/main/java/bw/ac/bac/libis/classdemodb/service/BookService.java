package bw.ac.bac.libis.classdemodb.service;

import bw.ac.bac.libis.classdemodb.dao.BookDAO;
import bw.ac.bac.libis.classdemodb.dao.BookDAOImpl;
import bw.ac.bac.libis.classdemodb.exception.ValidationException;
import bw.ac.bac.libis.classdemodb.model.Book;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BookService {

    private final BookDAO bookDAO;

    public BookService() {
        this.bookDAO = new BookDAOImpl();
    }

    public BookService(BookDAO bookDAO) {
        if (bookDAO == null) {
            throw new IllegalArgumentException("BookDAO cannot be null.");
        }
        this.bookDAO = bookDAO;
    }

    public List<Book> getAllBooks() {
        return bookDAO.getAllBooks();
    }

    public Book getBookById(int id) {
        return bookDAO.findById(id);
    }

    public List<Book> searchBooks(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return bookDAO.getAllBooks();
        }
        return bookDAO.search(keyword.trim());
    }

    public void addBook(Book book) {
        validateBook(book );
        checkDuplicateIsbn(book.getIsbn(), null);
        bookDAO.create(book);
    }

    public void updateBook(Book book) {
        validateBook(book);
        checkDuplicateIsbn(book.getIsbn(), book.getBook_Id());
        bookDAO.update(book);
    }

    public void deleteBook(int bookId) {
        Book book = bookDAO.findById(bookId);
        if (book != null && !book.isAvailable()) {
            throw new IllegalStateException("Cannot delete '" + book.getTitle() + "' because it is currently out on loan.");
        }
        bookDAO.delete(bookId);
    }

    private void checkDuplicateIsbn(String isbn, Integer currentBookId) {
        if (isbn == null || isbn.isEmpty()) {
            return;
        }

        Book existing = bookDAO.findByIsbn(isbn.trim());
        if (existing != null && (currentBookId == null || existing.getBook_Id() != currentBookId)) {
            throw new IllegalArgumentException("A book with ISBN '" + isbn.trim() + "' already exists.");
        }
    }

    private void validateBook(Book b) {

        Map<String, String> errors = new LinkedHashMap<>();

        if (b == null) {
            throw new ValidationException("Book details are required.");
        }

        if (b.getTitle() == null || b.getTitle().trim().isEmpty()) {
            errors.put("title", "Book title is required.");
        }

        if (b.getAuthor() == null || b.getAuthor().trim().isEmpty()) {
            errors.put("author", "Author is required.");
        }

        int currentYear = LocalDate.now().getYear();
        if (b.getPublished_year() > currentYear) {
            errors.put("publishedYear", "Published year cannot be in the future (" + currentYear + ").");
        }

        if (b.getIsbn() != null && !b.getIsbn().isEmpty()) {
            Book existing = bookDAO.findByIsbn(b.getIsbn().trim());
            if (existing != null && (existing.getBook_Id() != b.getBook_Id())) {
                errors.put("isbn", "A book with this ISBN already exists.");
            }
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Please fix the highlighted errors below.", errors);
        }
    }
}
