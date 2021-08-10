package br.com.yagovcb.apivotacao.repository;

import br.com.yagovcb.apivotacao.model.Pauta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Classe Repository da entidade {@link Pauta}
 * @author  Yago Castelo Branco
 * @since 06/08/2021
 * */
@Repository
public interface PautaRepository extends JpaRepository<Pauta, Integer> {

    @Query(name = "Pauta.findById", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Pauta> findById (@Param(value = "id") Integer id);

    @Query(name = "Pauta.existsByQuestionamento", nativeQuery = true)
    @RestResource(exported = false)
    boolean existsByQuestionamento(String questionamento);
}
