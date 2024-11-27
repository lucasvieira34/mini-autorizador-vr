package br.com.lucas.vieira.miniautorizadorvr.application.api;

import br.com.lucas.vieira.miniautorizadorvr.domain.dto.CartaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.CartaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.CartaoNaoExistenteException;
import br.com.lucas.vieira.miniautorizadorvr.domain.service.CartaoService;
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
        } catch (CartaoNaoExistenteException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}

