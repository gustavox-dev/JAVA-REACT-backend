package br.com.banco.http.controllers;

import br.com.banco.entities.Transferencia;
import br.com.banco.http.dto.request.transferencia.TransferenciaDTO;
import br.com.banco.services.transferencia.TransferenciaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Validated
@RequestMapping("/api/transferencia")
@RestController
@AllArgsConstructor
public class TransferenciaController {

    private final TransferenciaService service;

    @GetMapping("/filtro-por-conta")
    public ResponseEntity<List<TransferenciaDTO>> findByContaId(Long id) {
        List<TransferenciaDTO> transferenciasDTO = service.findByContaId(id);
        return ResponseEntity.ok(transferenciasDTO);
    }

    @GetMapping("/filtro-por-dia")
    public List<TransferenciaDTO> findByDataCriacaoBetween(@RequestParam("dataInicio") String dataInicio,
                                                           @RequestParam("dataFim") String dataFim) {

        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);
        return service.findByDataCriacaoBetween(inicio, fim);
    }


    @GetMapping
    public ResponseEntity<List<TransferenciaDTO>> findAll() {
        List<TransferenciaDTO> transferenciasDTO = service.findAll();
        return ResponseEntity.ok(transferenciasDTO);
    }

    @GetMapping("/nome-operador")
    public List<TransferenciaDTO> findByNomeOperadorTransacao(@RequestParam("nomeOperadorTransacao") String nomeOperadorTransacao) {
        return service.findByNomeOperadorTransacao(nomeOperadorTransacao);
    }

    @GetMapping("/transferencias-operador")
    public List<TransferenciaDTO> findAllTransferenciasByDataCriacaoBetweenAndNomeOperadorTransacao(@RequestParam("nomeOperadorTransacao") String nomeOperadorTransacao,
                                                                                                    @RequestParam("dataInicio") String dataInicio,
                                                                                                    @RequestParam("dataFim") String dataFim) {
        LocalDate inicio = LocalDate.parse(dataInicio);
        LocalDate fim = LocalDate.parse(dataFim);
        return service.findAllTransferenciasByDataCriacaoBetweenAndNomeOperadorTransacao(nomeOperadorTransacao, inicio, fim);
    }

}
