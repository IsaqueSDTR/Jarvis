package br.com.isaque.jarvis.ia;

public class FalhaProvedorIA extends RuntimeException {

    public FalhaProvedorIA(String mensagem) {
        super(mensagem);
    }

    public FalhaProvedorIA(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}