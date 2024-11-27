package br.com.lucas.vieira.miniautorizadorvr.domain.enumeration;

public enum ErroTransacao {
    SALDO_INSUFICIENTE("Saldo insuficiente"),
    SENHA_INVALIDA("Senha inválida"),
    CARTAO_INEXISTENTE("Cartão inexistente");

    private final String mensagem;

    ErroTransacao(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }
}

