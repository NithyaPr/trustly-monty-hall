package se.nithya.trustlymontyhall.exception;

import org.springframework.http.HttpStatus;

public class MontyHallException  extends RuntimeException {
    private final HttpStatus status;
    private final String code;
    private final String reason;

    public MontyHallException(HttpStatus status, String code, String reason, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
        this.code = code;
        this.reason = reason;
    }

    public MontyHallException(HttpStatus status, String code, String reason, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.reason = reason;
    }

    public MontyHallException(HttpStatus status, String reason, String message) {
        this(status, status.name(), reason, message);
    }

    public MontyHallException(HttpStatus status, String message) {
        this(status, status.name(), status.getReasonPhrase(), message);
    }

    public MontyHallException(HttpStatus status, String message, Throwable cause) {
        this(status, status.name(), status.getReasonPhrase(), message, cause);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }
}
