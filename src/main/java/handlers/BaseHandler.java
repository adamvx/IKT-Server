package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import database.Database;

import java.io.IOException;
import java.io.OutputStream;

public abstract class BaseHandler implements HttpHandler {

    private static final int SUCCESS = 200;

    Gson gson = new Gson();
    Database database = Database.getInstance();

    void handleResult(HttpExchange exchange, String response) throws IOException {
        if (response != null) {
            exchange.getResponseHeaders().set("Content-Type", "application/json");
            exchange.sendResponseHeaders(BaseHandler.SUCCESS, response.getBytes().length);
            OutputStream output = exchange.getResponseBody();
            output.write(response.getBytes());
            output.flush();
        }
        exchange.close();
    }


}
