package br.com.yagovcb.apivotacao.repository;

import br.com.yagovcb.apivotacao.model.Votacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Classe Repository da entidade {@link Votacao}
 * @author  Yago Castelo Branco
 * @since 06/08/2021
 * */
@Repository
public interface VotacaoRepository extends JpaRepository<Votacao, Integer> {

    @Query(name = "select v from Votacao v join Pauta p on v.pauta = p join Voto vts on v.votos = vts' where v.emVotacao = :votacao", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Votacao> findByEmVotacao(@Param(value = "votacao") Boolean votacao);

    @Query(name = "select v from Votacao v join Pauta p on v.pauta = p join Voto vts on v.votos = vts' where v.id = :id", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Votacao> findById (@Param(value = "id") Integer id);

}
