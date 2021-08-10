package br.com.yagovcb.apivotacao.service;

import br.com.yagovcb.apivotacao.model.Pauta;
import br.com.yagovcb.apivotacao.model.Votacao;
import br.com.yagovcb.apivotacao.repository.PautaRepository;
import br.com.yagovcb.apivotacao.repository.VotacaoRepository;
import br.com.yagovcb.apivotacao.service.dto.DetalheRespostaDTO;
import br.com.yagovcb.apivotacao.service.dto.VotacaoDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import br.com.yagovcb.apivotacao.util.VotacaoUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 *  {@link Service} controller para gerenciar as  ações do controller {@link br.com.yagovcb.apivotacao.controller.VotacaoController}.
 *
 *  @author Yago Castelo Branco
 *
 *  @since 06/08/2021
 * */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class VotacaoService {

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    /**
     * Método responsavel por salvar uma {@link Votacao} especifico, dado o id da entidade {@link Pauta}
     * @throws MethodNotAllowedException Caso não seja possivel salvar a {@link Votacao}
     * @return uma {@link Votacao} já persistido no banco
     * */
    @Transactional
    public Votacao salvarVotacao(VotacaoDTO votacaoDTO) {
        log.info("Votação - Iniciando o processo de persistencia de uma nova Votacao");
        Votacao votacao = new Votacao();
        if (VotacaoUtil.validaIdPautaNulo(votacaoDTO.getIdPauta()) && verificaVotacaoAberta()) {
            Optional<Pauta> p = pautaRepository.findById(votacaoDTO.getIdPauta());
            votacao.setPauta(p.orElse(null));
            votacao.setVotosSim(0);
            votacao.setVotosNao(0);
            votacao.setVotos(new ArrayList<>());
            try {
                log.info("Votação - Abrindo a votação");
                votacao.setEmVotacao(Boolean.TRUE);
                return votacaoRepository.save(votacao);
            } catch (MethodNotAllowedException e) {
                log.info("Votação - Um erro aconteceu durante a persistencia da votação" + e);
                throw new MethodNotAllowedException("Um erro aconteceu durante a persistencia da votação");
            } finally {
                log.info("Votação - Votação cadastrada com sucesso!");
            }
        }
        return null;
    }

    /**
     * Método responsavel por abrir uma {@link Votacao} especifico, dado o id da entidade {@link Votacao} e o tempo
     * que essa votação deverá permanecer aberta
     * @throws InterruptedException Caso não seja possivel abrir a {@link Votacao}
     * @throws MethodNotAllowedException Caso não exista nenhuma votação dado o id
     * @return uma {@link Votacao} aberta com base no tempo
     * */
    @Async
    @Transactional
    public CompletableFuture<DetalheRespostaDTO> abrirVotacao(VotacaoDTO votacaoDTO) {
        int tempoVotacao = (votacaoDTO.getTempo() >= 1 ? votacaoDTO.getTempo() : 1);
        Votacao votacao = votacaoRepository.findById(votacaoDTO.getIdVotacao()).orElse(null);
        return abreVotacao(votacaoDTO, tempoVotacao, votacao);
    }


    private static CompletableFuture<DetalheRespostaDTO> abreVotacao(VotacaoDTO votacaoDTO, int tempoVotacao, Votacao votacao){
        DetalheRespostaDTO respostaDTO = new DetalheRespostaDTO();
        return CompletableFuture.supplyAsync(() -> {
                try {
                    log.info("Votação - Votação ficara aberta durante " + votacaoDTO.getTempo() + " minutos");
                    TimeUnit.MINUTES.sleep(tempoVotacao);
                    respostaDTO.setMensagem("Votaçâo aberta com sucesso! Ficará aberta durante" + tempoVotacao + " minuto(s)");
                    respostaDTO.setStatus(200L);
                } catch (InterruptedException ex) {
                    throw new IllegalStateException("Não foi possivel continuar com a votação, erro: " + ex);
                } finally {
                    VotacaoUtil.montaEntidadeVotacaoFinal(votacao);
                    log.info("Votação - Votação  finalizada! Foi votada a Pauta:  '" + votacao.getPauta().getQuestionamento() + "', tendo um total de " + (votacao.getVotosSim() + votacao.getVotosNao()) + " votos. Sendo " + votacao.getVotosSim() + " associados a favor, e " + votacao.getVotosNao() + " contra");
                }
                return respostaDTO;
            });
    }

    /**
     * Método responsavel por verificar se existe alguma votação aberta
     * @return TRUE se não existir uma votação aberta
     * */
    private boolean verificaVotacaoAberta() {
        return Objects.isNull(votacaoRepository.findByEmVotacao(Boolean.TRUE)) || votacaoRepository.findByEmVotacao(Boolean.TRUE).isEmpty();
    }

}
