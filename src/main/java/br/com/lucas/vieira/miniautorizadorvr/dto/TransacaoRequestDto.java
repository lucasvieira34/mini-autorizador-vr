package br.com.lucas.vieira.miniautorizadorvr.dto;

import lombok.Data;

@Data
public class TransacaoRequestDto {
    private String numeroCartao;
    private String senhaCartao;
    private Double valor;
}