package com.gestao.pagamento;
import com.gestao.pagamento.Financeiro.ControleFinanceiro;
import com.gestao.pagamento.repository.PagamentoRepository;
import com.gestao.pagamento.repository.RegistroTrabalhoRepository;
import com.gestao.pagamento.ui.MenuPrincipal;
import com.gestao.pagamento.ui.PagamentoMenu;
import com.gestao.pagamento.ui.RegistroTrabalhoMenu;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        RegistroTrabalhoRepository registroTrabalhoRepository = new RegistroTrabalhoRepository();
        PagamentoRepository pagamentoRepository = new PagamentoRepository();
        ControleFinanceiro controleFinanceiro = new ControleFinanceiro(pagamentoRepository, registroTrabalhoRepository);
        RegistroTrabalhoMenu registroTrabalhoMenu = new RegistroTrabalhoMenu(scanner, registroTrabalhoRepository, controleFinanceiro);
        PagamentoMenu pagamentoMenu = new PagamentoMenu(scanner, pagamentoRepository);
        MenuPrincipal menuPrincipal = new MenuPrincipal(scanner, registroTrabalhoMenu, pagamentoMenu);

        menuPrincipal.executar();
    }
}