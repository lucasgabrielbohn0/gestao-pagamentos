package com.gestao.pagamento.ui;

import com.gestao.pagamento.Financeiro.ControleFinanceiro;
import com.gestao.pagamento.domain.RegistroTrabalho;
import com.gestao.pagamento.repository.RegistroTrabalhoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RegistroTrabalhoMenu {
    private Scanner scanner;
    private RegistroTrabalhoRepository registroTrabalhoRepository;
    private ControleFinanceiro controleFinanceiro;


    public RegistroTrabalhoMenu(Scanner scanner, RegistroTrabalhoRepository registroTrabalhoRepository, ControleFinanceiro controleFinanceiro) {
        this.scanner = scanner;
        this.registroTrabalhoRepository = registroTrabalhoRepository;
        this.controleFinanceiro = controleFinanceiro;
    }

    public void registrarDiaTrabalho() {

        LocalDate dataTrabalho;
        try {
            dataTrabalho = lerDataTrabalho();
        } catch (DateTimeParseException e) {
            System.out.println("data invalida");
            return;
        }
        System.out.println("Data registrada!\n");

        String servicoRealizado = lerDescricao();
        System.out.println("descriçao do serviço cadastrada!");

        int opcaoPeridoTrabalhado = lerPeriodoTrabalhado();
        RegistroTrabalho registroTrabalho;
        switch (opcaoPeridoTrabalhado) {

            case 1:
                registroTrabalho = RegistroTrabalho.diaInteiro(dataTrabalho, servicoRealizado);
                break;

            case 2:
                registroTrabalho = RegistroTrabalho.meioDia(dataTrabalho, servicoRealizado);
                break;

            default:
                System.out.println("digite uma opçao valida");
                return;
        }
        try {
            registroTrabalhoRepository.cadastrarRegistroDeTrabalho(registroTrabalho);
            System.out.println("Dia de trabalho registrado!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void visualizarDiasTrabalhados() {
        try {
            final List<RegistroTrabalho> diasTrabalhados = registroTrabalhoRepository.buscarTodos();
            for (RegistroTrabalho diaTrabalhado : diasTrabalhados) {
                System.out.println("id = " + diaTrabalhado.getId());
                System.out.println("descrição = " + diaTrabalhado.getDescricao());
                System.out.println("data |  " + diaTrabalhado.getData());
                System.out.println("valor: R$ " + diaTrabalhado.getValor() + "\n");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void visualizarSaldoAReceber() {
        try {
            BigDecimal totalAReceber = controleFinanceiro.descontaPagamento();
            System.out.println("o total a receber é: " + totalAReceber);
        } catch (SQLException e) {
            System.out.println("erro no banco" + e.getMessage());

        }
    }

    public void deletarDiaDeTrabalho() {
        visualizarDiasTrabalhados();
        Long idRemover = lerIdRemover();
        try {
            registroTrabalhoRepository.deletarDiaDeTrabalho(idRemover);
        } catch (SQLException e) {
            System.out.println("erro no banco" + e.getMessage());
        }
        System.out.println("Registro de trabalho de ID: "+idRemover+" removido com sucesso");
    }

    private LocalDate lerDataTrabalho() {
        System.out.println("Digite a data do dia trabalhado: ");
        String dataDiaTrabalhado = scanner.nextLine();
        return ConversorData.converterTextoParaData(dataDiaTrabalhado);
    }

    private String lerDescricao() {
        System.out.println("Qual o serviço realizado? (opcional)");
        String descricao = scanner.nextLine();
        return descricao;
    }

    private int lerPeriodoTrabalhado() {
        int infoPeriodoTrabalhado;
        while (true) {
            System.out.println("\nquantos periodos voce trabalhou?");
            System.out.println("1 - dia inteiro");
            System.out.println("2 - meio dia");
            try {
                infoPeriodoTrabalhado = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("não digite letras. Digite apenas 1 ou 2");
                continue;
            }
            if (infoPeriodoTrabalhado == 1 || infoPeriodoTrabalhado == 2) {
                return infoPeriodoTrabalhado;
            }
            System.out.println("numero invalido. Digte apenas 1 ou 2");
        }
    }

    private Long lerIdRemover() {
        System.out.println("Digite o ID do registro que deseja remover: ");
        Long idRemover = scanner.nextLong();
        scanner.nextLine();
        return idRemover;
    }
}
