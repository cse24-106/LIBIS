package bw.ac.bac.libis.classdemodb.dao;

import bw.ac.bac.libis.classdemodb.model.Book;
import java.util.List;

public interface BookDAO {
    List<Book> getAllBooks();
    Book findById(int bookId);
    List<Book> search(String keyword);
    Book findByIsbn(String isbn);
    void create(Book book);
    void update(Book book);
    void delete(int bookId);
}
