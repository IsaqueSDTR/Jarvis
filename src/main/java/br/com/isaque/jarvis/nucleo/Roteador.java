package br.com.isaque.jarvis.nucleo;

import br.com.isaque.jarvis.modulos.Modulo;

import java.util.HashMap;
import java.util.Map;

public class Roteador {
    private Map<String, Modulo> modulos = new HashMap<>();

    public void registrar(String palavra, Modulo modulo) {
        modulos.put(palavra, modulo);
    }
    public String rotear(String comando) {
        String[] partes = comando.trim().split(" ", 2);
        String palavra = partes[0].toLowerCase();
        String dados = partes.length > 1 ? partes[1] : "";

        Modulo modulo = modulos.get(palavra);
        if (modulo == null) {
            return null;
        }
        return modulo.executar(dados);
    }
}
