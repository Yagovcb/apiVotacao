package br.com.yagovcb.apivotacao.service;

import br.com.yagovcb.apivotacao.model.Associado;
import br.com.yagovcb.apivotacao.repository.AssociadoRepository;
import br.com.yagovcb.apivotacao.service.dto.AssociadoDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import br.com.yagovcb.apivotacao.util.AssociadoUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *  {@link Service} controller para gerenciar as  ações do controller {@link br.com.yagovcb.apivotacao.controller.AssociadoController}.
 *
 *  @author Yago Castelo Branco
 *
 *  @since 06/08/2021
 * */
@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class AssociadoService {

    @Autowired
    private AssociadoRepository repository;

    private final ModelMapper modelMapper = new ModelMapper();

    /**
     * Método responsavel por salvar um {@link Associado} especifico, dado seu {@link AssociadoDTO}
     *
     * @param dto entidade {@link AssociadoDTO} que será persistida
     *
     * @throws MethodNotAllowedException Caso não seja possivel salvar o {@link Associado}
     * @return um {@link Associado} já persistido no banco
     * */
    @Transactional
    public Associado cadastrarAssociado(AssociadoDTO dto) {
        Associado associado = modelMapper.map(AssociadoUtil.validaCPFAssociado(dto), Associado.class);
        try {
            log.info("Associado - Iniciando o processo de persistencia de um novo Associado");
            return repository.save(associado);
        } catch (MethodNotAllowedException exception) {
            log.info("Associado - Não foi possivel persistir o novo associado, erro: " + exception);
            throw new MethodNotAllowedException("Não foi possivel persistir o novo associado!");
        } finally {
            log.info("Associado - Associado cadastrado com sucesso! " + associado.toString());
        }
    }
}
