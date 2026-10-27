package br.com.isaque.jarvis.modulos;

import java.awt.Desktop;
import java.net.URI;

public class ModuloAmbienteEstudos implements Modulo {

    @Override
    public String executar(String dados) {
        try {
            Desktop.getDesktop().browse(new URI("https://estudante.estacio.br/disciplinas"));
            return "Ambiente de estudos iniciado com sucesso!";
        } catch (Exception e) {
            e.printStackTrace(); // mostra o motivo real no console
            return "Não consegui abrir o link.";
        }
    }
}
