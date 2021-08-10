package br.com.yagovcb.apivotacao.integration;

import br.com.yagovcb.apivotacao.ApiVotacaoApplication;
import br.com.yagovcb.apivotacao.mock.VotacaoMock;
import br.com.yagovcb.apivotacao.service.dto.VotacaoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApiVotacaoApplication.class)
@DisplayName("VotacaoController - Teste de Integração")
public class VotacaoControllerIT {

    private final String CRIAR = "/votacao/criar_votacao";
    private final String ABRIR = "/votacao/abrir_votacao";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc restVotacaoMockMvc;

    @Test
    @DisplayName("VotacaoController - Teste do endpoint de criação de uma votacao")
    public void postCreateVotacaoTest() throws Exception {
        VotacaoDTO dto = VotacaoMock.votacaoTest();
        restVotacaoMockMvc.perform(post(CRIAR).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.emVotacao").value(Boolean.TRUE));
    }

    @Test
    @DisplayName("VotacaoController - Teste do endpoint de abertura de votacao já criada")
    public void postAbrirVotacaoTest() throws Exception {
        VotacaoDTO dto = VotacaoMock.votacaoTest();
        restVotacaoMockMvc.perform(post(CRIAR).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        MvcResult mvcResult = restVotacaoMockMvc.perform(post(ABRIR).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(request().asyncStarted()).andReturn();
        mvcResult.getAsyncResult();

    }
}

