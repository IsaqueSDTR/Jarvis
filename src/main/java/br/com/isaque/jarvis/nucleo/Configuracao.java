package br.com.isaque.jarvis.nucleo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Configuracao {
    private Properties propriedades = new Properties();

    public Configuracao(String caminho) {
        try (FileInputStream arquivo = new FileInputStream(caminho)) {
            propriedades.load(arquivo);
        } catch (IOException e) {
            System.out.println("Não consegui ler " + caminho + ": " + e.getMessage());
        }
    }

    public String obter(String chave) {
        return propriedades.getProperty(chave, "");
    }
}