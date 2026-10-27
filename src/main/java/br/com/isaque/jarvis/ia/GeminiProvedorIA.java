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
import java.util.ArrayList;
import java.util.List;

public class GeminiProvedorIA implements ProvedorIA {

    private static final String MODELO = "gemini-3.5-flash-lite";

    private static final String URL_BASE =
            "https://generativelanguage.googleapis.com/v1beta/models/";

    private final HttpClient cliente;
    private final ObjectMapper json;
    private final String chaveApi;

    // Histórico mantido enquanto esta instância estiver ativa.
    private final List<MensagemConversa> historico = new ArrayList<>();

    private record MensagemConversa(String papel, String texto) {
    }

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
            throw new FalhaProvedorIA(
                    "A variável GEMINI_API_KEY não foi configurada.",
                    false
            );
        }

        try {
            ObjectNode corpo = json.createObjectNode();

            // 1. Define a personalidade do Jarvis.
            ObjectNode instrucaoSistema =
                    corpo.putObject("systemInstruction");

            ArrayNode partesInstrucao =
                    instrucaoSistema.putArray("parts");

            partesInstrucao.addObject().put(
                    "text",
                    PersonalidadeJarvis.obterInstrucoes()
            );

            // 2. Envia o histórico da conversa.
            ArrayNode contents = corpo.putArray("contents");

            for (MensagemConversa mensagem : historico) {
                adicionarMensagem(
                        contents,
                        mensagem.papel(),
                        mensagem.texto()
                );
            }

            // 3. Adiciona a pergunta atual.
            adicionarMensagem(contents, "user", pergunta);

            HttpRequest requisicao = HttpRequest.newBuilder()
                    .uri(URI.create(
                            URL_BASE + MODELO + ":generateContent"
                    ))
                    .timeout(Duration.ofSeconds(60))
                    .header("x-goog-api-key", chaveApi)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            json.writeValueAsString(corpo)
                    ))
                    .build();

            HttpResponse<String> resposta = cliente.send(
                    requisicao,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (resposta.statusCode() < 200
                    || resposta.statusCode() >= 300) {

                int codigo = resposta.statusCode();

                boolean permiteFallback =
                        codigo == 429 || codigo >= 500;

                throw new FalhaProvedorIA(
                        "Erro na API Gemini. Código HTTP: "
                                + codigo
                                + " | Detalhes: "
                                + resposta.body(),
                        permiteFallback
                );
            }

            JsonNode raiz = json.readTree(resposta.body());

            JsonNode texto = raiz.at(
                    "/candidates/0/content/parts/0/text"
            );

            if (texto.isMissingNode()
                    || texto.isNull()
                    || !texto.isTextual()) {

                return "O Gemini não retornou uma resposta em texto.";
            }

            String respostaJarvis = texto.asText();

            // 4. Salva a interação após receber uma resposta válida.
            historico.add(
                    new MensagemConversa("user", pergunta)
            );

            historico.add(
                    new MensagemConversa("model", respostaJarvis)
            );

            return respostaJarvis;

        } catch (IOException e) {
            throw new FalhaProvedorIA(
                    "Erro ao comunicar com o Gemini.", e
            );

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();

            throw new FalhaProvedorIA(
                    "A comunicação com o Gemini foi interrompida.", e
            );
        }
    }

    private void adicionarMensagem(
            ArrayNode contents,
            String papel,
            String texto
    ) {
        ObjectNode content = contents.addObject();
        content.put("role", papel);

        ArrayNode partes = content.putArray("parts");
        partes.addObject().put("text", texto);
    }
}
