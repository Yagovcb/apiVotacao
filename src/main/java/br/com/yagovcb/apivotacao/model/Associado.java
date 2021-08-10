package br.com.yagovcb.apivotacao.model;

import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *  Classe da entidade {@link Associado}
 *
 *  @author Yago Castelo Branco
 *
 * @since 06/08/2021
 * */
@Data
@Entity
@Table(name = "associado")
public class Associado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String cpf;

    private String nome;
}
