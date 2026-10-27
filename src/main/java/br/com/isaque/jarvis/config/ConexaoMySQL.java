
package br.com.isaque.jarvis.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConexaoMySQL {

    private static final String URL =
            "jdbc:mysql://localhost:3306/jarvis_db"
                    + "?useSSL=false"
                    + "&serverTimezone=America/Sao_Paulo"
                    + "&characterEncoding=UTF-8";

    private static final String USUARIO =
            System.getenv("DB_USER");

    private static final String SENHA =
            System.getenv("DB_PASSWORD");

    private ConexaoMySQL() {
        // Impede a criação de objetos desta classe.
    }

    public static Connection obterConexao() throws SQLException {
        if (USUARIO == null || USUARIO.isBlank()) {
            throw new SQLException(
                    "A variável de ambiente DB_USER não está configurada."
            );
        }

        if (SENHA == null || SENHA.isBlank()) {
            throw new SQLException(
                    "A variável de ambiente DB_PASSWORD não está configurada."
            );
        }

        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}
