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

//import bw.ac.bac.libis.classdemodb.config.DatabaseConfig;
//import bw.ac.bac.libis.classdemodb.model.Loan;
//
//import java.sql.*;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.List;
//
//public class LoanDAO {
//
//
//
//    // create new loan
//    public void create(Loan loan) {
//
//    }
//
//
//    public void markReturned(int loanId, LocalDate returnedOn) {
//
//    }
//
//
//    public int countOpenByMember(int memberId) {
//
//    }
//
//
//    public List<Loan> findOpenByMember(int memberId) {
//
//    }
//
//
//    public List<Loan> findHistoryByMember(int memberId) {
//
//    }
//
//
//    public boolean hasOpenLoan(int bookId) {

//    }
//}
