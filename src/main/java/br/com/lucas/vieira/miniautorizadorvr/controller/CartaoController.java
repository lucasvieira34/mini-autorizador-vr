package br.com.lucas.vieira.miniautorizadorvr.controller;

import br.com.lucas.vieira.miniautorizadorvr.dto.CartaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.exceptions.CartaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class CartaoController {
    private final CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<?> criarCartao(@RequestBody CartaoRequestDto request) {
        try {
            this.cartaoService.criarCartao(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(request);
        } catch (CartaoExistenteException e) {
            return ResponseEntity.unprocessableEntity().body(e.getCartaoRequest());
        }

    }
}

