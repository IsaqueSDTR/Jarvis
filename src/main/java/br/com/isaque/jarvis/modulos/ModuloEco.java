package br.com.isaque.jarvis.modulos;


public class ModuloEco implements Modulo {
    @Override
    public String executar(String dados) {
        return dados.toUpperCase();
    }

}
