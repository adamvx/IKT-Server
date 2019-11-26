package model;

public enum ApiState {

    OK(0, "OK"),
    ERROR_LOGIN(1, "Login unsuccessful. Try again!"),
    ERROR_REGISTER(2, "Registration unsuccessful. Try again!"),
    ERROR_DELETE(3, "Deletion unsuccessful. Try again!"),
    ERROR_CRETE(4, "Creation unsuccessful. Try again!"),
    ERROR_UNKNOWN(5, "Awww, unknown error has occurred");

    int code;
    String message;

    ApiState(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
