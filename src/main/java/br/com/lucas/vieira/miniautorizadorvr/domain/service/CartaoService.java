package br.com.lucas.vieira.miniautorizadorvr.domain.service;

import br.com.lucas.vieira.miniautorizadorvr.domain.dto.CartaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.dto.CartaoResponseDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.entity.Cartao;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.CartaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.CartaoNaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.infrastructure.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final CartaoRepository cartaoRepository;

    public CartaoResponseDto criarCartao(CartaoRequestDto request) {
        if (this.cartaoRepository.existsByNumeroCartao(request.getNumeroCartao())) {
            throw new CartaoExistenteException(request);
        }
        Cartao cartao = Cartao.builder()
                .numeroCartao(request.getNumeroCartao())
                .senha(request.getSenha())
                .saldo(500.00)
                .build();
        this.cartaoRepository.save(cartao);

        return CartaoResponseDto.builder()
                .numeroCartao(request.getNumeroCartao())
                .senha(request.getSenha())
                .build();
    }

    public Double obterSaldo(String numeroCartao) {
        Cartao cartao = this.cartaoRepository.findByNumeroCartao(numeroCartao)
                .orElseThrow(() -> new CartaoNaoExistenteException(numeroCartao));
        return cartao.getSaldo();
    }
}
