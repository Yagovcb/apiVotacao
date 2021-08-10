package br.com.yagovcb.apivotacao.service;

import br.com.yagovcb.apivotacao.model.Pauta;
import br.com.yagovcb.apivotacao.repository.PautaRepository;
import br.com.yagovcb.apivotacao.service.dto.PautaDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import br.com.yagovcb.apivotacao.util.PautaUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  {@link Service} controller para gerenciar as  ações do controller {@link br.com.yagovcb.apivotacao.controller.PautaController}.
 *
 *  @author Yago Castelo Branco
 *
 *  @since 06/08/2021
 * */
@Slf4j
@Service
@AllArgsConstructor
@NoArgsConstructor
public class PautaService {

    @Autowired
    private PautaRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Método responsavel por salvar uma {@link Pauta} especifico, dado seu {@link PautaDTO}
     *
     * @param pautaDTO entidade {@link PautaDTO} que será persistida
     *
     * @throws MethodNotAllowedException Caso não seja possivel salvar a {@link Pauta}
     * @return uma {@link Pauta} já persistido no banco
     * */
    @Transactional
    public Pauta salvarPauta(PautaDTO pautaDTO){
        Pauta pauta = modelMapper.map(pautaDTO, Pauta.class);
        try {
            log.info("Pauta - Iniciando o processo de persistencia de uma nova Pauta");
            return PautaUtil.validaQuestionamentoNulo(pautaDTO.getQuestionamento()) ? repository.save(pauta) : null;
        } catch (MethodNotAllowedException exception) {
            throw new MethodNotAllowedException("Não foi possivel salvar o produto, erro: " + exception);
        } finally {
            log.info("Pauta - Pauta criada: " + pauta.toString());
        }
    }


    /**
     * Método responsavel por verificar se pauta ja existe
     * @param pauta para verificacao
     * @return TRUE se ja existir
     * */
    private boolean verificaPautaJaCadastrada(Pauta pauta) {
        return repository.existsByQuestionamento(pauta.getQuestionamento());
    }

}
