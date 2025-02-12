package frnholding.matpriser;

import io.javalin.Javalin;
import org.json.JSONObject;

public class ApiServer {
    private ApiClient apiClient;

    public ApiServer(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void start(int port) {
        Javalin app = Javalin.create().start(port);

        // Endepunkt for å hente alle produkter
        app.get("/api/products", ctx -> {
            String response = apiClient.getData("/products").toString(2);
            ctx.result(response).contentType("application/json");
        });

        // Endepunkt for å hente spesifikt produkt
        app.get("/api/products/id/{id}", ctx -> {
            String id = ctx.pathParam("id");
            String response = apiClient.getData("/products/id/" + id).toString(2);
            ctx.result(response).contentType("application/json");
        });

        // Legg til flere endepunkter etter behov
    }
}
