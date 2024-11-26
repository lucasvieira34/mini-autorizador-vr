package br.com.lucas.vieira.miniautorizadorvr.controller;

import br.com.lucas.vieira.miniautorizadorvr.dto.CartaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.exceptions.CartaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.exceptions.CartaoNaoEncontradoException;
import br.com.lucas.vieira.miniautorizadorvr.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<?> obterSaldo(@PathVariable String numeroCartao) {
        try {
            Double saldo = this.cartaoService.obterSaldo(numeroCartao);
            return ResponseEntity.ok(saldo);
        } catch (CartaoNaoEncontradoException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

