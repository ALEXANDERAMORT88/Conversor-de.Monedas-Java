import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaMoneda {

    public Moneda busquedaDeMoneda(String desde, String hasta) {
        URI direccion = URI.create("https://v6.exchangerate-api.com/v6/c5b0efe2cd5f5e3e5f653f9f/" + desde + "/" + hasta);

        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder().uri(direccion).build();

        try {
            HttpResponse<String> response = cliente
                    .send(solicitud, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(responseBody, JsonObject.class);

            if (jsonObject.has("result") && jsonObject.get("result").getAsString().equals("error") ) {
                if (jsonObject.has("error-type")) {
                    String errorType = jsonObject.get("error-type").getAsString();
                    switch (errorType) {
                        case "unsupported-code" ->
                                throw new RuntimeException("Error, códgo de modena no son soportados");
                        case "invalid_key" ->
                                throw new RuntimeException("Error, la calve de la API proporcionada no es valida");
                        case "malformed-request" ->
                                throw new RuntimeException("Error, la solicitud a la API esta mal formada");
                        case "inputMismatchException" ->
                                throw new RuntimeException(("Error, la cantidad ingreasda no es un numero valido"));
                        default ->
                                throw  new RuntimeException("Error, al obtener la tasa de cambio: el servicio reporto in error" + errorType);
                    }
                } else {
                    throw  new RuntimeException("Error desconocido al obtener la tasa de cambio desde el servicio");
                }
            }
            return gson.fromJson(responseBody, Moneda.class);
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener la tasa de cambio -> Clave de moneda no encontrada en base de datos o malformada");
        }
    }
}