package br.com.yagovcb.apivotacao.util;

import br.com.yagovcb.apivotacao.model.Votacao;
import br.com.yagovcb.apivotacao.model.Voto;
import java.util.List;
import java.util.Objects;

/**
 *  Classe de Métodos auxiliares
 *
 *  Criado por Yago Castelo Branco
 *
 * @since 29/07/2021
 * */
public class VotacaoUtil {

    /**
     * Construtor padrão vazio
     *
     * */
    public VotacaoUtil() { }


    /**
     * Método auxiliar responsavel por verificar atributos da votacao antes de liberar a persistencia
     * @param idPauta atributo para verificação
     * @throws NullPointerException caso o atributo esteja nulo
     *
     * @return true se o atributo estiver preenchido
     */
    public static boolean validaIdPautaNulo(Integer idPauta) {
        return !Objects.isNull(idPauta);
    }

    /**
     * Método responsavel por montar a entidade final de {@link Votacao} após o encerramento da da votação
     * @param votacao entidade que será montada
     * @return {@link Votacao} montada
     */
    public static Votacao montaEntidadeVotacaoFinal(Votacao votacao) {
        votacao.setEmVotacao(Boolean.FALSE);
        List<Voto> votos = votacao.getVotos();
        votacao.setVotos(votos);
        votos.forEach(v -> {
            if (v.getVoto().equals(VotoUtil.SIM))
                votacao.setVotosSim(1);
            else
                votacao.setVotosNao(1);
        });
        return votacao;
    }
}
