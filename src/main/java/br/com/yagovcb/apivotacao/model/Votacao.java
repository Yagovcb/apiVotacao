package br.com.yagovcb.apivotacao.model;

import lombok.Data;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 *  Classe da entidade {@link Votacao}
 *
 *  @author Yago Castelo Branco
 *
 * @since 06/08/2021
 * */
@Data
@Entity
@Table(name = "votacao")
public class Votacao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    private Pauta pauta;

    private boolean emVotacao;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Voto> votos;

    private int votosSim;

    private int votosNao;
}
