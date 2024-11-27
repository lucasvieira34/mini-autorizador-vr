package br.com.lucas.vieira.miniautorizadorvr.service;

import br.com.lucas.vieira.miniautorizadorvr.domain.service.TransacaoService;
import br.com.lucas.vieira.miniautorizadorvr.domain.dto.TransacaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.entity.Cartao;
import br.com.lucas.vieira.miniautorizadorvr.domain.enumeration.ErroTransacao;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.TransacaoException;
import br.com.lucas.vieira.miniautorizadorvr.infrastructure.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransacaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private TransacaoService transacaoService;

    private TransacaoRequestDto request;
    private Cartao cartao;

    @BeforeEach
    void setUp() {
        request = TransacaoRequestDto.builder()
                .numeroCartao("6549873025634501")
                .senhaCartao("1234")
                .valor(100.00)
                .build();

        cartao = Cartao.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .saldo(500.00)
                .build();
    }

    @Test
    void autorizarTransacaoComSucesso() {
        when(cartaoRepository.findByNumeroCartaoAndLock(request.getNumeroCartao()))
                .thenReturn(Optional.of(cartao));

        transacaoService.autorizarTransacao(request);

        assertEquals(400.00, cartao.getSaldo());
        verify(cartaoRepository, times(1)).save(cartao);
    }

    @Test
    void autorizarTransacaoCartaoInexistente() {
        when(cartaoRepository.findByNumeroCartaoAndLock(request.getNumeroCartao()))
                .thenReturn(Optional.empty());

        TransacaoException exception = assertThrows(TransacaoException.class,
                () -> transacaoService.autorizarTransacao(request));

        assertEquals(ErroTransacao.CARTAO_INEXISTENTE, exception.getErro());
        verify(cartaoRepository, never()).save(any());
    }

    @Test
    void autorizarTransacaoSenhaInvalida() {
        cartao.setSenha("4321");
        when(cartaoRepository.findByNumeroCartaoAndLock(request.getNumeroCartao()))
                .thenReturn(Optional.of(cartao));

        TransacaoException exception = assertThrows(TransacaoException.class,
                () -> transacaoService.autorizarTransacao(request));

        assertEquals(ErroTransacao.SENHA_INVALIDA, exception.getErro());
        verify(cartaoRepository, never()).save(any());
    }

    @Test
    void autorizarTransacaoSaldoInsuficiente() {
        cartao.setSaldo(50.00);
        when(cartaoRepository.findByNumeroCartaoAndLock(request.getNumeroCartao()))
                .thenReturn(Optional.of(cartao));

        TransacaoException exception = assertThrows(TransacaoException.class,
                () -> transacaoService.autorizarTransacao(request));

        assertEquals(ErroTransacao.SALDO_INSUFICIENTE, exception.getErro());
        verify(cartaoRepository, never()).save(any());
    }
}
