package br.com.banco.services.stub;

import br.com.banco.entities.Conta;
import br.com.banco.entities.Transferencia;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransferenciaStub {
    public static Transferencia valid() {
        BigDecimal valor = new BigDecimal("220.0");
        return Transferencia.builder()
                .id(1L)
                .dataTransferencia(LocalDate.of(2019, 9, 12))
                .valor(valor)
                .tipo("DEPÓSITO")
                .nomeOperadorTransacao("Beltrano")
                .conta(new Conta())
                .build();
    }
}
