package br.com.isaque.jarvis.entrada;

import java.util.Scanner;

public class EntradaTeclado implements EntradaDeComando{
    private Scanner scanner = new Scanner(System.in);

    @Override
    public String ouvir() {
        return scanner.nextLine();
    }
}
