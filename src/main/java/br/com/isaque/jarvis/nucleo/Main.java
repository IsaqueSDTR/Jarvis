package br.com.isaque.jarvis.nucleo;

import br.com.isaque.jarvis.entrada.EntradaDeComando;
import br.com.isaque.jarvis.entrada.EntradaTeclado;
import br.com.isaque.jarvis.modulos.ModuloAmbienteEstudos;
import br.com.isaque.jarvis.modulos.ModuloEco;
import br.com.isaque.jarvis.saida.SaidaConsole;
import br.com.isaque.jarvis.saida.SaidaDeResposta;
import br.com.isaque.jarvis.ia.FallbackProvedorIA;
import br.com.isaque.jarvis.ia.GeminiProvedorIA;
import br.com.isaque.jarvis.ia.OllamaProvedorIA;
import br.com.isaque.jarvis.ia.ProvedorIA;
import br.com.isaque.jarvis.ia.FalhaProvedorIA;
import br.com.isaque.jarvis.memoria.MemoriaService;
import br.com.isaque.jarvis.modulos.ModuloMemoria;

public class Main {
    public static void main(String[] args) {
        EntradaDeComando entrada = new EntradaTeclado();
        SaidaDeResposta saida = new SaidaConsole();
        Configuracao config = new Configuracao("config.properties");

        ProvedorIA gemini = new GeminiProvedorIA();
        ProvedorIA ollama = new OllamaProvedorIA();

        ProvedorIA ia = new FallbackProvedorIA(gemini, ollama);
        Roteador roteador = new Roteador(ia);

        roteador.registrar("estudos", new ModuloAmbienteEstudos(config));
        roteador.registrar("eco", new ModuloEco());
        roteador.registrar("memoria", new ModuloMemoria(new MemoriaService()));

        while (true) {
            String comando = entrada.ouvir();

            if (comando.trim().equalsIgnoreCase("sair")) {
                break;
            }

            if (comando.isBlank()) {
                continue;
            }

            String resposta;
            try {
                resposta = roteador.rotear(comando);
            } catch (FalhaProvedorIA e) {
                resposta = "Não consegui falar com a IA agora: " + e.getMessage();
            }

            saida.falar(resposta);
        }
    }
}