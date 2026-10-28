
package br.com.isaque.jarvis.memoria;

import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

public class MemoriaService {

    private final MemoriaRepository repositorio;

    public MemoriaService() {
        this.repositorio = new MemoriaRepository();
    }

    public void salvarMemoria(
            String categoria,
            String chave,
            String valor
    ) throws SQLException {

        validarTexto(categoria, "A categoria");
        validarTexto(chave, "A chave");
        validarTexto(valor, "O valor");

        repositorio.salvar(
                categoria.trim(),
                chave.trim(),
                valor.trim()
        );
    }

    public Optional<String> buscarMemoria(
            String categoria,
            String chave
    ) throws SQLException {

        validarTexto(categoria, "A categoria");
        validarTexto(chave, "A chave");

        return repositorio.buscar(
                categoria.trim(),
                chave.trim()
        );
    }

    public boolean excluirMemoria(
            String categoria,
            String chave
    ) throws SQLException {

        validarTexto(categoria, "A categoria");
        validarTexto(chave, "A chave");

        return repositorio.excluir(
                categoria.trim(),
                chave.trim()
        );
    }

    public List<String> listarPorCategoria(String categoria) throws SQLException {

        validarTexto(categoria, "A categoria");

        return repositorio.listarPorCategoria(categoria.trim());
    }


    public String montarContexto() throws SQLException {

        List<String> memorias = listarPorCategoria("pessoal");

        if (memorias.isEmpty()) {
            return "";
        }

        StringBuilder texto = new StringBuilder();

        texto.append("Informações que você sabe sobre Isaque:\n");

        for (String memoria : memorias) {
            texto.append("- ")
                    .append(memoria)
                    .append("\n");
        }

        return texto.toString();
    }

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    campo + " não pode ser vazio."
            );
        }
    }
}
