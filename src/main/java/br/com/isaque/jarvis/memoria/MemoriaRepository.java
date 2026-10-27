
package br.com.isaque.jarvis.memoria;

import br.com.isaque.jarvis.config.ConexaoMySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class MemoriaRepository {

    public void salvar(
            String categoria,
            String chave,
            String valor
    ) throws SQLException {

        String sql = """
                INSERT INTO memorias (categoria, chave, valor)
                VALUES (?, ?, ?)
                ON DUPLICATE KEY UPDATE
                    valor = ?,
                    data_atualizacao = CURRENT_TIMESTAMP
                """;

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(1, categoria);
            comando.setString(2, chave);
            comando.setString(3, valor);
            comando.setString(4, valor);

            comando.executeUpdate();
        }
    }

    public Optional<String> buscar(
            String categoria,
            String chave
    ) throws SQLException {

        String sql = """
                SELECT valor
                FROM memorias
                WHERE categoria = ? AND chave = ?
                """;

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(1, categoria);
            comando.setString(2, chave);

            try (ResultSet resultado = comando.executeQuery()) {
                if (resultado.next()) {
                    return Optional.of(resultado.getString("valor"));
                }

                return Optional.empty();
            }
        }
    }

    public boolean excluir(
            String categoria,
            String chave
    ) throws SQLException {

        String sql = """
                DELETE FROM memorias
                WHERE categoria = ? AND chave = ?
                """;

        try (Connection conexao = ConexaoMySQL.obterConexao();
             PreparedStatement comando =
                     conexao.prepareStatement(sql)) {

            comando.setString(1, categoria);
            comando.setString(2, chave);

            return comando.executeUpdate() > 0;
        }
    }
}
