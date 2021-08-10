package br.com.yagovcb.apivotacao.mock;

import br.com.yagovcb.apivotacao.service.dto.VotoDTO;

public class VotoMock {

    public static VotoDTO votoTest(){
        VotoDTO votacaoDTO = new VotoDTO();
        votacaoDTO.setVotoSimNao("SIM");
        votacaoDTO.setCpfAssociado("04863047010");
        return votacaoDTO;
    }
}
