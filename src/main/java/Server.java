import com.sun.net.httpserver.HttpServer;
import handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Server {
    private static final int PORT = 8000;
    private static Server instance;


    private Server() {

    }

    public static Server getInstance() {
        if (instance == null) {
            instance = new Server();
        }
        return instance;
    }

    public static void main(String[] args) {
        try {
            Server.getInstance().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        attachHandlers(server);
        server.setExecutor(null);
        server.start();
    }

    private void attachHandlers(HttpServer server) {
        server.createContext("/api/login", new LoginHandler());
        server.createContext("/api/register", new RegisterHandle());
        server.createContext("/api/notes", new NoteHandler());
        server.createContext("/api/create", new CreateHandler());
        server.createContext("/api/delete", new DeleteHandler());
    }
}
