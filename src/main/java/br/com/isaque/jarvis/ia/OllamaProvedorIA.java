
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

public class OllamaProvedorIA implements ProvedorIA {

    private static final String MODELO = "llama3.2:3b";
    private static final String URL = "http://localhost:11434/api/chat";

    private final HttpClient cliente;
    private final ObjectMapper json;

    public OllamaProvedorIA() {
        this.cliente = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();

        this.json = new ObjectMapper();
    }

    @Override
    public String perguntar(String pergunta) {
        try {
            ObjectNode corpo = json.createObjectNode();
            corpo.put("model", MODELO);
            corpo.put("stream", false);

            ArrayNode mensagens = corpo.putArray("messages");

            ObjectNode mensagemSistema = mensagens.addObject();
            mensagemSistema.put("role", "system");
            mensagemSistema.put(
                    "content",
                    PersonalidadeJarvis.obterInstrucoes()
            );

            ObjectNode mensagem = mensagens.addObject();

            mensagem.put("role", "user");
            mensagem.put("content", pergunta);

            HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .timeout(Duration.ofMinutes(3))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            json.writeValueAsString(corpo)))
                    .build();

            HttpResponse<String> resposta = cliente.send(
                    requisicao,
                    HttpResponse.BodyHandlers.ofString());

            if (resposta.statusCode() < 200
                    || resposta.statusCode() >= 300) {
                throw new FalhaProvedorIA(
                        "Erro no Ollama. Código HTTP: "
                                + resposta.statusCode()
                );
            }

            JsonNode raiz = json.readTree(resposta.body());
            JsonNode conteudo = raiz.path("message").path("content");

            if (!conteudo.isTextual()) {
                throw new FalhaProvedorIA(
                        "O Ollama não retornou uma resposta em texto."
                );
            }

            return conteudo.asText();

        } catch (IOException e) {
            throw new FalhaProvedorIA(
                    "Não foi possível conectar ao Ollama. "
                            + "Verifique se ele está em execução.",
                    e
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new FalhaProvedorIA(
                    "A comunicação com o Ollama foi interrompida.",
                    e
            );
        }
    }
}
