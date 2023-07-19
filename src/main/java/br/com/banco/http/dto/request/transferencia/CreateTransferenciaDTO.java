package br.com.banco.http.dto.request.transferencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateTransferenciaDTO {
    @NotNull
    private LocalDate dataTransferencia;

    @NotNull
    private BigDecimal valor;

    @NotNull
    private String tipo;

    @NotNull
    private String nomeOperadorTransacao;

    @NotNull
    private Long contaId;
}