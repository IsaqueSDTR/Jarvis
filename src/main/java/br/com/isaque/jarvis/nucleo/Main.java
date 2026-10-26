package br.com.isaque.jarvis.nucleo;

import br.com.isaque.jarvis.entrada.EntradaDeComando;
import br.com.isaque.jarvis.entrada.EntradaTeclado;
import br.com.isaque.jarvis.saida.SaidaConsole;
import br.com.isaque.jarvis.saida.SaidaDeResposta;

public class Main {
    public static void main(String[] args) {
        EntradaDeComando entrada = new EntradaTeclado();
        SaidaDeResposta saida = new SaidaConsole();

        while (true) {
            String comando = entrada.ouvir();
            if (comando.trim().equalsIgnoreCase("sair")) {
                break;
            }
            saida.falar("Você disse: " + comando);
        }
    }
}