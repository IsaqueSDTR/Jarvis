package br.com.isaque.jarvis.modulos;

import br.com.isaque.jarvis.nucleo.Configuracao;

import java.awt.Desktop;
import java.net.URI;

public class ModuloFinancas implements Modulo {
    private Configuracao config2;

    public ModuloFinancas(Configuracao config) {
        this.config2 = config;
    }

    @Override
    public String executar(String dados) {
        String texto = config2.obter("links.financas");
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
