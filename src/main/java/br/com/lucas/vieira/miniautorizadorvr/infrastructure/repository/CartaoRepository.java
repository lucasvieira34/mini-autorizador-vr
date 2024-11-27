package br.com.lucas.vieira.miniautorizadorvr.infrastructure.repository;

import br.com.lucas.vieira.miniautorizadorvr.domain.entity.Cartao;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {
    boolean existsByNumeroCartao(String numeroCartao);
    Optional<Cartao> findByNumeroCartao(String numeroCartao);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Cartao c WHERE c.numeroCartao = :numeroCartao")
    Optional<Cartao> findByNumeroCartaoAndLock(@Param("numeroCartao") String numeroCartao);

}
