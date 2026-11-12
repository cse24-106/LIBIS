package bw.ac.bac.libis.classdemodb.model;

public class Book {
    private int book_Id;
    private String title;
    private String author;
    private String genre;
    private int published_year;
    private String synopsis;
    private String isbn;

    public Book() {
    }

    public Book(int book_Id, String title, String author, String genre, int published_year, String synopsis, String isbn) {
        this.book_Id = book_Id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.published_year = published_year;
        this.synopsis = synopsis;
        this.isbn = isbn;
    }

    public int getBook_Id() {
        return book_Id;
    }

    public void setBook_Id(int book_Id) {
        this.book_Id = book_Id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPublished_year() {
        return published_year;
    }

    public void setPublished_year(int published_year) {
        this.published_year = published_year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public void setIsbn(String isbn) {
    }

    public String getIsbn() {
        return isbn;
    }
}
