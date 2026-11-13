package bw.ac.bac.libis.classdemodb.service;

import bw.ac.bac.libis.classdemodb.model.Loan;
import bw.ac.bac.libis.classdemodb.dao.LoanDAO;

import java.time.LocalDate;
import java.util.List;

public class LoanService {

    private static final int MAX_OPEN_LOANS = 3;
    private final LoanDAO loanDao;

    public LoanService(LoanDAO loanDao) {
        this.loanDao = new LoanDAO();
    }

    public void borrowBook(int memberId, int bookId) {
        // check if the book is already borrowed
        if (loanDao.hasOpenLoan(bookId)) {
            throw new IllegalStateException("This book is currently out on loan and unavailable.");
        }

        // 3 book maximum limit per member
        int currentOpenLoans = loanDao.countOpenByMember(memberId);
        if (currentOpenLoans >= MAX_OPEN_LOANS) {
            throw new IllegalStateException("Borrowing limit reached. Members may only have " + MAX_OPEN_LOANS + " active loans.");
        }

        // new loan with date
        Loan newLoan = new Loan(memberId, bookId, LocalDate.now());
        loanDao.create(newLoan);
    }

    public void returnBook(int loanId) {
        loanDao.markReturned(loanId, LocalDate.now());
    }

    public List<Loan> getCurrentLoans(int memberId) {
        return loanDao.findOpenByMember(memberId);
    }

    public List<Loan> getBorrowingHistory(int memberId) {
        return loanDao.findHistoryByMember(memberId);
    }
}
