package br.com.yagovcb.apivotacao.service.dto;

import br.com.yagovcb.apivotacao.model.Associado;
import lombok.Data;

/**
 *  Classe DTO para padronização das requisições REST
 *  quando houvesse a necessidade de usar a entidade {@link Associado}
 *
 *  @author Yago Castelo Branco
 *
 *  @since 06/08/2021
 * */
@Data
public class AssociadoDTO {

    private String cpf;

    private String nome;
}
