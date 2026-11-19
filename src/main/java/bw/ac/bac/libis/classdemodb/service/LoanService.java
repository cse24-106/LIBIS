package bw.ac.bac.libis.classdemodb.service;

import bw.ac.bac.libis.classdemodb.dao.BookDAO;
import bw.ac.bac.libis.classdemodb.dao.BookDAOImpl;
import bw.ac.bac.libis.classdemodb.dao.LoanDAO;
import bw.ac.bac.libis.classdemodb.dao.LoanDAOImpl;
import bw.ac.bac.libis.classdemodb.exception.LoanException;
import bw.ac.bac.libis.classdemodb.model.Book;
import bw.ac.bac.libis.classdemodb.model.Loan;

import java.time.LocalDate;
import java.util.List;

public class LoanService {

    private static final int MAX_OPEN_LOANS = 3;
    private final LoanDAO loanDAO;
    private final BookDAO bookDAO;

    public LoanService() {
        this(new LoanDAOImpl(), new BookDAOImpl());
    }

    public LoanService(LoanDAO loanDAO) {
        this(loanDAO, new BookDAOImpl());
    }

    public LoanService(LoanDAO loanDAO, BookDAO bookDAO) {
        if (loanDAO == null || bookDAO == null) {
            throw new IllegalArgumentException("DAOs cannot be null.");
        }
        this.loanDAO = loanDAO;
        this.bookDAO = bookDAO;
    }

    public void borrowBook(int memberId, int bookId) {
        Book book = bookDAO.findById(bookId);
        if (book == null) {
            throw new LoanException("The selected book could not be found.");
        }
        if (loanDAO.hasOpenLoan(bookId)) {
            throw new LoanException("'" + book.getTitle() + "' is currently out on loan.");
        }

        int currentOpenLoans = loanDAO.countOpenByMember(memberId);
        if (currentOpenLoans >= MAX_OPEN_LOANS) {
            throw new LoanException("Borrowing limit reached. You can only have " + MAX_OPEN_LOANS + " active loans at a time.");
        }

        loanDAO.create(new Loan(memberId, bookId, LocalDate.now()));
    }

    public void returnBook(int loanId) {
        loanDAO.markReturned(loanId, LocalDate.now());
    }

    public List<Loan> getCurrentLoans(int memberId) {
        return loanDAO.findOpenByMember(memberId);
    }

    public List<Loan> getBorrowingHistory(int memberId) {
        return loanDAO.findHistoryByMember(memberId);
    }
}
