
package br.com.isaque.jarvis;

import br.com.isaque.jarvis.memoria.MemoriaRepository;
import br.com.isaque.jarvis.memoria.MemoriaService;
import java.sql.SQLException;

public class TesteMemoria {

    public static void main(String[] args) {
        MemoriaRepository memoria = new MemoriaRepository();

        try {
            MemoriaService service = new MemoriaService();

            service.salvarMemoria("pessoal", "cidade", "Contagem");
            service.salvarMemoria("pessoal", "curso", "ADS");

            System.out.println(service.listarPorCategoria("pessoal"));
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
            memoria.salvar("teste", "cidade", "Contagem");
            memoria.salvar("teste", "curso", "ADS");

            System.out.println(memoria.listarPorCategoria("teste"));

        } catch (SQLException e) {
            System.err.println(
                    "Erro ao testar a memória: " + e.getMessage()
            );
        }
    }
}
