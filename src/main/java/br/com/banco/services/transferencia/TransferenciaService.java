package br.com.banco.services.transferencia;

import br.com.banco.entities.Transferencia;
import br.com.banco.exceptions.CustomException;
import br.com.banco.http.dto.request.transferencia.TransferenciaDTO;
import br.com.banco.repository.TransferenciaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class TransferenciaService {

    private final TransferenciaRepository repo;

    public List<TransferenciaDTO> findByContaId(Long id) {
        try {
            List<Transferencia> transferencias = repo.findByContaId(id);
            return returnConvertDTO(transferencias);
        } catch (CustomException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Oooops! Parece que a busca por conta não deu muito certo... Pode tentar de novo mais tarde? 😅");
        }
    }

    public List<TransferenciaDTO> findAll() {
        try {
            List<Transferencia> transferencias = repo.findAll();
            return returnConvertDTO(transferencias);
        } catch (CustomException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Desculpe, mas não conseguimos encontrar nenhuma transferência. Talvez tenha voado para longe 🕊️");
        }
    }

    public List<TransferenciaDTO> findByDataCriacaoBetween(LocalDate dataInicio, LocalDate dataFim) {
        try {
            if (dataInicio.isAfter(dataFim)) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "A data de inicio não pode ser maior que a data de fim.");
            }
            List<Transferencia> transferencias = repo.findByDataTransferenciaBetween(dataInicio, dataFim);
            return returnConvertDTO(transferencias);
        } catch (CustomException e) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "Ops! Parece que não há transferências nesse período.");
        }
    }

    public List<TransferenciaDTO> findByNomeOperadorTransacao(String nomeOperadorTransacao) {
        try {
            List<Transferencia> transferencias = repo.findByNomeOperadorTransacao(nomeOperadorTransacao);
            return returnConvertDTO(transferencias);
        } catch (CustomException e) {
            throw new CustomException(HttpStatus.NOT_FOUND, "Que pena! Não conseguimos encontrar nenhuma transferência com esse operador.");
        }
    }

    public List<TransferenciaDTO> findAllTransferenciasByDataCriacaoBetweenAndNomeOperadorTransacao(String nomeOperadorTransacao, LocalDate dataInicio, LocalDate dataFim) {
        try {
            if (dataInicio.isAfter(dataFim)) {
                throw new CustomException(HttpStatus.BAD_REQUEST, "A data de inicio não pode ser maior que a data de fim.");
            }
            List<Transferencia> transferencias = repo.findAllTransferenciasByDataTransferenciaBetweenAndNomeOperadorTransacao(nomeOperadorTransacao, dataInicio, dataFim);
            return returnConvertDTO(transferencias);
        } catch (CustomException e) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, "Ops! Parece que as transferências sumiram... será que foram abduzidas? 👽");
        }
    }


    private List<TransferenciaDTO> returnConvertDTO(List<Transferencia> transferencias) {
        return transferencias.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TransferenciaDTO convertToDTO(Transferencia transferencia) {
        TransferenciaDTO dto = new TransferenciaDTO();
        dto.setId(transferencia.getId());
        dto.setDataTransferencia(transferencia.getDataTransferencia());
        dto.setValor(transferencia.getValor());
        dto.setTipo(transferencia.getTipo());
        dto.setNomeOperadorTransacao(transferencia.getNomeOperadorTransacao());
        dto.setContaId(transferencia.getConta().getIdConta());
        return dto;
    }
}
