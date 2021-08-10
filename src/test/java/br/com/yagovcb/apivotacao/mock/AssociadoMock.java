package br.com.yagovcb.apivotacao.mock;

import br.com.yagovcb.apivotacao.model.Associado;
import br.com.yagovcb.apivotacao.service.dto.AssociadoDTO;

import java.util.ArrayList;
import java.util.List;

public class AssociadoMock {

    public static List<Associado> createAssociados(){
        List<Associado> associadoList = new ArrayList<>();
        Associado a1 = new Associado();
        a1.setId(1);
        a1.setCpf("01234567891");
        a1.setNome("Associado teste 1");
        Associado a2 = new Associado();
        a2.setId(2);
        a2.setCpf("98765432101");
        a2.setNome("Associado teste 2");
        Associado a3 = new Associado();
        a3.setId(3);
        a3.setCpf("81546321785");
        a3.setNome("Associado teste 3");

        associadoList.add(a1);
        associadoList.add(a2);
        associadoList.add(a3);
        return associadoList;
    }

    public static AssociadoDTO associadoTest(){
        AssociadoDTO associado = new AssociadoDTO();
        associado.setCpf("88717755204");
        associado.setNome("Yago do Valle");
        return associado;
    }
}
