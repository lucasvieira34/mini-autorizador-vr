package br.com.lucas.vieira.miniautorizadorvr.exceptions;

public class CartaoNaoExistenteException extends RuntimeException {
    public CartaoNaoExistenteException(String numeroCartao) {
        super("Cartão não encontrado: " + numeroCartao);
    }
}
