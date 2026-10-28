package br.com.isaque.jarvis.modulos;

import br.com.isaque.jarvis.memoria.MemoriaService;

import java.sql.SQLException;

public class ModuloMemoria implements Modulo {

    private static final String USO = """
            Uso:
              memoria salvar <categoria> <chave> <valor>
              memoria buscar <categoria> <chave>
              memoria esquecer <categoria> <chave>""";

    private final MemoriaService memoria;

    public ModuloMemoria(MemoriaService memoria) {
        this.memoria = memoria;
    }

    @Override
    public String executar(String dados) {
        // Divide em até 4 partes: ação, categoria, chave e o resto (valor)
        String[] partes = dados.trim().split("\\s+", 4);
        String acao = partes[0].toLowerCase();

        try {
            switch (acao) {
                case "salvar":
                    if (partes.length < 4) return USO;
                    memoria.salvarMemoria(partes[1], partes[2], partes[3]);
                    return "Memória salva: " + partes[1] + "/" + partes[2];

                case "buscar":
                    if (partes.length < 3) return USO;
                    return memoria.buscarMemoria(partes[1], partes[2])
                            .orElse("Não encontrei essa memória.");

                case "esquecer":
                    if (partes.length < 3) return USO;
                    boolean removida = memoria.excluirMemoria(partes[1], partes[2]);
                    return removida
                            ? "Memória removida."
                            : "Não havia nada para remover.";

                default:
                    return USO;
            }
        } catch (SQLException e) {
            return "Erro no banco de dados: " + e.getMessage();
        }
    }
}