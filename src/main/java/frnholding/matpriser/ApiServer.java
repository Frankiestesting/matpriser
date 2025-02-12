package frnholding.matpriser;

import io.javalin.Javalin;

import java.util.Properties;

public class ApiServer {
    private final ApiClient apiClient;
    Properties properties = new Properties();

    public ApiServer(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void setProperties(Properties props) { properties = props;}

    public void start(int port) {
        Javalin app = Javalin.create().start(port);

        // Endepunkt for å hente alle produkter
        app.get("/api/products", ctx -> {
            String response = apiClient.getData( properties.getProperty("endpoint.products")).toString(2);
            ctx.result(response).contentType("application/json");
        });

        // Endepunkt for å hente spesifikt produkt
        app.get("/api/product/id/{id}", ctx -> {
            String id = ctx.pathParam("id");
            String response = String.valueOf(apiClient.getData(properties.getProperty("endpoint.product_by_id").replace("{id}", id)));
            ctx.result(response).contentType("application/json");
        });

        // Endepunkt for å hente spesifikt produkt
        app.get("/api/product/ean/{ean}", ctx -> {
            String id = ctx.pathParam("ean");
            String response = String.valueOf(apiClient.getData(properties.getProperty("endpoint.product_by_ean").replace("{ean}", id)));
            ctx.result(response).contentType("application/json");
        });

        // Endepunkt for å hente spesifikt produkt
        app.get("/api/physical_store", ctx -> {
            String response = apiClient.getData(properties.getProperty("endpoint.physical_stores")).toString(2);
            ctx.result(response).contentType("application/json");
        });

        // Endepunkt for å hente spesifikt produkt
        app.get("/api/physical_store/id/{id}", ctx -> {
            String id = ctx.pathParam("id");
            String response = String.valueOf(apiClient.getData( properties.getProperty("endpoint.product_by_id").replace("{id}", id)));
            ctx.result(response).contentType("application/json");
        });


        // Legg til flere endepunkter etter behov
    }
}
