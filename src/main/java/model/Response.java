package model;

public class Response extends BaseObject {

    private Integer code;
    private String message;
    private String token;

    public Response(String token) {
        this.token = token;
    }

    public Response(ApiState error) {
        this.code = error.getCode();
        this.message = error.getMessage();
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }


}
