package br.com.isaque.jarvis.modulos;

import br.com.isaque.jarvis.nucleo.Configuracao;

import java.awt.Desktop;
import java.net.URI;

public class ModuloAmbienteEstudos implements Modulo {
    private Configuracao config;

    public ModuloAmbienteEstudos(Configuracao config) {
        this.config = config;
    }

    @Override
    public String executar(String dados) {
        String texto = config.obter("links.estudos");
        if (texto.isBlank()) {
            return "Nenhum link encontrado!";
        }
        String[] links = texto.split(",");
        int abertos = 0;
        for (String link : links) {
            try {
                Desktop.getDesktop().browse(new URI(link.trim()));
                abertos++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "Abri " + abertos + " de " + links.length + " links.";
    }
}
