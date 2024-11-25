package br.com.lucas.vieira.miniautorizadorvr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
public class Cartao {

    @Id
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo = BigDecimal.valueOf(500.00);

    @Version
    private Integer version;
}
