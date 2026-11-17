package com.gestao.pagamento.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class ConversorData {

    public static LocalDate converterTextoParaData(String data) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM uu").withResolverStyle(ResolverStyle.STRICT);
        LocalDate dataTrabalho = LocalDate.parse(data, formatter);
        return dataTrabalho;
    }
}
