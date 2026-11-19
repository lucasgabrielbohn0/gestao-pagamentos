package com.gestao.pagamento.ui;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuPrincipal {
    private Scanner scanner;
    private RegistroTrabalhoMenu registroTrabalhoMenu;
    private PagamentoMenu pagamentoMenu;


    public MenuPrincipal(Scanner scanner, RegistroTrabalhoMenu registroTrabalhoMenu, PagamentoMenu pagamentoMenu) {
        this.scanner = scanner;
        this.registroTrabalhoMenu = registroTrabalhoMenu;
        this.pagamentoMenu = pagamentoMenu;
    }

    private void mostrarMenu() {
        System.out.println("\nMenu Dias Trabalhados");
        System.out.println("1 - Registrar Dia De Trabalho ");
        System.out.println("2 - Vizualizar Dias Trabalhados ");
        System.out.println("3 - Deletar Dias Trabalhados ");
        System.out.println("4 - Consultar o Saldo ");
        System.out.println("5 - Registrar Pagamento ");
        System.out.println("6 - Visualizar Pagamentos ");
        System.out.println("7 - Deletar Pagamento ");
        System.out.println("0 - Sair");
    }

    public void executar() {
        int opcao = -1;

        do {
            mostrarMenu();
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("digite apenas numeros validos");
                continue;
            }


            switch (opcao) {
                case 1:
                    registroTrabalhoMenu.registrarDiaTrabalho();
                    break;

                case 2:
                    registroTrabalhoMenu.visualizarDiasTrabalhados();
                    break;

                case 3:
                    registroTrabalhoMenu.deletarDiaDeTrabalho();
                    break;

                case 4:
                    registroTrabalhoMenu.visualizarSaldoAReceber();
                    break;

                case 5:
                    pagamentoMenu.registrarPagamento();
                    break;

                case 6:
                    pagamentoMenu.vizualizarPagamentos();
                    break;

                case 7:
                    pagamentoMenu.removerPagamento();
                    break;

                case 0:
                    System.out.println("saindo..");
                    break;

                default:
                    System.out.println("opçao invalida");

            }
        } while (opcao != 0);

    }

}


