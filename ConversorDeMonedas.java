import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class ConversorDeMonedas {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la moneda de origen:");
        String monedaOrigen = scanner.nextLine().toUpperCase();

        System.out.println("Ingrese la moneda de destino:");
        String monedaDestino = scanner.nextLine().toUpperCase();

        System.out.println("Ingrese la cantidad a convertir:");
        double cantidad = scanner.nextDouble();

        scanner.close();

        try {
            double cantidadConvertida = convertirMoneda(monedaOrigen, monedaDestino, cantidad);
            System.out.println(cantidad + " " + monedaOrigen + " = " + cantidadConvertida + " " + monedaDestino);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static double convertirMoneda(String monedaOrigen, String monedaDestino, double cantidad)
            throws IOException, InterruptedException {
        String API_URL = "https://v6.exchangerate-api.com/v6/3539f57ab275341fed19229f/latest/";
        String url = API_URL + monedaOrigen.toUpperCase();
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder().uri(URI.create(url)).build();

        HttpResponse<String> respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        RespuestaTipoCambio respuestaTipoCambio = gson.fromJson(respuesta.body(), RespuestaTipoCambio.class);

        double tasaCambio = respuestaTipoCambio.conversion_rates.get(monedaDestino.toUpperCase());
        return cantidad * tasaCambio;
    }

    private static class RespuestaTipoCambio {
        ConversionRates conversion_rates;
    }

    private static class ConversionRates {
        double USD;
        double EUR;

        public double get(String upperCase) {
            return 0;
        }
    }
}
