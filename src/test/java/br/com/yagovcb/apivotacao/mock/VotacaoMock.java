package br.com.yagovcb.apivotacao.mock;

import br.com.yagovcb.apivotacao.service.dto.VotacaoDTO;

public class VotacaoMock {

    public static VotacaoDTO votacaoTest(){
        VotacaoDTO votacaoDTO = new VotacaoDTO();
        votacaoDTO.setIdVotacao(1);
        votacaoDTO.setTempo(1);
        votacaoDTO.setIdPauta(1);
        return votacaoDTO;
    }

}
