package br.com.isaque.jarvis.ia;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class GeminiProvedorIA implements ProvedorIA {

    private static final String MODELO = "gemini-3.5-flash-lite";
    private static final String URL_BASE =
            "https://generativelanguage.googleapis.com/v1beta/models/";

    private final HttpClient cliente;
    private final ObjectMapper json;
    private final String chaveApi;

    public GeminiProvedorIA() {
        this.chaveApi = System.getenv("GEMINI_API_KEY");
        this.cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(15))
                .build();
        this.json = new ObjectMapper();
    }

    @Override
    public String perguntar(String pergunta) {
        if (chaveApi == null || chaveApi.isBlank()) {
            return "Erro: a variável GEMINI_API_KEY não foi configurada.";
        }

        try {
            ObjectNode corpo = json.createObjectNode();
            ArrayNode contents = corpo.putArray("contents");
            ObjectNode content = contents.addObject();
            ArrayNode parts = content.putArray("parts");
            parts.addObject().put("text", pergunta);

            HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(URI.create(URL_BASE + MODELO + ":generateContent"))
                    .timeout(Duration.ofSeconds(60))
                    .header("x-goog-api-key", chaveApi)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            json.writeValueAsString(corpo)))
                    .build();

            HttpResponse<String> resposta = cliente.send(
                    requisicao,
                    HttpResponse.BodyHandlers.ofString());

            if (resposta.statusCode() < 200
                    || resposta.statusCode() >= 300) {
                return "Erro na API Gemini. Código HTTP: "
                        + resposta.statusCode()
                        + " | Detalhes: "
                        + resposta.body();
            }

            JsonNode raiz = json.readTree(resposta.body());
            JsonNode texto = raiz.at(
                    "/candidates/0/content/parts/0/text");

            if (texto.isMissingNode() || texto.isNull()) {
                return "O Gemini não retornou uma resposta em texto.";
            }

            return texto.asText();

        } catch (IOException e) {
            return "Erro ao comunicar com o Gemini: "
                    + e.getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "A comunicação com o Gemini foi interrompida.";
        }
    }
}
