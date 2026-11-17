package com.gestao.pagamento.ui;

import com.gestao.pagamento.domain.Pagamento;
import com.gestao.pagamento.repository.PagamentoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class PagamentoMenu {
    private Scanner scanner;
    private PagamentoRepository pagamentoRepository;

    public PagamentoMenu(Scanner scanner, PagamentoRepository pagamentoRepository) {
        this.scanner = scanner;
        this.pagamentoRepository = pagamentoRepository;
    }

    public void registrarPagamento() {
        LocalDate dataPagamento;
        try {
            dataPagamento = lerDataPagamento();
        } catch (DateTimeParseException e) {
            System.out.println("Data invalida. Digite no formato de Dia, Mes e Ano");
            return;
        }
        System.out.println("data registrada com sucesso!");

        BigDecimal valorPagamento;
        try {
            valorPagamento = lerValorPagamento();
        } catch (NumberFormatException e) {
            System.out.println("digite um valor sem espaços. Para centavos use ponto");
            return;
        }
        try {
            final Pagamento pagamento = new Pagamento(valorPagamento, dataPagamento);
            pagamentoRepository.cadastrarPagamento(pagamento);
            System.out.println("pagamento realizado com sucesso!");
        } catch (IllegalArgumentException erro) {
            System.out.println(erro.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void vizualizarPagamentos() {
        try {
            List<Pagamento> pagamentosRetornados = pagamentoRepository.buscarTodosPagamentos();
            for (Pagamento pagamento : pagamentosRetornados) {
                System.out.println("id = " + pagamento.getId());
                System.out.println("data | " + pagamento.getData());
                System.out.println("valor: R$ " + pagamento.getValor() + "\n");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private LocalDate lerDataPagamento() {
        System.out.println("Qual a data do pagamento?");
        String dataDiaPagamento = scanner.nextLine();
        return ConversorData.converterTextoParaData(dataDiaPagamento);
    }

    private BigDecimal lerValorPagamento() {
        System.out.println("Qual o valor do pagamento? digite um valor sem espaços. Para centavos use ponto");
        String textoValorPagamento = scanner.nextLine();
        return new BigDecimal(textoValorPagamento);
    }
}
