package com.conversor.principal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.InputMismatchException;
import java.util.Scanner;
import com.conversor.datos.Conversion;
import com.conversor.datos.ExchangeRateApi;
import com.conversor.excepciones.ErrorFromApiException;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class App {
    public static void main(String[] args) throws Exception {
        // variables y constructores
        String apiKey = "Bearer ${ExchangeRate_API_KEY}";
        Scanner scanner = new Scanner(System.in);
        Gson gson = new Gson();

        // Menu
        while (true) {
            try {
                
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
                    System.out.println("Gracias por usar!");
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

                    // Recibir la "response" .json y pasarle los datos a un objeto del Record "ExchangeRateApi"
                    ExchangeRateApi conversionFromExchangeRateApi = gson.fromJson(response.body(), ExchangeRateApi.class);

                    // Pasar los datos del objeto Record "conversionExchangeRateApi" a un nuevo objeto de la clase "Conversion"
                    Conversion conversion = new Conversion(conversionFromExchangeRateApi);

                    // Imprimir los resultados
                    System.out.printf("""
                    ********************************************************
                    Resultado de la conversion:
                        
                    %.2f [%S] equivale a ==> %.2f [%S]
                        
                    ********************************************************
                    """,amount,conversion.getBaseCode(),conversion.getConversionResult(),conversion.getTargetCode());

                    }else{
                        System.out.println("Esa opcion no es valida!");
                    }

                } catch (ErrorFromApiException e){
                    System.out.println(e.getMessage());
                } catch (JsonSyntaxException e){
                    System.out.println("Los codigos de moneda introducidos no son validos!");
                } catch (InputMismatchException e){
                    System.out.println("Esa opcion no es valida!");
                    scanner.next();
                }

        }
        scanner.close();
    }
}
