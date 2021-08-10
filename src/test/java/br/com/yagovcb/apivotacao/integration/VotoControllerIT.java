package br.com.yagovcb.apivotacao.integration;

import br.com.yagovcb.apivotacao.ApiVotacaoApplication;
import br.com.yagovcb.apivotacao.mock.VotoMock;
import br.com.yagovcb.apivotacao.model.Votacao;
import br.com.yagovcb.apivotacao.repository.VotacaoRepository;
import br.com.yagovcb.apivotacao.service.dto.VotacaoDTO;
import br.com.yagovcb.apivotacao.service.dto.VotoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApiVotacaoApplication.class)
@DisplayName("VotoController - Teste de Integração")
public class VotoControllerIT {

    private final String BASE_URL = "/voto/votar";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc restVotoMockMvc;

    @MockBean
    private VotacaoRepository votacaoRepository;

    @Test
    @DisplayName("VotacaoController - Teste do endpoint de criação de um voto")
    public void postCreateVotacaoTest() throws Exception {
        VotoDTO dto = VotoMock.votoTest();
        Votacao votacao = new Votacao();
        when(votacaoRepository.save(any())).thenReturn(votacao);

        restVotoMockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.voto").value(dto.getVotoSimNao()));
    }

    @Test
    @DisplayName("VotacaoController - Teste do endpoint de criação de um novo voto para verificar se o " +
            "voto é valido ou se o associado tem permissao de votar simulando o erro MethodNotAllowed")
    public void postCreateVotacaoTest_erroMethodAllowed() throws Exception {
        Votacao votacao = new Votacao();
        when(votacaoRepository.save(any())).thenReturn(votacao);

        restVotoMockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new VotacaoDTO())))
                .andExpect(status().isMethodNotAllowed());

    }

}
