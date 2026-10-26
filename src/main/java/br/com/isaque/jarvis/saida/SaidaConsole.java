package br.com.isaque.jarvis.saida;

public class SaidaConsole implements SaidaDeResposta {

    @Override
    public void falar(String resposta) {
        System.out.println(resposta);
    }
}
