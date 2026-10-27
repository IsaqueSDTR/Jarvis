package br.com.isaque.jarvis.ia;

public class TesteGemini {

    public static void main(String[] args) {
        ProvedorIA gemini = new GeminiProvedorIA();

        String resposta = gemini.perguntar(
                "Responda em português: diga apenas 'Jarvis online'."
        );

        System.out.println("Gemini: " + resposta);
    }
}
