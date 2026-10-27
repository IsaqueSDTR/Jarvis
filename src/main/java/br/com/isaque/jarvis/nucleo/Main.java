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

        while (true) {
            String comando = entrada.ouvir();
            if (comando.trim().equalsIgnoreCase("sair")) {
                break;
            }
            String resposta = roteador.rotear(comando);
            if (resposta == null) {
                saida.falar("Você disse: " + comando);
            } else {
                saida.falar(resposta);
            }
        }
    }
}