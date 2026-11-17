package com.gestao.pagamento.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConectionFactory {
    public static Connection openConnection() throws SQLException {
        String url = "jdbc:postgresql://localhost:5432/gestao_pagamento_dia";
        String usuario = "postgres";
        String senha = System.getenv("DB_PASSWORD");
        Connection connection = DriverManager.getConnection(url, usuario, senha);
        return connection;
    }
}
