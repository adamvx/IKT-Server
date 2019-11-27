package handlers;

import com.sun.net.httpserver.HttpExchange;
import model.Note;
import model.Response;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Handler for retrieving all notes for one user
 */
public class NoteHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        String token = gson.fromJson(reader, Response.class).getToken();

        List<Note> notes = database.getNotes(token);
        handleResult(exchange, gson.toJson(notes));
    }
}
