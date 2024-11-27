package br.com.lucas.vieira.miniautorizadorvr.service;

import br.com.lucas.vieira.miniautorizadorvr.dto.TransacaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.entity.Cartao;
import br.com.lucas.vieira.miniautorizadorvr.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ConcorrenciaTransacaoServiceTest {

    private CartaoRepository cartaoRepository;
    private TransacaoService transacaoService;

    private Cartao cartao;
    private TransacaoRequestDto request;

    @BeforeEach
    void setUp() {
        cartaoRepository = mock(CartaoRepository.class);
        transacaoService = new TransacaoService(cartaoRepository);

        cartao = Cartao.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .saldo(10.00)
                .build();

        request = TransacaoRequestDto.builder()
                .numeroCartao("6549873025634501")
                .senhaCartao("1234")
                .valor(10.00)
                .build();
    }

    @Test
    void testarConcorrenciaTransacoesSimultaneas() throws Exception {
        when(cartaoRepository.findByNumeroCartaoAndLock(request.getNumeroCartao()))
                .thenAnswer(invocation -> {
                    synchronized (this) {
                        Thread.sleep(100);
                        return Optional.of(cartao);
                    }
                });

        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<?> transacao1 = executor.submit(() -> {
            try {
                transacaoService.autorizarTransacao(request);
            } catch (Exception e) {
                return e;
            }
            return null;
        });

        Future<?> transacao2 = executor.submit(() -> {
            try {
                transacaoService.autorizarTransacao(request);
            } catch (Exception e) {
                return e;
            }
            return null;
        });

        Object resultado1 = transacao1.get();
        Object resultado2 = transacao2.get();

        int transacoesComErro = 0;
        if (resultado1 instanceof Exception) transacoesComErro++;
        if (resultado2 instanceof Exception) transacoesComErro++;

        assertEquals(1, transacoesComErro, "Uma transação deve falhar devido ao saldo insuficiente");

        assertEquals(0.00, cartao.getSaldo(), "O saldo do cartão deve ser reduzido corretamente");

        verify(cartaoRepository, times(1)).save(cartao);
    }
}
