package br.com.lucas.vieira.miniautorizadorvr.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartaoResponseDto {
    private String numeroCartao;
    private String senha;
}