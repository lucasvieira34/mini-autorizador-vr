package br.com.lucas.vieira.miniautorizadorvr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Cartao {

    @Id
    private String numeroCartao;
    private String senha;
    private Double saldo;

    @Version
    private Integer version;
}
