package com.gestao.pagamento.domain;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Pagamento {
    private final Long id;
    private final BigDecimal valor;
    private final LocalDate data;

    public Pagamento(Long id, BigDecimal valor, LocalDate data) {
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("O valor do pagamento é obrigatorio e deve ser positivo");
        }

        if (data == null) {
            throw new IllegalArgumentException("A data do pagamento é obrigatoria");
        }
        this.valor = valor;
        this.data = data;
        this.id = id;
    }

    public Pagamento(BigDecimal valor, LocalDate data) {
        this(null, valor, data);
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public Long getId() {
        return id;
    }
}


