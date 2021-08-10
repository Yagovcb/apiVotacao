package br.com.yagovcb.apivotacao.controller;

import br.com.yagovcb.apivotacao.model.Associado;
import br.com.yagovcb.apivotacao.service.AssociadoService;
import br.com.yagovcb.apivotacao.service.dto.AssociadoDTO;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *  REST controller para gerenciar {@link Associado}.
 *  Criado por Yago Castelo Branco
 *
 * @since 06/08/2021
 * */
@Slf4j
@RepositoryRestController
@RequestMapping("/associado")
@RequiredArgsConstructor
public class AssociadoController {

    private final AssociadoService service;

    /**
     * {@code POST /cadastrar} : Rest Endpoint de Criação do {@link Associado}
     * @param associadoDTO passado no corpo da requisição
     * @return the {@link ResponseEntity} com o status {@code 201 (CREATED)} e a entidade {@link Associado} criada
     * */
    @ApiOperation(value = "Endpoint de criação de um Associado. Recebe no corpo da requisição o cpf e nome do associado")
    @PostMapping(path = "/cadastrar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Associado> createAssociado(@RequestBody AssociadoDTO associadoDTO) {
        log.info( "AssociadoController -  Criando um novo associado na base:" + associadoDTO );
        return new ResponseEntity<>(service.cadastrarAssociado(associadoDTO), HttpStatus.CREATED);
    }
}
