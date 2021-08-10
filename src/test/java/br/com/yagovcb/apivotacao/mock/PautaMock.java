package br.com.yagovcb.apivotacao.mock;

import br.com.yagovcb.apivotacao.model.Pauta;
import br.com.yagovcb.apivotacao.service.dto.PautaDTO;
import java.util.ArrayList;
import java.util.List;

public class PautaMock {
    public static List<Pauta> createPautas() {
        List<Pauta> pautaList = new ArrayList<>();
        Pauta p1 = new Pauta();
        p1.setId(1);
        p1.setQuestionamento("Questionamento teste 1");
        Pauta p2 = new Pauta();
        p2.setId(2);
        p2.setQuestionamento("Questionamento teste 2");
        Pauta p3 = new Pauta();
        p3.setId(3);
        p3.setQuestionamento("Questionamento teste 3");

        pautaList.add(p1);
        pautaList.add(p2);
        pautaList.add(p3);
        return pautaList;
    }

    public static PautaDTO pautaTest(){
        PautaDTO dto = new PautaDTO();
        dto.setQuestionamento("Questionamento para teste");
        return dto;
    }
}
