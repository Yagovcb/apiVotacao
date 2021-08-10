package br.com.yagovcb.apivotacao.service;

import br.com.yagovcb.apivotacao.mock.AssociadoMock;
import br.com.yagovcb.apivotacao.model.Associado;
import br.com.yagovcb.apivotacao.repository.AssociadoRepository;
import br.com.yagovcb.apivotacao.service.dto.AssociadoDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AssociadoServiceTest {

    @Autowired
    private AssociadoService service;

    @MockBean
    private AssociadoRepository repository;

    @BeforeEach
    public void setup() {
        this.service = new AssociadoService(repository);
        Mockito.when(this.repository.save(Mockito.any(Associado.class))).thenReturn(new Associado());
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.of(new Associado()));

        when(repository.findAll()).thenReturn(AssociadoMock.createAssociados());
    }

    @Test
    @Transactional
    @DisplayName("AssociadoServiceTest - Teste de criacao de Associado")
    public void createAssociadoTest(){
        AssociadoDTO associadoDTO = AssociadoMock.associadoTest();

        Assertions.assertNotNull(associadoDTO);

        when(this.service.cadastrarAssociado(associadoDTO)).thenThrow(MethodNotAllowedException.class);
        Assertions.assertThrows(MethodNotAllowedException.class, () -> this.service.cadastrarAssociado(associadoDTO));
    }
}
