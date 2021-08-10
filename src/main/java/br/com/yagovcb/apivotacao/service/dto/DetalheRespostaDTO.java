package br.com.yagovcb.apivotacao.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Classe DTO para padronização das requisições REST
 *  quando houvesse a necessidade de usar a entidade {@link Exception}
 *
 *  @author Yago Castelo Branco
 *
 *  @since 06/08/2021
 * */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalheRespostaDTO {

    private String mensagem;
    private Long status;
}
