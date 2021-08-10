package br.com.yagovcb.apivotacao.integration;

import br.com.yagovcb.apivotacao.ApiVotacaoApplication;
import br.com.yagovcb.apivotacao.mock.AssociadoMock;
import br.com.yagovcb.apivotacao.service.dto.AssociadoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = ApiVotacaoApplication.class)
@DisplayName("AssociadoController - Teste de Integração")
public class AssociadoControllerIT {

    private final String BASE_URL = "/associado/cadastrar";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc restAssociadoMockMvc;

    @Test
    @DisplayName("AssociadoController - Teste do endpoint de criação de associado")
    public void postCreateAssociadoTest() throws Exception {
        AssociadoDTO associadoDTO = AssociadoMock.associadoTest();
        restAssociadoMockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(associadoDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.cpf").value(associadoDTO.getCpf()));
    }
}

