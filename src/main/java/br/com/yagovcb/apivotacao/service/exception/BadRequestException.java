package br.com.yagovcb.apivotacao.service.exception;

/**
 *  Classe que faz o apontamento para a {@link Exception} personalizada {@link BadRequestException}
 *
 *  Criado por Yago Castelo Branco
 *
 * @since 06/08/2021
 * */
public class BadRequestException extends RuntimeException {

    /**
     * Método que chama o construtor padrão de RuntimeException
     *
     * @param mensagem a ser exibida
     */
    public BadRequestException(String mensagem) {
        super(mensagem);
    }
}
