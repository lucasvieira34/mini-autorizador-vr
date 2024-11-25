package br.com.lucas.vieira.miniautorizadorvr.repository;

import br.com.lucas.vieira.miniautorizadorvr.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
}
