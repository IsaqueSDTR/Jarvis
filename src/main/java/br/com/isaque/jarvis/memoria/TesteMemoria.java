
package br.com.isaque.jarvis.memoria;

import java.sql.SQLException;

public class TesteMemoria {

    public static void main(String[] args) {
        testarMontarContexto();
    }

    public static void testarMontarContexto() {
        MemoriaService memoria = new MemoriaService();

        try {
            String contexto = memoria.montarContexto();

            System.out.println("=== CONTEXTO DO JARVIS ===");

            if (contexto.isBlank()) {
                System.out.println(
                        "Nenhuma memória encontrada na categoria pessoal."
                );
            } else {
                System.out.println(contexto);
            }

            System.out.println("==========================");

        } catch (SQLException e) {
            System.err.println(
                    "Erro ao consultar as memórias: " + e.getMessage()
            );
            e.printStackTrace();
        }
    }
}
