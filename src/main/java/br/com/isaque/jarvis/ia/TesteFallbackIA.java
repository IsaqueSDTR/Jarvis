
package br.com.isaque.jarvis.ia;

public class TesteFallbackIA {

    public static void main(String[] args) {

        ProvedorIA geminiSimulado = pergunta -> {
            throw new FalhaProvedorIA(
                    "Falha simulada no Gemini."
            );
        };

        ProvedorIA ollamaReal = new OllamaProvedorIA();

        ProvedorIA ia = new FallbackProvedorIA(
                geminiSimulado,
                ollamaReal
        );

        String resposta = ia.perguntar(
                "Responda em português e de forma breve: " +
                        "apresente-se como o provedor alternativo do Jarvis."
        );

        System.out.println("Resposta recebida:");
        System.out.println(resposta);
    }
}
