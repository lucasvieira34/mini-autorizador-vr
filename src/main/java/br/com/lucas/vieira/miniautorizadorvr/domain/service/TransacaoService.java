package br.com.lucas.vieira.miniautorizadorvr.domain.service;

import br.com.lucas.vieira.miniautorizadorvr.domain.dto.TransacaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.entity.Cartao;
import br.com.lucas.vieira.miniautorizadorvr.domain.enumeration.ErroTransacao;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.TransacaoException;
import br.com.lucas.vieira.miniautorizadorvr.infrastructure.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransacaoService {
    private final CartaoRepository cartaoRepository;

    @Transactional
    public void autorizarTransacao(TransacaoRequestDto request) {
        Cartao cartao = this.cartaoRepository.findByNumeroCartaoAndLock(request.getNumeroCartao())
                .orElseThrow(() -> new TransacaoException(ErroTransacao.CARTAO_INEXISTENTE));

        if (!cartao.getSenha().equals(request.getSenhaCartao())) {
            throw new TransacaoException(ErroTransacao.SENHA_INVALIDA);
        }

        if (cartao.getSaldo() < request.getValor()) {
            throw new TransacaoException(ErroTransacao.SALDO_INSUFICIENTE);
        }

        cartao.setSaldo(cartao.getSaldo() - request.getValor());
        this.cartaoRepository.save(cartao);
    }
}
