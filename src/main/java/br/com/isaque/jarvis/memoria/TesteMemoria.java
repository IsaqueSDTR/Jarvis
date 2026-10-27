
package br.com.isaque.jarvis;

import br.com.isaque.jarvis.memoria.MemoriaRepository;

import java.sql.SQLException;

public class TesteMemoria {

    public static void main(String[] args) {
        MemoriaRepository memoria = new MemoriaRepository();

        try {
            memoria.salvar(
                    "teste",
                    "memoria_persistente",
                    "O banco de memória do Jarvis está funcionando."
            );

            var resultado = memoria.buscar(
                    "teste",
                    "memoria_persistente"
            );

            System.out.println(
                    resultado.orElse("Memória não encontrada.")
            );

        } catch (SQLException e) {
            System.err.println(
                    "Erro ao testar a memória: " + e.getMessage()
            );
        }
    }
}
