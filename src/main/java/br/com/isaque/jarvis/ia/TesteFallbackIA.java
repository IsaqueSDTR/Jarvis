
package br.com.isaque.jarvis.ia;

public class TesteFallbackIA {

    public static void main(String[] args) {

        ProvedorIA principal = pergunta -> {
            throw new FalhaProvedorIA(
                    "Falha simulada no Gemini."
            );
        };

        ProvedorIA alternativa = pergunta -> {
            return "Resposta de teste do Ollama: " + pergunta;
        };

        ProvedorIA ia = new FallbackProvedorIA(
                principal,
                alternativa
        );

        String resposta = ia.perguntar(
                "O que é Java?"
        );

        System.out.println(resposta);
    }
}
