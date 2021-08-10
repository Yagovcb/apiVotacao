package br.com.yagovcb.apivotacao.repository;

import br.com.yagovcb.apivotacao.model.Associado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Classe Repository da entidade {@link Associado}
 * @author  Yago Castelo Branco
 * @since 06/08/2021
 * */
@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Object> {

    Associado findByCpf(String cpf);
}
