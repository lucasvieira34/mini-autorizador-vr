package br.com.lucas.vieira.miniautorizadorvr.repository;

import br.com.lucas.vieira.miniautorizadorvr.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {
    boolean existsByNumeroCartao(String numeroCartao);
    Optional<Cartao> findByNumeroCartao(String numeroCartao);

}
