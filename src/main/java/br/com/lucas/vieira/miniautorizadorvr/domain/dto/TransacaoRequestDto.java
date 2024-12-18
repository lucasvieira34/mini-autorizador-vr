package br.com.lucas.vieira.miniautorizadorvr.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransacaoRequestDto {
    private String numeroCartao;
    private String senhaCartao;
    private Double valor;
}