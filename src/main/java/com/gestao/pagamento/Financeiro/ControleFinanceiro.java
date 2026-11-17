package com.gestao.pagamento.Financeiro;
import com.gestao.pagamento.repository.PagamentoRepository;
import com.gestao.pagamento.repository.RegistroTrabalhoRepository;

import java.math.BigDecimal;
import java.sql.SQLException;

public class ControleFinanceiro {
    private PagamentoRepository pagamentoRepository;
    private RegistroTrabalhoRepository registroTrabalhoRepository;

    public ControleFinanceiro(PagamentoRepository pagamentoRepository, RegistroTrabalhoRepository registroTrabalhoRepository) {
        this.pagamentoRepository = pagamentoRepository;
        this.registroTrabalhoRepository = registroTrabalhoRepository;
    }

    public BigDecimal descontaPagamento() throws SQLException {
        BigDecimal totalTrabalhos = registroTrabalhoRepository.somaTrabalhosRegistrados();
        BigDecimal totalPagamentos = pagamentoRepository.somaPagamentos();
        BigDecimal novoSaldoAReceber =  totalTrabalhos.subtract(totalPagamentos);
        return novoSaldoAReceber;
    }
}
