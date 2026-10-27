package br.com.isaque.jarvis.nucleo;

import br.com.isaque.jarvis.entrada.EntradaDeComando;
import br.com.isaque.jarvis.entrada.EntradaTeclado;
import br.com.isaque.jarvis.modulos.Modulo;
import br.com.isaque.jarvis.modulos.ModuloAmbienteEstudos;
import br.com.isaque.jarvis.modulos.ModuloEco;
import br.com.isaque.jarvis.saida.SaidaConsole;
import br.com.isaque.jarvis.saida.SaidaDeResposta;

public class Main {
    public static void main(String[] args) {
        EntradaDeComando entrada = new EntradaTeclado();
        SaidaDeResposta saida = new SaidaConsole();
        Modulo estudos = new ModuloAmbienteEstudos();
        Modulo eco = new ModuloEco();

        while (true) {
            String comando = entrada.ouvir();
            if (comando.trim().equalsIgnoreCase("sair")) {
                break;
            }
            if (comando.trim().equalsIgnoreCase("estudos")) {
                saida.falar(estudos.executar(comando));
                continue;
            }
            if (comando.toLowerCase().startsWith("eco ")) {
                saida.falar(eco.executar(comando.substring(4)));
                continue;
            }
            saida.falar("Você disse: " + comando);
        }
    }
}