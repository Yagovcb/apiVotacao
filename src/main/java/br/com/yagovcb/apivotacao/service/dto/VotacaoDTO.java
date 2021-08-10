package br.com.yagovcb.apivotacao.service.dto;

import br.com.yagovcb.apivotacao.model.Votacao;
import lombok.Data;

/**
 *  Classe DTO para padronização das requisições REST
 *  quando houvesse a necessidade de usar a entidade {@link Votacao}
 *
 *  @author Yago Castelo Branco
 *
 *  @since 06/08/2021
 * */
@Data
public class VotacaoDTO {

    private Integer idVotacao;
    private Integer idPauta;
    private Integer tempo;
}
