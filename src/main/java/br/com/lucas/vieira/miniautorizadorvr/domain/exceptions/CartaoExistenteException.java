package br.com.lucas.vieira.miniautorizadorvr.domain.exceptions;

import br.com.lucas.vieira.miniautorizadorvr.domain.dto.CartaoRequestDto;

public class CartaoExistenteException extends RuntimeException {
    private final CartaoRequestDto request;

    public CartaoExistenteException(CartaoRequestDto request) {
        super("Cartão já existente.");
        this.request = request;
    }

    public CartaoRequestDto getCartaoRequest() {
        return request;
    }
}

