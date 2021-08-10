package br.com.yagovcb.apivotacao.service;

import br.com.yagovcb.apivotacao.mock.PautaMock;
import br.com.yagovcb.apivotacao.model.Pauta;
import br.com.yagovcb.apivotacao.repository.PautaRepository;
import br.com.yagovcb.apivotacao.service.dto.PautaDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class PautaServiceTest {

    @Autowired
    private PautaService service;

    @MockBean
    private PautaRepository repository;

    @BeforeEach
    public void setup(){
        this.service = new PautaService(repository);
        Mockito.when(this.repository.save(Mockito.any(Pauta.class))).thenReturn(new Pauta());
        Mockito.when(this.repository.findById(Mockito.any())).thenReturn(Optional.of(new Pauta()));

        when(repository.findAll()).thenReturn(PautaMock.createPautas());
    }

    @Test
    @Transactional
    @DisplayName("PautaServiceTest - Teste de criacao de Pauta")
    void salvarPautaTest(){
        PautaDTO pautaDTO = PautaMock.pautaTest();
        Pauta pauta = this.service.salvarPauta(pautaDTO);

        Assertions.assertNotNull(pautaDTO);
        Assertions.assertNotNull(pauta);

        when(this.service.salvarPauta(pautaDTO)).thenThrow(MethodNotAllowedException.class);
        Assertions.assertThrows(MethodNotAllowedException.class, () -> this.service.salvarPauta(pautaDTO));
    }
}

