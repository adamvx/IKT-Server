package handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import model.ApiState;
import model.Note;
import model.Response;
import model.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class CreateHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Note note = new Gson().fromJson(reader, Note.class);

        User user = database.getUser(note.getToken());

        if (user != null) {
            database.createNote(user.getId(), note.getHeading(), note.getMessage());
            handleResult(exchange, new Gson().toJson(database.getNotes(user.getToken())));
        } else {
            Response response = new Response(ApiState.ERROR_CRETE);
            handleResult(exchange, response.toJson());
        }
    }
}
