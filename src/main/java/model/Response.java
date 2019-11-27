package model;

/**
 * Model of server response object
 */
public class Response extends BaseObject {

    private Integer code;
    private String message;
    private String token;

    public Response(String token) {
        this.token = token;
    }

    public Response(ApiState state) {
        this.code = state.getCode();
        this.message = state.getMessage();
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
