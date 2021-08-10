package br.com.yagovcb.apivotacao.repository;

import br.com.yagovcb.apivotacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe Repository da entidade {@link Voto}
 * @author  Yago Castelo Branco
 * @since 06/08/2021
 * */
@Repository
public interface VotoRepository extends JpaRepository<Voto, Integer> {
}
