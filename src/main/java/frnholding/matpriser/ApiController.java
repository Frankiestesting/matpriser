package frnholding.matpriser;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

public class ApiController {
    private ApiClient apiClient;
    Properties properties = new Properties();
    private String apiKey;
    private String baseUrl;
    private int serverPort;

    public ApiController() {

        try (InputStream input = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                System.out.println("Kunne ikke finne application.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        apiKey = properties.getProperty("api.secret.key");
        baseUrl = properties.getProperty("api.base.url");

        serverPort = Integer.parseInt(properties.getProperty("server.port"));

        apiClient = new ApiClient(apiKey, baseUrl);
    }

    public JSONObject getData(String endpoint) {
        try {
            return apiClient.getData(endpoint);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static void main(String[] args) {
        ApiController myApiController = new ApiController();
        ApiClient apiClient = new ApiClient(myApiController.apiKey, myApiController.baseUrl);

        ApiServer apiServer = new ApiServer(apiClient);
        apiServer.start(myApiController.serverPort); // Serveren vil kjøre på port 7000

        System.out.println("API-server kjører på http://localhost:7001");
    }

    /*
    public static void main(String[] args) {
        ApiController apiController = new ApiController();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Velg et endepunkt å hente data fra:");
        System.out.println("1: Butikker");
        System.out.println("2: En butikk (ID)");
        System.out.println("3: Produkter");
        System.out.println("4: Et produkt (ID)");
        System.out.println("5: Et produkt (ean)");
        System.out.println("6: Et produkt (url)");
        System.out.println("7: Produktsammenligning (url)");
        System.out.print("Tast inn tallet for valget ditt (1-7): ");

        int choice = scanner.nextInt();
        String endpoint = "";

        switch (choice) {
            case 1:
                endpoint = apiController.properties.getProperty("endpoint.physical_stores"); // Erstatt med faktisk endepunkt
                break;
            case 2:
                System.out.print("Skriv inn ID-en til butikken: ");
                int store_id = scanner.nextInt();
                endpoint = apiController.properties.getProperty("endpoint.physical_stores_by_id").replace("{id}", String.valueOf(store_id));
                break;
            case 3:
                endpoint = apiController.properties.getProperty("endpoint.products");
                break;
            case 4:
                System.out.print("Skriv inn ID-en til produktet: ");
                int id = scanner.nextInt();
                endpoint = apiController.properties.getProperty("endpoint.product_by_id").replace("{id}", String.valueOf(id));
                break;
            case 5:
                System.out.print("Skriv inn ean-koden til produktet: ");
                int ean = scanner.nextInt();
                endpoint = apiController.properties.getProperty("endpoint.product_by_ean").replace("{ean}", String.valueOf(ean));
                break;
            case 6:
                System.out.print("Skriv inn url til produktet: (VIRKER IKKE ENDA) ");
                int product_url = scanner.nextInt();
                endpoint = apiController.properties.getProperty("endpoint.product_by_url").replace("single", String.valueOf(product_url));
                break;
            case 7:
                System.out.print("Skriv inn url til produktet: (VIRKER IKKE ENDA)");
                int compare_url = scanner.nextInt();
                endpoint = apiController.properties.getProperty("endpoint.product_by_url_compare").replace("single", String.valueOf(compare_url));
                break;

            default:
                System.out.println("Ugyldig valg. Avslutter programmet.");
                scanner.close();
                return; // Avslutter programmet hvis valget er invalid
        }


        // Hent data fra et spesifisert endepunkt
        JSONObject json = apiController.getData(endpoint);
        System.out.println("Respons fra API: " + json.toString(2));
    }

     */
}
