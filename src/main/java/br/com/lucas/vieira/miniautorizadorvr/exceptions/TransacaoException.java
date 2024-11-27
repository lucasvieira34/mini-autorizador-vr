package br.com.lucas.vieira.miniautorizadorvr.exceptions;

import br.com.lucas.vieira.miniautorizadorvr.enumeration.ErroTransacao;
import lombok.Getter;

@Getter
public class TransacaoException extends RuntimeException {
    private final ErroTransacao erro;

    public TransacaoException(ErroTransacao erro) {
        super(erro.getMensagem());
        this.erro = erro;
    }

}
