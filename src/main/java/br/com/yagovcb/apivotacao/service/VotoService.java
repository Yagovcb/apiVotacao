package br.com.yagovcb.apivotacao.service;

import br.com.yagovcb.apivotacao.model.Associado;
import br.com.yagovcb.apivotacao.model.Votacao;
import br.com.yagovcb.apivotacao.model.Voto;
import br.com.yagovcb.apivotacao.repository.AssociadoRepository;
import br.com.yagovcb.apivotacao.repository.VotacaoRepository;
import br.com.yagovcb.apivotacao.repository.VotoRepository;
import br.com.yagovcb.apivotacao.service.dto.VotoDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import br.com.yagovcb.apivotacao.util.VotoUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  {@link Service} controller para gerenciar as  ações do controller {@link br.com.yagovcb.apivotacao.controller.VotoController}.
 *
 *  @author Yago Castelo Branco
 *
 *  @since 06/08/2021
 * */
@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class VotoService {

    private static final String URL = "https://user-info.herokuapp.com/users/";

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private AssociadoRepository associadoRepository;

    /**
     * Método responsavel por salvar um {@link Voto} especifico, dado seu {@link VotoDTO}
     *
     * @param dto entidade {@link VotoDTO} que será persistida
     *
     * @throws MethodNotAllowedException Caso não seja possivel salvar o produto
     * @return um {@link Voto} já persistido no banco
     * */
    public Voto votar(VotoDTO dto) throws ExecutionException, InterruptedException {
        Optional<Votacao> votacao = votacaoRepository.findByEmVotacao(Boolean.TRUE);
        Associado associado = associadoRepository.findByCpf(dto.getCpfAssociado());
        Voto voto = new Voto();
        montaEntidadeVotoSePermissaoTrue(dto, votacao, associado, voto);
        try {
            log.info("Voto - Iniciando o processo de voto");
            voto = votoRepository.save(voto);
        } catch (MethodNotAllowedException e){
            log.info("Voto - Um erro ocorreu ao tentar salvar o voto, erro: " + e);
            throw new MethodNotAllowedException("Não foi possivel salvar o voto!");
        } finally {
            log.info("Voto - voto cadastrado com sucesso!");
        }
        return voto;
    }

    /**
     * Método responsavel por salvar uma entidade {@link Voto} especifico, se for concedida
     * a permissão com base nos parametros
     *
     * @param dto entidade {@link VotoDTO} que será persistida
     * @param votacao entidade Optional da entidade {@link Votacao}
     * @param associado entidade que repressenta o {@link Associado} que será acoplado ao {@link Voto}
     * @param voto entidade {@link Voto} que será montada conforme as regras
     *
     * @throws MethodNotAllowedException Caso não seja possivel salvar o produto
     * @return um {@link Voto} já persistido no banco
     * */
    private Voto montaEntidadeVotoSePermissaoTrue(VotoDTO dto, Optional<Votacao> votacao, Associado associado, Voto voto) throws ExecutionException, InterruptedException {
        if (VotoUtil.validarVoto(dto) && !verificaAssociadoJaVotou(associado, votacao) || verificaPermissaoVotacao(dto.getCpfAssociado()).get()){
            voto.setAssociado(associado);
            voto.setVoto(VotoUtil.defineVoto(dto.getVotoSimNao()));
            associaVotoAVotacao(votacao, voto);
            return voto;
        } else if (verificaAssociadoJaVotou(associado, votacao)) {
            throw new MethodNotAllowedException("Associado já votou nessa votação!");
        } else {
            log.info("Voto - Associado não possui permissao para votar!");
            throw new MethodNotAllowedException("Associado não possui permissao para votar!");
        }
    }

    /**
     * Método responsavel por associar o {@link Voto} á {@link Votacao}
     * @param votacao entidade que será montada
     * @param voto entidade que que será associada à {@link Votacao}
     * @throws NoSuchElementException caso não haja uma votação
     *
     * */
    private void associaVotoAVotacao(Optional<Votacao> votacao, Voto voto) {
        if (Objects.nonNull(votacao)){
            Votacao v = votacao.isPresent() ? votacao.get() : new Votacao();
            v.setVotos(new ArrayList<>());
            v.getVotos().add(voto);
            v.setVotosSim(voto.getVoto().equals(VotoUtil.SIM) ? 1 : 0);
            v.setVotosNao(voto.getVoto().equals(VotoUtil.NAO) ? 1 : 0);
            votacaoRepository.save(v);
        } else {
            throw new NoSuchElementException("Não há nenhuma votação em aberto!");
        }
    }

    /**
     * Método responsavel por verificar se o {@link Associado} tem permissão para prosseguir á {@link Votacao}
     * dado seu cpf
     *
     * @param cpf do {@link Associado} para verificação
     *
     * @return TRUE se a permissão for concedida
     * */
    @Async
    public CompletableFuture<Boolean> verificaPermissaoVotacao(String cpf){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resposta = restTemplate.getForEntity(URL, String.class, cpf);
        boolean podeVotar = Objects.nonNull(resposta.getBody()) ? resposta.getBody().equals(VotoUtil.PODE_VOTAR) : Boolean.FALSE;
        return CompletableFuture.completedFuture(resposta.hasBody() && podeVotar);
    }

    /**
     * Método responsavel por verificar se o {@link Associado} ja votou na {@link Votacao} vigente
     *
     * @param associado que será verificado
     * @param votacao entidade que representa a votação vigente
     *
     * @return TRUE se o {@link Associado} ja estiver votado
     * */
    private boolean verificaAssociadoJaVotou(Associado associado, Optional<Votacao> votacao){
        AtomicBoolean resposta = new AtomicBoolean(false);
        if (votacao.isPresent()){
            List<Voto> votosList = votacao.get().getVotos();
            votosList.forEach(voto -> resposta.set(voto.getAssociado().equals(associado)));
        }
        return resposta.get();
    }
}
