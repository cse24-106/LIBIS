package bw.ac.bac.libis.classdemodb.exception;

public class LoanException extends RuntimeException {

    public LoanException(String message) {
        super(message);
    }

    public LoanException(String message, Throwable cause) {
        super(message, cause);
    }
}
