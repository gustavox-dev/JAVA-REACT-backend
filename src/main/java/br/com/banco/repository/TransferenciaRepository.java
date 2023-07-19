package br.com.banco.repository;

import br.com.banco.entities.Conta;
import br.com.banco.entities.Transferencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransferenciaRepository extends JpaRepository<Transferencia, Long> {

    @Query("SELECT t FROM Transferencia t INNER JOIN Conta c ON t.conta.id = c.id WHERE c.id = :idConta")
    List<Transferencia> findByContaId(Long idConta);
    List<Transferencia> findByDataTransferenciaBetween(LocalDate dataInicio, LocalDate dataFim);
    List<Transferencia> findByNomeOperadorTransacao(String nomeOperadorTransacao);
    @Query("SELECT t FROM Transferencia t WHERE t.nomeOperadorTransacao = :nomeOperadorTransacao AND t.dataTransferencia BETWEEN :dataInicio AND :dataFim")
    List<Transferencia> findAllTransferenciasByDataTransferenciaBetweenAndNomeOperadorTransacao(String nomeOperadorTransacao, LocalDate dataInicio, LocalDate dataFim);

}
