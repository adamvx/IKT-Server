import com.sun.net.httpserver.HttpServer;
import handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;


/**
 * Server singleton class that will manage all incoming network traffic
 *
 * @author Adam Vician, Milan Ponist, Matej Vanek
 */
public class Server {

    /**
     * Port on witch server will be running
     */
    private static final int PORT = 8000;

    /**
     * Static singleton instance of server
     */
    private static Server instance;

    /**
     * Private constructor
     */
    private Server() {

    }

    /**
     * Getter for singleton instance of server. If no instance is present new is created.
     * @return Server instance
     */
    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    /**
     * Entry point of java application.
     * @param args input arguments. Ignored in this case.
     */
    public static void main(String[] args) {
        try {
            Server.getInstance().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that will start HttpServer on provided port with different handlers.
     * @throws IOException Exception is thrown if there was problem with starting server. For example another process
     * is using this port.
     */
    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        attachHandlers(server);
        server.setExecutor(null);
        server.start();
    }

    /**
     * Server handlers are attached to HttpServer instance.
     * @param server handlers will be attached on provided HttpServer instance
     */
    private void attachHandlers(HttpServer server) {
        server.createContext("/api/check", new CheckHandler());
        server.createContext("/api/login", new LoginHandler());
        server.createContext("/api/register", new RegisterHandle());
        server.createContext("/api/notes", new NoteHandler());
        server.createContext("/api/create", new CreateHandler());
        server.createContext("/api/delete", new DeleteHandler());
        server.createContext("/api/edit", new EditHandler());
    }
}
