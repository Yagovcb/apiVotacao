package br.com.yagovcb.apivotacao.service;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import br.com.yagovcb.apivotacao.mock.VotacaoMock;
import br.com.yagovcb.apivotacao.model.Pauta;
import br.com.yagovcb.apivotacao.model.Votacao;
import br.com.yagovcb.apivotacao.model.Voto;
import br.com.yagovcb.apivotacao.repository.PautaRepository;
import br.com.yagovcb.apivotacao.repository.VotacaoRepository;
import br.com.yagovcb.apivotacao.service.dto.VotacaoDTO;
import br.com.yagovcb.apivotacao.service.exception.MethodNotAllowedException;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@EnableAsync
public class VotacaoServiceTest {

    @Autowired
    private VotacaoService votacaoService;

    @MockBean
    private VotacaoRepository votacaoRepository;

    @MockBean
    private PautaRepository pautaRepository;

    @BeforeEach
    public void setup() {
        this.votacaoService = new VotacaoService(votacaoRepository, pautaRepository);
    }

    @Test
    @DisplayName("VotacaoServiceTest - Teste de criacao de Votacao")
    public void testSalvarVotacao() {
        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setQuestionamento("alice.liddell@example.org");

        Votacao votacao = new Votacao();
        votacao.setPauta(pauta);
        votacao.setId(1);
        votacao.setVotosNao(1);
        votacao.setEmVotacao(true);
        votacao.setVotos(new ArrayList<Voto>());
        votacao.setVotosSim(1);
        Optional<Votacao> ofResult = Optional.<Votacao>of(votacao);
        when(this.votacaoRepository.findByEmVotacao((Boolean) any())).thenReturn(ofResult);

        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setIdPauta(1);
        votacaoDTO.setTempo(1);
        votacaoDTO.setIdVotacao(1);
        assertNull(this.votacaoService.salvarVotacao(votacaoDTO));
        verify(this.votacaoRepository, times(2)).findByEmVotacao((Boolean) any());
    }

    @Test
    @DisplayName("VotacaoServiceTest - Teste de criacao de Votacao 2")
    public void testSalvarVotacao2() {
        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setQuestionamento("alice.liddell@example.org");

        Votacao votacao = new Votacao();
        votacao.setPauta(pauta);
        votacao.setId(1);
        votacao.setVotosNao(1);
        votacao.setEmVotacao(true);
        votacao.setVotos(new ArrayList<Voto>());
        votacao.setVotosSim(1);
        when(this.votacaoRepository.save((Votacao) any())).thenReturn(votacao);
        when(this.votacaoRepository.findByEmVotacao((Boolean) any())).thenReturn(Optional.<Votacao>empty());

        Pauta pauta1 = new Pauta();
        pauta1.setId(1);
        pauta1.setQuestionamento("alice.liddell@example.org");
        Optional<Pauta> ofResult = Optional.<Pauta>of(pauta1);
        when(this.pautaRepository.findById((Integer) any())).thenReturn(ofResult);

        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setIdPauta(1);
        votacaoDTO.setTempo(1);
        votacaoDTO.setIdVotacao(1);
        assertSame(votacao, this.votacaoService.salvarVotacao(votacaoDTO));
        verify(this.votacaoRepository, times(2)).findByEmVotacao((Boolean) any());
        verify(this.votacaoRepository).save((Votacao) any());
        verify(this.pautaRepository).findById((Integer) any());
    }

    @Test
    @DisplayName("VotacaoServiceTest - Teste de criacao de Votacao simulando o erro MethodNotAllowed")
    public void testSalvarVotacao_erroMethodNotAllowed() {
        when(this.votacaoRepository.save((Votacao) any())).thenThrow(new MethodNotAllowedException("Mensagem"));
        when(this.votacaoRepository.findByEmVotacao((Boolean) any())).thenReturn(Optional.<Votacao>empty());

        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setQuestionamento("alice.liddell@example.org");
        Optional<Pauta> ofResult = Optional.<Pauta>of(pauta);
        when(this.pautaRepository.findById((Integer) any())).thenReturn(ofResult);

        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setIdPauta(1);
        votacaoDTO.setTempo(1);
        votacaoDTO.setIdVotacao(1);
        assertThrows(MethodNotAllowedException.class, () -> this.votacaoService.salvarVotacao(votacaoDTO));
        verify(this.votacaoRepository, times(2)).findByEmVotacao((Boolean) any());
        verify(this.votacaoRepository).save((Votacao) any());
        verify(this.pautaRepository).findById((Integer) any());
    }

    @Test
    public void testAbrirVotacao() {
        // Arrange
        Pauta pauta = new Pauta();
        pauta.setId(1);
        pauta.setQuestionamento("alice.liddell@example.org");

        Votacao votacao = new Votacao();
        votacao.setPauta(pauta);
        votacao.setId(1);
        votacao.setVotosNao(1);
        votacao.setEmVotacao(true);
        votacao.setVotos(new ArrayList<Voto>());
        votacao.setVotosSim(1);
        Optional<Votacao> ofResult = Optional.<Votacao>of(votacao);
        when(this.votacaoRepository.findById((Integer) any())).thenReturn(ofResult);
        VotacaoDTO votacaoDTO = VotacaoMock.votacaoTest();

        // Act
        this.votacaoService.abrirVotacao(votacaoDTO);

    }

}
