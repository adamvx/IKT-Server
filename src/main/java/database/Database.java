package database;

import com.sun.istack.internal.Nullable;
import model.Note;
import model.User;
import utils.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Database class is responsible for connecting and handling database requests.
 *
 * @author Adam Vician, Milan Ponist, Matej Vanek
 */
public class Database {

    /**
     * JDBC Driver constant
     */
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    /**
     * Database url
     */
    private static final String DB_URL = "jdbc:mysql://localhost:3306/ikt?characterEncoding=utf8&useConfigs=maxPerformance";

    /**
     * Database username
     */
    private static final String USER = "ikt";
    /**
     * Database password
     */
    private static final String PASS = "Qwerqwer1234";

    /**
     * Database singleton instance
     */
    private static Database instance;

    /**
     * Database connection reference
     */
    private Connection conn = null;

    /**
     * Database global statement
     */
    private Statement stmt = null;

    /**
     * Private constructor. When new Database instance is created it will also execute start method that will create
     * connection to database.
     * @see Database#start()
     */
    private Database() {
        start();
    }


    /**
     * Getter for singleton instance of database. If no instance is present new is created.
     * @return Database instance
     */
    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     * Start will make connection to database with provided URL and login credentials
     */
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

    /**
     * Tries to login user.
     * @param email
     * @param password
     * @return returns updated user from databased or null if user with provided credentials doesn't exist
     * @see Database#updateUser(int)
     * @see Database#getUser(int)
     */
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

    /**
     * Will create new note in database for user
     * @param userId user id for user who is creating the note
     * @param title title of note
     * @param message message of note
     */
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

    /**
     * Will update note in database
     * @param noteId note id which will be edited
     * @param title updated title
     * @param message updated message
     */
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

    /**
     * Will delete note from database by id
     * @param noteId note id which will be deleted
     */
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

    /**
     * Will update user last login date
     * @param id id of user
     * @return returns user by id from database of null if user is not found.
     * @see Database#getUser(int)
     */
    @Nullable
    public User updateUser(int id) {
        try {
            String date = Utils.databaseDate(new Date());
            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String query = "UPDATE users SET last_login = '%s' where id = '%s'";
            String sql = String.format(query, date, id);
            stmt.executeUpdate(sql);
            return getUser(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Will create new user in database with token
     * @param email User email
     * @param password User password
     * @return returns user by token from database of null if user is not found.
     * @see Database#getUser(String)
     */
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

    /**
     * Will return user from database by provided id
     * @param id id of user
     * @return returns user by id from database of null if user is not found.
     */
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

    /**
     * Will return user from database by provided token
     * @param token token of user
     * @return returns user by token from database of null if user is not found.
     */
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

    /**
     * Returns list of notes for provided user by token
     * @param token user token
     * @return returns not null list of notes which belong to user.
     */
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

}
