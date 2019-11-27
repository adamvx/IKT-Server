package model;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Model of note object
 */
public class Note extends BaseObject {
    private int id;
    private int userId;
    private String heading;
    private String message;
    private String token;

    public Note(ResultSet rs) throws SQLException {
        id = rs.getInt("id");
        userId = rs.getInt("user_id");
        heading = rs.getString("title");
        message = rs.getString("note");
    }

    public int getId() {
        return id;
    }

    public String getHeading() {
        return heading;
    }

    public String getMessage() {
        return message;
    }

    public int getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
