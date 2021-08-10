package br.com.yagovcb.apivotacao.controller;

import br.com.yagovcb.apivotacao.model.Votacao;
import br.com.yagovcb.apivotacao.model.Voto;
import br.com.yagovcb.apivotacao.service.VotoService;
import br.com.yagovcb.apivotacao.service.dto.VotoDTO;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.concurrent.ExecutionException;

/**
 *  REST controller para gerenciar {@link Votacao}.
 *  Criado por Yago Castelo Branco
 *
 * @since 06/08/2021
 * */
@Slf4j
@RepositoryRestController
@RequestMapping("/voto")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService service;

    /**
     * {@code POST /votar} : Rest Endpoint de Criação do {@link Voto}
     * @param votoDTO passado no corpo da requisição
     * @return the {@link ResponseEntity} com o status {@code 200 (OK)} e a entidade {@link Voto} criada
     * */
    @ApiOperation(value = "Endpoint de criação de um Voto. Obrigatorio passar o CPF do associado e o voto na requisição")
    @PostMapping(path = "/votar", produces = MediaType.APPLICATION_JSON_VALUE)

    public ResponseEntity<Voto> createVoto(@RequestBody VotoDTO votoDTO) throws ExecutionException, InterruptedException {
        log.info("VotoController - Gerando um novo Voto para a pauta");
        return new ResponseEntity<>(service.votar(votoDTO), HttpStatus.OK);
    }
}
