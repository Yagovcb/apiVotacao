package br.com.yagovcb.apivotacao.controller;

import br.com.yagovcb.apivotacao.model.Votacao;
import br.com.yagovcb.apivotacao.service.VotacaoService;
import br.com.yagovcb.apivotacao.service.dto.DetalheRespostaDTO;
import br.com.yagovcb.apivotacao.service.dto.VotacaoDTO;
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
@RequestMapping("/votacao")
@RequiredArgsConstructor
public class VotacaoController {

    private final VotacaoService service;

    /**
     * {@code POST /criar_votacao} : Rest Endpoint de Criação do {@link Votacao}
     * @param votacaoDTO passado no corpo da requisição
     * @return the {@link ResponseEntity} com o status {@code 201 (CREATED)} e a entidade {@link Votacao} criada
     * */
    @ApiOperation(value = "Endpoint de criação de uma Votação. Obrigatorio passar o id da Pauta que precisa ser criada previamente")
    @PostMapping(path = "/criar_votacao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Votacao> createVotacao(@RequestBody VotacaoDTO votacaoDTO){
        log.info( "VotacaoController -  Criando uma nova votação com base na pauta: " + votacaoDTO.getIdPauta() );
        return new ResponseEntity<>(service.salvarVotacao(votacaoDTO), HttpStatus.CREATED);
    }

    /**
     * {@code POST /abrir_votacao} : Rest Endpoint para abrir uma {@link Votacao}
     * @param votacaoDTO passado no corpo da requisição
     * @return the {@link ResponseEntity} com o status {@code 201 (CREATED)} e a entidade {@link Votacao} criada
     * */
    @ApiOperation(value = "Endpoint de criação de uma Votação. Obrigatorio passar o id da Votacao criada e o tempo que ficará aberta")
    @PostMapping(path = "/abrir_votacao", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DetalheRespostaDTO> abrirVotacao(@RequestBody VotacaoDTO votacaoDTO) throws InterruptedException, ExecutionException {
        log.info( "VotacaoController -  Abrindo a votação: " + votacaoDTO.getIdVotacao());
        return new ResponseEntity<>(service.abrirVotacao(votacaoDTO).get(), HttpStatus.OK);
    }

}
