import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import com.google.gson.Gson;

public class App {
    public static void main(String[] args) throws Exception {
        // variables y constructores
        String apiKey = "Bearer ";
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        // Menu
        while (true) {
            System.out.println("""
                    ***************************************
                    1) Realizar una conversion de moneda
                    2) Salir
                    ***************************************
                    """);
            int salida = scanner.nextInt();
            if (salida==2) {
                break;
            }else if (salida==1) {

                System.out.print("Escriba el codigo de su moneda en uso actual: ");
                String baseCurrency = scanner.next().toUpperCase();
                System.out.print("Escriba la cantidad a convertir: ");
                Double amount = scanner.nextDouble();
                System.out.print("Escriba el codigo de la moneda a convertir: ");
                String targetCurrency = scanner.next().toUpperCase();

                // HttpRequest
                HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/pair/"+baseCurrency+"/"+targetCurrency+"/"+amount))
                .header("Authorization",apiKey)
                .build();

                // HttpClient
                HttpClient client = HttpClient.newHttpClient();

                // HttpResponse
                HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

                System.out.println(response.body());

            }else{
                System.out.println("Esa opcion no es valida!");
            }
            

        }
        scanner.close();
    }
}
