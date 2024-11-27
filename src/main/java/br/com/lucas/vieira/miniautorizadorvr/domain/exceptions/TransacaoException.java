package br.com.lucas.vieira.miniautorizadorvr.domain.exceptions;

import br.com.lucas.vieira.miniautorizadorvr.domain.enumeration.ErroTransacao;
import lombok.Getter;

@Getter
public class TransacaoException extends RuntimeException {
    private final ErroTransacao erro;

    public TransacaoException(ErroTransacao erro) {
        super(erro.getMensagem());
        this.erro = erro;
    }

}
