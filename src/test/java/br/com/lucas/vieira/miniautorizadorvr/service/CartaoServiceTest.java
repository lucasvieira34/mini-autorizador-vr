package br.com.lucas.vieira.miniautorizadorvr.service;

import br.com.lucas.vieira.miniautorizadorvr.domain.service.CartaoService;
import br.com.lucas.vieira.miniautorizadorvr.domain.dto.CartaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.dto.CartaoResponseDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.entity.Cartao;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.CartaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.CartaoNaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.infrastructure.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    private CartaoRequestDto request;

    @BeforeEach
    void setUp() {
        request = CartaoRequestDto.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build();
    }


    @Test
    void criarCartaoComSaldoInicial() {
        when(cartaoRepository.existsByNumeroCartao(request.getNumeroCartao())).thenReturn(false);

        CartaoResponseDto response = cartaoService.criarCartao(request);

        assertEquals(request.getNumeroCartao(), response.getNumeroCartao());
        assertEquals(request.getSenha(), response.getSenha());
        verify(cartaoRepository, times(1)).save(any(Cartao.class));
    }

    @Test
    void exceptionCasoCartaoExistente() {
        when(cartaoRepository.existsByNumeroCartao(request.getNumeroCartao())).thenReturn(true);

        CartaoExistenteException exception = assertThrows(CartaoExistenteException.class, () -> {
            cartaoService.criarCartao(request);
        });

        assertEquals(request, exception.getCartaoRequest());
        verify(cartaoRepository, times(0)).save(any(Cartao.class));
    }

    @Test
    void obterSaldoCartaoExistente() {
        Cartao cartao = Cartao.builder()
                .numeroCartao(request.getNumeroCartao())
                .senha(request.getSenha())
                .saldo(500.00)
                .build();
        when(cartaoRepository.findByNumeroCartao(request.getNumeroCartao())).thenReturn(java.util.Optional.of(cartao));

        Double saldo = cartaoService.obterSaldo(request.getNumeroCartao());

        assertNotNull(saldo);
        assertEquals(500.00, saldo);
    }

    @Test
    void obterSaldoCartaoNaoExistente() {
        when(cartaoRepository.findByNumeroCartao(request.getNumeroCartao())).thenReturn(java.util.Optional.empty());

        assertThrows(CartaoNaoExistenteException.class, () -> {
            cartaoService.obterSaldo(request.getNumeroCartao());
        });
    }

}
