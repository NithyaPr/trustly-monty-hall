package se.nithya.trustlymontyhall.exception;

public class ApiError {

    private String code;
    private String reason;
    private String message;

    public ApiError(String code, String reason, String message) {
        this.code = code;
        this.reason = reason;
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
