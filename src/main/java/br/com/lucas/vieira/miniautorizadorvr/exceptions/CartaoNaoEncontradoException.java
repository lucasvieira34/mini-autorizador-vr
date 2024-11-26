package br.com.lucas.vieira.miniautorizadorvr.exceptions;

public class CartaoNaoEncontradoException extends RuntimeException {
    public CartaoNaoEncontradoException(String numeroCartao) {
        super("Cartão não encontrado: " + numeroCartao);
    }
}
