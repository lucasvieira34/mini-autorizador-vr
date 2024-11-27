package br.com.lucas.vieira.miniautorizadorvr.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {

    @Id
    private String numeroCartao;
    private String senha;
    private Double saldo;

    @Version
    private Integer version;
}
