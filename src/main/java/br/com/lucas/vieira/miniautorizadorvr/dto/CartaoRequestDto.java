package br.com.lucas.vieira.miniautorizadorvr.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartaoRequestDto {
    private String numeroCartao;
    private String senha;
}
