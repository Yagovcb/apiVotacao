package br.com.yagovcb.apivotacao.util;

import br.com.yagovcb.apivotacao.service.dto.AssociadoDTO;
import br.com.yagovcb.apivotacao.service.exception.BadRequestException;

import java.util.Objects;

/**
 *  Classe de Métodos auxiliares
 *
 *  Criado por Yago Castelo Branco
 *
 * @since 02/08/2021
 * */
public class AssociadoUtil {

    /**
     * Construtor padrão vazio
     *
     * */
    public AssociadoUtil() { }

    /**
     * Metodo responsavel por validar se o cpf do {@link AssociadoDTO} foi preenchido corretamente
     * @param associadoDTO entidade onde será verificado o CPF
     * @throws BadRequestException Caso o cpf do {@link AssociadoDTO} esteja fora de acordo com a padronização necessaria
     * @return {@link AssociadoDTO} validado perante as condições
     * */
    public static AssociadoDTO validaCPFAssociado(AssociadoDTO associadoDTO){
        if (associadoDTO.getCpf().length() == 11 && (Objects.nonNull(associadoDTO.getNome()) && !associadoDTO.getNome().equals(""))) {
            return associadoDTO;
        } else {
            throw new BadRequestException("Você precisa passar um CPF valido para prosseguir com o cadastro");
        }
    }
}
