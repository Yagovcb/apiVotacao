package br.com.yagovcb.apivotacao.integration;

import br.com.yagovcb.apivotacao.ApiVotacaoApplication;
import br.com.yagovcb.apivotacao.mock.PautaMock;
import br.com.yagovcb.apivotacao.service.dto.PautaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApiVotacaoApplication.class)
@DisplayName("PautaController - Teste de Integração")
public class PautaControllerIT {

    private final String BASE_URL = "/pauta/criar_pauta";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc restPautaMockMvc;

    @Test
    @DisplayName("PautaController - Teste do endpoint de criação de pauta")
    public void postCreateAssociadoTest() throws Exception {
        PautaDTO dto = PautaMock.pautaTest();
        restPautaMockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.questionamento").value(dto.getQuestionamento()));

    }

    @Test
    @DisplayName("PautaController - Teste do endpoint de criação de pauta forçando erro BadRequest")
    public void postCreateAssociadoTest_erroBadRequest() throws Exception {
        PautaDTO dto = PautaMock.pautaTest();
        restPautaMockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        PautaDTO pautaDTO = new PautaDTO();
        pautaDTO.setQuestionamento("fail");
        restPautaMockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(pautaDTO)))
                .andExpect(status().isBadRequest());

    }
}

