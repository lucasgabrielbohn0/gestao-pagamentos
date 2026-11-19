package com.gestao.pagamento.repository;

import com.gestao.pagamento.database.ConectionFactory;
import com.gestao.pagamento.domain.RegistroTrabalho;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegistroTrabalhoRepository {

    public void cadastrarRegistroDeTrabalho(RegistroTrabalho registroTrabalho) throws SQLException {
        String sql = "INSERT INTO registros_trabalho (data, valor, descricao) VALUES(?,?,?)";
        try (Connection connection = ConectionFactory.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setDate(1, java.sql.Date.valueOf(registroTrabalho.getData()));
            preparedStatement.setBigDecimal(2, registroTrabalho.getValor());
            preparedStatement.setString(3, registroTrabalho.getDescricao());

            preparedStatement.executeUpdate();
        }
    }

    public BigDecimal somaTrabalhosRegistrados() throws SQLException {
        String sql = "SELECT COALESCE(SUM(valor), 0) AS total FROM registros_trabalho";
        try (Connection connection = ConectionFactory.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                BigDecimal totalTrabalhoRegistrado = resultSet.getBigDecimal("total");
                return totalTrabalhoRegistrado;
            }
        }
    }

    public List<RegistroTrabalho> buscarTodos() throws SQLException {
        List<RegistroTrabalho> trabalhosRetornados = new ArrayList<>();
        String sql = "SELECT id, data, valor, descricao FROM registros_trabalho ORDER BY data ASC";
        try (Connection connection = ConectionFactory.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    LocalDate data = resultSet.getObject("data", LocalDate.class);
                    BigDecimal valor = resultSet.getBigDecimal("valor");
                    String descricao = resultSet.getString("descricao");
                    RegistroTrabalho registroTrabalho = new RegistroTrabalho(id, data, valor, descricao);
                    trabalhosRetornados.add(registroTrabalho);
                }
            }
        }
        return trabalhosRetornados;
    }

    public void deletarDiaDeTrabalho(Long id) throws SQLException {
        String sql = "DELETE from registros_trabalho WHERE id = ?";
        try (Connection connection = ConectionFactory.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        }
    }
}
