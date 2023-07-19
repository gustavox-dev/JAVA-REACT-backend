package br.com.banco.services;

import br.com.banco.exceptions.CustomException;
import br.com.banco.http.dto.request.transferencia.TransferenciaDTO;
import br.com.banco.repository.TransferenciaRepository;
import br.com.banco.services.stub.TransferenciaStub;
import br.com.banco.services.transferencia.TransferenciaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class TransfrenciaServiceTest {

    @Mock
    private TransferenciaRepository repo;

    @Autowired
    @InjectMocks
    private TransferenciaService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTransferencia();
    }

    @Test
    @DisplayName("Should return success when find account bby id")
    void should_returnSuccess_whenFindAccountById() {

        when(repo.findByContaId(anyLong())).thenReturn(Arrays.asList(TransferenciaStub.valid()));
        List<TransferenciaDTO> findByContaId = service.findByContaId(1L);

        findByContaId.forEach(transferenciaDTO -> assertNotNull(transferenciaDTO));
        assertNotNull(findByContaId);
        assertFalse(findByContaId.isEmpty());
    }

    @Test
    @DisplayName("Should return success when findAll transfers")
    void should_returnSuccess_whenFindAllTransfers() {
        when(repo.findAll()).thenReturn(Arrays.asList(TransferenciaStub.valid()));
        List<TransferenciaDTO> findAllTransfers = service.findAll();

        findAllTransfers.forEach(transferenciaDTO -> assertNotNull(transferenciaDTO));
        assertNotNull(findAllTransfers);
        assertFalse(findAllTransfers.isEmpty());
    }

    @Test
    @DisplayName("Should return fail when find all transfers")
    void should_returnFail_whenFindAllTransfers() {
        // Simule um erro quando o repositório lança uma exceção
        when(repo.findAll()).thenThrow(new CustomException("Erro ao buscar todas as transferências", HttpStatus.NOT_FOUND));


        assertThrows(CustomException.class, () -> service.findAll());

    }

    @Test
    @DisplayName("Should return success when find transfer per date created")
    void should_returnSuccess_whenFindTransferPerDateCreated() {
        LocalDate dataInicio = LocalDate.of(2019, 9, 07);
        LocalDate dataFim = LocalDate.of(2021, 9, 07);
        when(repo.findByDataTransferenciaBetween(dataInicio, dataFim)).thenReturn(Arrays.asList(TransferenciaStub.valid()));
        List<TransferenciaDTO> findByDataCriacaoBetween = service.findByDataCriacaoBetween(dataInicio, dataFim);

        findByDataCriacaoBetween.forEach(transferenciaDTO -> assertNotNull(transferenciaDTO));
        assertNotNull(findByDataCriacaoBetween);
        assertFalse(findByDataCriacaoBetween.isEmpty());
    }

    @Test
    @DisplayName("Should return fail when find transfer per date created")
    void should_returnFail_whenFindTransferPerDateCreated() {
        LocalDate dataInicio = LocalDate.of(2023, 9, 07);
        LocalDate dataFim = LocalDate.of(2021, 9, 07);

        when(repo.findByDataTransferenciaBetween(dataInicio, dataFim)).thenThrow(new CustomException(HttpStatus.BAD_REQUEST, "Erro ao buscar transferências por data de criação"));

        assertThrows(CustomException.class, () -> service.findByDataCriacaoBetween(dataInicio, dataFim));
    }

    @Test
    @DisplayName("Should return success when locating transfer by operator name")
    void should_returnSuccess_whenLocatingTransferByOperatorName() {
        when(repo.findByNomeOperadorTransacao("Beltrano")).thenReturn(Arrays.asList(TransferenciaStub.valid()));
        List<TransferenciaDTO> findByNomeOperadorTransacao = service.findByNomeOperadorTransacao("Beltrano");

        findByNomeOperadorTransacao.forEach(transferenciaDTO -> assertNotNull(transferenciaDTO));
        assertNotNull(findByNomeOperadorTransacao);
        assertFalse(findByNomeOperadorTransacao.isEmpty());
    }

    @Test
    @DisplayName("Should return fail when locating transfer by operator name")
    void should_returnFail_whenLocatingTransferByOperatorName() {
        when(repo.findByNomeOperadorTransacao("Beltrano")).thenThrow(new CustomException(HttpStatus.NOT_FOUND, "Erro ao buscar transferências por nome do operador"));

        assertThrows(CustomException.class, () -> service.findByNomeOperadorTransacao("Beltrano"));
    }

    @Test
    @DisplayName("Should return success when locating transfer by operator name and date created")
    void should_returnSuccess_whenLocatingTransferByOperatorNameAndDateCreated() {
        LocalDate dataInicio = LocalDate.of(2019, 9, 07);
        LocalDate dataFim = LocalDate.of(2021, 9, 07);
        when(repo.findAllTransferenciasByDataTransferenciaBetweenAndNomeOperadorTransacao("Beltrano", dataInicio, dataFim)).thenReturn(Arrays.asList(TransferenciaStub.valid()));
        List<TransferenciaDTO> findByOperatorAndDateCreated = service.findAllTransferenciasByDataCriacaoBetweenAndNomeOperadorTransacao("Beltrano", dataInicio, dataFim);

        findByOperatorAndDateCreated.forEach(transferenciaDTO -> assertNotNull(transferenciaDTO));
        assertNotNull(findByOperatorAndDateCreated);
        assertFalse(findByOperatorAndDateCreated.isEmpty());
    }

    @Test
    @DisplayName("Should return fail when locating transfer by operator name and date created")
    void should_returnFail_whenLocatingTransferByOperatorNameAndDateCreated() {
        LocalDate dataInicio = LocalDate.of(2019, 9, 07);
        LocalDate dataFim = LocalDate.of(2021, 9, 07);

        when(repo.findAllTransferenciasByDataTransferenciaBetweenAndNomeOperadorTransacao("Beltrano", dataInicio, dataFim))
                .thenThrow(new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao buscar transferências por nome do operador e data de criação"));

        assertThrows(CustomException.class, () -> service.findAllTransferenciasByDataCriacaoBetweenAndNomeOperadorTransacao("Beltrano", dataInicio, dataFim));
    }


    private void startTransferencia() {
        service = new TransferenciaService(repo);
    }
}
