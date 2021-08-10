package br.com.yagovcb.apivotacao.util;

import br.com.yagovcb.apivotacao.model.Pauta;
import br.com.yagovcb.apivotacao.service.exception.BadRequestException;
import java.util.Objects;

/**
 *  Classe de Métodos auxiliares
 *
 *  Criado por Yago Castelo Branco
 *
 * @since 29/07/2021
 * */
public class PautaUtil {


    /**
     * Construtor padrão vazio
     *
     * */
    private PautaUtil(){}


    /**
     * Método auxiliar responsavel por verificar se o atributo questionamento da entidade {@link Pauta} está nulo
     * ou se esta com um tamanho maior que 15 caracteres
     *
     * @param pauta entidade para verificação
     * @throws BadRequestException caso o atributo esteja nulo
     * @return true se o atributo estiver preenchido
     */
    public static boolean validaQuestionamentoNulo(String pauta) {
        if (Objects.isNull(pauta) || pauta.length() < 15){
            throw new BadRequestException("Você precisa escrever um questionamento valido para cadastrar uma Pauta");
        }
        return true;
    }
}
