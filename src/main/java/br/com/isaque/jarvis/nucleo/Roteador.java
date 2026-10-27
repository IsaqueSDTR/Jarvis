
package br.com.isaque.jarvis.nucleo;

import br.com.isaque.jarvis.ia.ProvedorIA;
import br.com.isaque.jarvis.modulos.Modulo;

import java.util.HashMap;
import java.util.Map;

public class Roteador {

    private final Map<String, Modulo> modulos = new HashMap<>();
    private final ProvedorIA ia;

    public Roteador(ProvedorIA ia) {
        this.ia = ia;
    }

    public void registrar(String palavra, Modulo modulo) {
        modulos.put(palavra.toLowerCase(), modulo);
    }

    public String rotear(String comando) {
        String comandoLimpo = comando.trim();

        if (comandoLimpo.isEmpty()) {
            return "";
        }

        String[] partes = comandoLimpo.split("\\s+", 2);
        String palavra = partes[0].toLowerCase();
        String dados = partes.length > 1 ? partes[1] : "";

        Modulo modulo = modulos.get(palavra);

        if (modulo != null) {
            return modulo.executar(dados);
        }

        return ia.perguntar(comandoLimpo);
    }
}
