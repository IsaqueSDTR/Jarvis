
package br.com.isaque.jarvis.memoria;

import java.sql.SQLException;
import java.util.Optional;

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

    private void validarTexto(String texto, String campo) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException(
                    campo + " não pode ser vazio."
            );
        }
    }
}
