package br.com.yagovcb.apivotacao.service.exception;

/**
 *  Classe que faz o apontamento para a {@link Exception} personalizada {@link MethodNotAllowedException}
 *
 *  Criado por Yago Castelo Branco
 *
 * @since 06/08/2021
 * */
public class MethodNotAllowedException extends RuntimeException{

    /**
     * Método que chama o construtor padrão de RuntimeException
     * @param mensagem a ser exibida
     * */
    public MethodNotAllowedException(String mensagem) {
        super(mensagem);
    }
}
