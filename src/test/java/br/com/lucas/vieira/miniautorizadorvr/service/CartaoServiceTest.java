package br.com.lucas.vieira.miniautorizadorvr.service;

import br.com.lucas.vieira.miniautorizadorvr.dto.CartaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.dto.CartaoResponseDto;
import br.com.lucas.vieira.miniautorizadorvr.entity.Cartao;
import br.com.lucas.vieira.miniautorizadorvr.exceptions.CartaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void deveCriarCartaoComSaldoInicialDe500() {
        when(cartaoRepository.existsByNumeroCartao(request.getNumeroCartao())).thenReturn(false);

        CartaoResponseDto response = cartaoService.criarCartao(request);

        assertEquals(request.getNumeroCartao(), response.getNumeroCartao());
        assertEquals(request.getSenha(), response.getSenha());
        verify(cartaoRepository, times(1)).save(any(Cartao.class));
    }

    @Test
    void naoDeveCriarCartaoSeJaExistir() {
        when(cartaoRepository.existsByNumeroCartao(request.getNumeroCartao())).thenReturn(true);

        CartaoExistenteException exception = assertThrows(CartaoExistenteException.class, () -> {
            cartaoService.criarCartao(request);
        });

        assertEquals(request, exception.getCartaoRequest());
        verify(cartaoRepository, times(0)).save(any(Cartao.class));
    }
}
