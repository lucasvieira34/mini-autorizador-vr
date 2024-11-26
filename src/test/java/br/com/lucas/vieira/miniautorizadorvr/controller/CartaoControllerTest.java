package br.com.lucas.vieira.miniautorizadorvr.controller;

import br.com.lucas.vieira.miniautorizadorvr.dto.CartaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.dto.CartaoResponseDto;
import br.com.lucas.vieira.miniautorizadorvr.exceptions.CartaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.service.CartaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class CartaoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CartaoService cartaoService;

    @InjectMocks
    private CartaoController cartaoController;

    private CartaoRequestDto request;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(cartaoController).build();
        request = CartaoRequestDto.builder()
                .numeroCartao("6549873025634501")
                .senha("1234")
                .build();
    }

    @Test
    void criacaoCartaoComSucesso() throws Exception {
        when(cartaoService.criarCartao(any(CartaoRequestDto.class))).thenReturn(
                CartaoResponseDto.builder().numeroCartao(request.getNumeroCartao()).senha(request.getSenha()).build()
        );

        mockMvc.perform(post("/cartoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\": \"6549873025634501\", \"senha\": \"1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"numeroCartao\":\"6549873025634501\",\"senha\":\"1234\"}"));

        verify(cartaoService, times(1)).criarCartao(any(CartaoRequestDto.class));
    }

    @Test
    void exceptionCasoCartaoExista() throws Exception {
        when(cartaoService.criarCartao(any(CartaoRequestDto.class))).thenThrow(
                new CartaoExistenteException(request)
        );

        mockMvc.perform(post("/cartoes")
                        .contentType("application/json")
                        .content("{\"numeroCartao\": \"6549873025634501\", \"senha\": \"1234\"}"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().json("{\"numeroCartao\":\"6549873025634501\",\"senha\":\"1234\"}"));

        verify(cartaoService, times(1)).criarCartao(any(CartaoRequestDto.class));
    }
}

