package bw.ac.bac.libis.classdemodb.exception;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {

    private final Map<String, String> fieldErrors;

    public ValidationException(String message) {
        super(message);
        this.fieldErrors = Collections.emptyMap();
    }

    public ValidationException(String message, Map<String, String> fieldErrors) {
        super(message);
        this.fieldErrors = (fieldErrors != null)
                ? Collections.unmodifiableMap(new LinkedHashMap<>(fieldErrors))
                : Collections.emptyMap();
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }

    public boolean hasFieldErrors() {
        return !fieldErrors.isEmpty();
    }
}