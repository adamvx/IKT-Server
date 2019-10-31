package handlers;

import com.sun.net.httpserver.HttpExchange;
import model.ApiError;
import model.Response;
import model.User;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class LoginHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        User login = gson.fromJson(reader, User.class);
        String email = login.getEmail();
        String password = login.getPassword();

        User user = database.login(email, password);

        if (user != null) {
            Response response = new Response(user.getToken());
            handleResult(exchange, response.toJson());
        } else {
            Response response = new Response(ApiError.ERROR_LOGIN);
            handleResult(exchange, response.toJson());
        }
    }

}
