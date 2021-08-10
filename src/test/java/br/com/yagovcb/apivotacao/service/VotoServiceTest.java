package br.com.yagovcb.apivotacao.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import br.com.yagovcb.apivotacao.mock.VotoMock;
import br.com.yagovcb.apivotacao.model.Pauta;
import br.com.yagovcb.apivotacao.model.Votacao;
import br.com.yagovcb.apivotacao.repository.AssociadoRepository;
import br.com.yagovcb.apivotacao.repository.VotacaoRepository;
import br.com.yagovcb.apivotacao.repository.VotoRepository;
import br.com.yagovcb.apivotacao.service.dto.VotoDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.aop.AopInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class VotoServiceTest {

    @MockBean
    private AssociadoRepository associadoRepository;

    @MockBean
    private VotacaoRepository votacaoRepository;

    @MockBean
    private VotoRepository votoRepository;

    @Autowired
    private VotoService votoService;

    @BeforeEach
    public void setup() {
        this.votoService = new VotoService(votoRepository, votacaoRepository, associadoRepository);
    }

    @Test
    public void testVotar_erroMethodNotAllowed() {
        // Arrange
        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setQuestionamento("alice.liddell@example.org");

        Votacao votacao = new Votacao();
        votacao.setPauta(pauta);
        votacao.setId(1);
        votacao.setVotosNao(0);
        votacao.setEmVotacao(true);
        votacao.setVotos(new ArrayList<>());
        votacao.setVotosSim(0);
        Optional<Votacao> ofResult = Optional.<Votacao>of(votacao);
        when(this.votacaoRepository.findByEmVotacao((Boolean) any())).thenReturn(ofResult);
        when(this.associadoRepository.findByCpf(anyString())).thenThrow(new MethodNotAllowedException("Mensagem"));

        VotoDTO votoDTO = VotoMock.votoTest();

        // Act and Assert


        assertThrows(MethodNotAllowedException.class, () -> this.votoService.votar(votoDTO));
        verify(this.votacaoRepository).findByEmVotacao((Boolean) any());
        verify(this.associadoRepository).findByCpf(anyString());
    }

    @Test(expected = AopInvocationException.class)
    public void verificaPermissaoVotacaoTest(){
        VotoDTO votoDTO = VotoMock.votoTest();

        // Act and Assert
        assertEquals(Boolean.FALSE, this.votoService.verificaPermissaoVotacao(votoDTO.getCpfAssociado()));
        //TODO: Falta concluir o teste para melhorar a cobertura
    }
}
