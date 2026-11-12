package bw.ac.bac.libis.classdemodb.model;

import java.time.LocalDate;

public class Loan {
    private int loanId;
    private int memberId;
    private int bookId;
    private LocalDate borrowedOn;
    private LocalDate returnedOn;

    //constructor
    public Loan(int loanId, int memberId, int bookId, LocalDate borrowedOn, LocalDate returnedOn) {
        this.loanId = loanId;
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowedOn = borrowedOn;
        this.returnedOn = returnedOn;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getBorrowedOn() {
        return borrowedOn;
    }

    public void setBorrowedOn(LocalDate borrowedOn) {
        this.borrowedOn = borrowedOn;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public void setReturnedOn(LocalDate returnedOn) {
        this.returnedOn = returnedOn;
    }

    // constructor for creating a new loan
    public Loan(int memberId, int bookId, LocalDate borrowedOn) {
        this.memberId = memberId;
        this.bookId = bookId;
        this.borrowedOn = borrowedOn;
        this.returnedOn = null;
    }

    // checks if the loan is still active
    public boolean isOpen() {
        return this.returnedOn == null;
    }
}
