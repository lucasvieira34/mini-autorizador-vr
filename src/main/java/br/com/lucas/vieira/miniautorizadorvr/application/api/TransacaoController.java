package br.com.lucas.vieira.miniautorizadorvr.application.api;

import br.com.lucas.vieira.miniautorizadorvr.domain.dto.TransacaoRequestDto;
import br.com.lucas.vieira.miniautorizadorvr.domain.exceptions.TransacaoException;
import br.com.lucas.vieira.miniautorizadorvr.domain.service.TransacaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transacoes")
@RequiredArgsConstructor
public class TransacaoController {
    private final TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<String> realizarTransacao(@RequestBody TransacaoRequestDto request) {
        try {
            this.transacaoService.autorizarTransacao(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("OK");
        } catch (TransacaoException e) {
            return ResponseEntity.unprocessableEntity().body(e.getErro().name());
        }
    }
}
