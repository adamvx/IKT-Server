package handlers;

import com.sun.net.httpserver.HttpExchange;
import model.ApiState;
import model.Response;
import model.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * Handler for user registration.
 */
public class RegisterHandle extends BaseHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        User login = gson.fromJson(reader, User.class);
        String email = login.getEmail();
        String password = login.getPassword();

        User user = database.createUser(email, password);

        if (user != null) {
            Response response = new Response(user.getToken());
            handleResult(exchange, response.toJson());
        } else {
            Response response = new Response(ApiState.ERROR_REGISTER);
            handleResult(exchange, response.toJson());
        }
    }
}
