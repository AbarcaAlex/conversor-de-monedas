package com.conversor.principal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Scanner;

import com.conversor.operacion.Conversion;
import com.google.gson.Gson;

public class App {
    public static void main(String[] args) throws Exception {
        // variables y constructores
        String apiKey = "Bearer 14ddeb33aae57ccc95b6c103";
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        // Menu
        while (true) {
            System.out.println("""
                    ***************************************
                    Bienvenido/a al conversor de monedas!
                    
                    Elija una opcion:

                    1) Realizar una conversion
                    2) Salir
                    
                    ***************************************
                    """);
            int opcion = scanner.nextInt();
            if (opcion==2) {
                break;
            }else if (opcion==1) {

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

                // Recibir la "response" .json y pasarle los datos al Record "Conversion"
                Conversion conversion = gson.fromJson(response.body(), Conversion.class);

                // Imprimir los resultados
                System.out.printf("""
                ********************************************************
                Resultado de la conversion:
                    
                %.2f [%S] equivale a ==> %.2f [%S]
                    
                ********************************************************
                """,amount,conversion.base_code(),conversion.conversion_result(),conversion.target_code());

            }else{
                System.out.println("Esa opcion no es valida!");
            }
            

        }
        scanner.close();
    }
}
