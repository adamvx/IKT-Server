package handlers;

import com.sun.net.httpserver.HttpExchange;
import model.ApiState;
import model.Response;

import java.io.IOException;

public class CheckHandler extends BaseHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        Response response = new Response(ApiState.OK);
        handleResult(exchange, response.toJson());
    }

}
