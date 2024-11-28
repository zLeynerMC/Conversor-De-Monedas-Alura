import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiHandler {
    private final String baseUrl;

    public ApiHandler(String baseUrl){
        this.baseUrl = baseUrl;
    }
    public String BuscarTasaDeCambio(String moneda){
        try {
             HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create(baseUrl + moneda))
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    return response.body();
        } catch (Exception e){
            System.out.println("Error En La API: " + e.getMessage());
            return null;
        }
    }
}
