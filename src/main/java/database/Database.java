package database;

import com.sun.istack.internal.Nullable;
import model.Note;
import model.User;
import utils.Utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Database {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ikt?characterEncoding=utf8&useConfigs=maxPerformance";

    private static final String USER = "ikt";
    private static final String PASS = "Qwerqwer1234";

    private static Database instance;
    private Connection conn = null;
    private Statement stmt = null;

    private Database() {
        start();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private void start() {

        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public User login(String email, String password) {
        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE email = '%s' AND password = '%s'";
            String sql = String.format(query, email, password);
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                User user = new User(rs);
                return updateUser(user.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void createNote(int userId, String title, String message) {
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "INSERT INTO notes (user_id, title, note) VALUES ('%s', '%s', '%s')";
            String sql = String.format(query, userId, title, message);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateNote(int noteId, String title, String message) {
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "UPDATE notes SET title = '%s', note = '%s' WHERE id = %s";
            String sql = String.format(query, title, message, noteId);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteNote(int noteId) {
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "DELETE FROM notes WHERE id = %s";
            String sql = String.format(query, noteId);
            stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Nullable
    public User updateUser(int id) {
        try {
            String date = Utils.databaseDate(new Date());
            String token = UUID.randomUUID().toString();
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "UPDATE users SET last_login = '%s', token = '%s' where id = '%s'";
            String sql = String.format(query, date, token, id);
            stmt.executeUpdate(sql);
            return getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public User createUser(String email, String password) {
        try {
            String date = Utils.databaseDate(new Date());
            String token = UUID.randomUUID().toString();
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "INSERT INTO users (email, password, last_login, token) VALUES ('%s', '%s', '%s', '%s')";
            String sql = String.format(query, email, password, date, token);
            stmt.executeUpdate(sql);
            return getUser(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public User getUser(int id) {
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE id = '%s'";
            String sql = String.format(query, id);
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                return new User(resultSet);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    public User getUser(String token) {
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE token = '%s'";
            String sql = String.format(query, token);
            ResultSet resultSet = stmt.executeQuery(sql);
            if (resultSet.next()) {
                return new User(resultSet);
            }
            resultSet.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Note> getNotes(String token) {
        List<Note> notes = new ArrayList<>();
        try {
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "SELECT notes.id, notes.user_id, notes.title, notes.note FROM notes JOIN users ON notes.user_id = users.id WHERE token = '%s'";
            String sql = String.format(query, token);
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Note note = new Note(rs);
                notes.add(note);
            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return notes;
    }


    public void close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}
