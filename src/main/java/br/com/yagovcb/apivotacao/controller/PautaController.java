package br.com.yagovcb.apivotacao.controller;

import br.com.yagovcb.apivotacao.model.Pauta;
import br.com.yagovcb.apivotacao.service.PautaService;
import br.com.yagovcb.apivotacao.service.dto.PautaDTO;
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

/**
 *  REST controller para gerenciar {@link Pauta}.
 *  Criado por Yago Castelo Branco
 *
 * @since 06/08/2021
 * */
@Slf4j
@RepositoryRestController
@RequestMapping("/pauta")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService service;

    /**
     * {@code POST /criar_pauta} : Rest Endpoint de Criação do {@link Pauta}
     * @param pautaDTO passado no corpo da requisição
     * @return the {@link ResponseEntity} com o status {@code 201 (CREATED)} e a entidade {@link Pauta} criada
     * */
    @ApiOperation(value = "Endpoint de criação de uma Pauta. Recebe um questionamento no corpo da requisição")
    @PostMapping(path = "/criar_pauta", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pauta> createPauta(@RequestBody PautaDTO pautaDTO) {
        log.info( "PautaController: Criando uma nova Pauta" + pautaDTO );
        return new ResponseEntity<>(service.salvarPauta( pautaDTO ), HttpStatus.CREATED);
    }

}
