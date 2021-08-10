package br.com.yagovcb.apivotacao.util;

import br.com.yagovcb.apivotacao.model.Voto;
import br.com.yagovcb.apivotacao.service.dto.VotoDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 *  Classe de Métodos auxiliares
 *
 *  Criado por Yago Castelo Branco
 *
 * @since 02/08/2021
 * */
public class VotoUtil {

    public static final String SIM = "SIM";
    public static final String NAO = "NAO";
    public  static final String PODE_VOTAR = "ABLE_TO_VOTE";

    /**
     * Construtor padrão vazio
     *
     * */
    public VotoUtil() {}

    /**
     * Método responsavel por validar a entidade {@link Voto}
     * @param dto entidade para ser validada
     * @throws MethodNotAllowedException caso não tenha sido preenchido o valor do voto
     * @throws NoSuchElementException Caso não tenha sido preenchido o cpf do associado votante
     *
     * @return TRUE caso todas as condições sejam respeitadas
     * */
    public static boolean validarVoto(VotoDTO dto){
        if (Objects.isNull(dto.getVotoSimNao()) || dto.getVotoSimNao().isEmpty()){
            throw new MethodNotAllowedException("Você precisa enviar seu voto para que ele seja validado!");
        }
        if (Objects.isNull(dto.getCpfAssociado()) || dto.getCpfAssociado().isEmpty() || dto.getCpfAssociado().length() < 11) {
            throw new NoSuchElementException("Você precisa colocar um CPF de um associado previamente cadastrado!");
        }
        return true;
    }

    /**
     * Método responsavel por validar o valor do voto caso seja SIM ou NAO
     * @param votoSimNao Valor do voto sendo SIM ou NAO
     *
     * @return uma string como SIM ou NAO
     * */
    public static String defineVoto(String votoSimNao) {
        if (votoSimNao.toUpperCase(Locale.ROOT).equals(SIM)){
            return SIM;
        } else {
            return NAO;
        }
    }

}
