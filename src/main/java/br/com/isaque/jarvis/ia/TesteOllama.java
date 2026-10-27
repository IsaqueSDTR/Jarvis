
package br.com.isaque.jarvis.ia;

public class TesteOllama {

    public static void main(String[] args) {
        ProvedorIA ollama = new OllamaProvedorIA();

        String resposta = ollama.perguntar(
                "Responda apenas com uma saudação curta em português."
        );

        System.out.println("Ollama: " + resposta);
    }
}
