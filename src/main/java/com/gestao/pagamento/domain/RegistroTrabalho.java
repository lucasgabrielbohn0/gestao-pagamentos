package com.gestao.pagamento.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RegistroTrabalho {
    private final Long id;
    private final LocalDate data;
    private final BigDecimal valor;
    private final String descricao;

    public RegistroTrabalho(Long id, LocalDate data, BigDecimal valor, String descricao) {
        if (data == null) {
            throw new IllegalArgumentException("A data do trabalho é obrigatoria");
        }
        if (valor == null || valor.signum() <= 0) {
            throw new IllegalArgumentException("o valor do trabalho é obrigatorio e deve ser positivo");
        }

        this.id = id;
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
    }

    public RegistroTrabalho(LocalDate data, BigDecimal valor, String descricao) {
        this(null, data, valor, descricao);
    }

    public LocalDate getData() {
        return data;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public static RegistroTrabalho meioDia(LocalDate data, String descricao) {
        return new RegistroTrabalho(data, new BigDecimal("50"), descricao);
    }

    public static RegistroTrabalho diaInteiro(LocalDate data, String descricao) {
        return new RegistroTrabalho(data, new BigDecimal("100"), descricao);
    }
}
