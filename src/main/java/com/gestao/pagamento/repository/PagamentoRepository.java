package com.gestao.pagamento.repository;

import com.gestao.pagamento.database.ConectionFactory;
import com.gestao.pagamento.domain.Pagamento;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PagamentoRepository {

    public void cadastrarPagamento(Pagamento pagamento) throws SQLException {
        String sql = "INSERT INTO pagamentos(valor, data) VALUES (?, ?)";

        try (Connection connection = ConectionFactory.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setBigDecimal(1, pagamento.getValor());
            preparedStatement.setDate(2, java.sql.Date.valueOf(pagamento.getData()));

            preparedStatement.executeUpdate();
        }
    }

    public BigDecimal somaPagamentos() throws SQLException {
        String sql = "SELECT COALESCE(SUM(valor), 0) AS total FROM pagamentos";
        try (Connection connection = ConectionFactory.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                resultSet.next();
                BigDecimal totalPagamento = resultSet.getBigDecimal("total");
                return totalPagamento;
            }
        }
    }

    public void deletarPagamento(Long id) throws SQLException {
        String sql = "DELETE from pagamentos WHERE id = ?";
        try (Connection connection = ConectionFactory.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        }

    }

    public List<Pagamento> buscarTodosPagamentos() throws SQLException {
        List<Pagamento> pagamentosRetornados = new ArrayList<>();
        String sql = "SELECT id, data, valor FROM pagamentos ORDER BY data ASC";
        try (Connection connection = ConectionFactory.openConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    LocalDate data = resultSet.getObject("data", LocalDate.class);
                    BigDecimal valor = resultSet.getBigDecimal("valor");
                    Pagamento pagamento = new Pagamento(id, valor, data);
                    pagamentosRetornados.add(pagamento);
                }
            }
        }
        return pagamentosRetornados;
    }
}

