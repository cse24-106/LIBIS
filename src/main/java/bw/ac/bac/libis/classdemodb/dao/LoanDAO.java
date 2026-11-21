package bw.ac.bac.libis.classdemodb.dao;

import bw.ac.bac.libis.classdemodb.model.Loan;
import java.time.LocalDate;
import java.util.List;

public interface LoanDAO {
    void create(Loan loan);
    void markReturned(int loanId, LocalDate returnedOn);
    int countOpenByMember(int memberId);
    List<Loan> findOpenByMember(int memberId);
    List<Loan> findHistoryByMember(int memberId);
    boolean hasOpenLoan(int bookId);
}
