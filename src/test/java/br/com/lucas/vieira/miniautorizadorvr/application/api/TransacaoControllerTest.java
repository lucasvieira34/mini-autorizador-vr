package br.com.lucas.vieira.miniautorizadorvr.application.api;

import br.com.lucas.vieira.miniautorizadorvr.domain.dto.TransacaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.enumeration.ErroTransacao;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.TransacaoException;
import br.com.lucas.vieira.miniautorizadorvr.domain.service.TransacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class TransacaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TransacaoService transacaoService;

    @InjectMocks
    private TransacaoController transacaoController;

    private TransacaoRequestDto request;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
        request = TransacaoRequestDto.builder()
                .numeroCartao("6549873025634501")
                .senhaCartao("1234")
                .valor(100.00)
                .build();
    }

    @Test
    void realizarTransacaoComSucesso() throws Exception {
        doNothing().when(transacaoService).autorizarTransacao(any(TransacaoRequestDto.class));

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"1234\", \"valor\": 100.00}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("OK"));

        verify(transacaoService, times(1)).autorizarTransacao(any(TransacaoRequestDto.class));
    }

    @Test
    void realizarTransacaoComErroSaldoInsuficiente() throws Exception {
        doThrow(new TransacaoException(ErroTransacao.SALDO_INSUFICIENTE))
                .when(transacaoService).autorizarTransacao(any(TransacaoRequestDto.class));

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"1234\", \"valor\": 600.00}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SALDO_INSUFICIENTE"));

        verify(transacaoService, times(1)).autorizarTransacao(any(TransacaoRequestDto.class));
    }

    @Test
    void realizarTransacaoComErroSenhaInvalida() throws Exception {
        doThrow(new TransacaoException(ErroTransacao.SENHA_INVALIDA))
                .when(transacaoService).autorizarTransacao(any(TransacaoRequestDto.class));

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\": \"6549873025634501\", \"senhaCartao\": \"4321\", \"valor\": 100.00}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("SENHA_INVALIDA"));

        verify(transacaoService, times(1)).autorizarTransacao(any(TransacaoRequestDto.class));
    }

    @Test
    void realizarTransacaoComErroCartaoInexistente() throws Exception {
        doThrow(new TransacaoException(ErroTransacao.CARTAO_INEXISTENTE))
                .when(transacaoService).autorizarTransacao(any(TransacaoRequestDto.class));

        mockMvc.perform(post("/transacoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\": \"0000000000000000\", \"senhaCartao\": \"1234\", \"valor\": 100.00}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().string("CARTAO_INEXISTENTE"));

        verify(transacaoService, times(1)).autorizarTransacao(any(TransacaoRequestDto.class));
    }
}
