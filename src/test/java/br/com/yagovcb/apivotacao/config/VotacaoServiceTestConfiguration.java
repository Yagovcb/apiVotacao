package br.com.yagovcb.apivotacao.config;

import br.com.yagovcb.apivotacao.repository.AssociadoRepository;
import br.com.yagovcb.apivotacao.repository.PautaRepository;
import br.com.yagovcb.apivotacao.repository.VotacaoRepository;
import br.com.yagovcb.apivotacao.repository.VotoRepository;
import br.com.yagovcb.apivotacao.service.AssociadoService;
import br.com.yagovcb.apivotacao.service.PautaService;
import br.com.yagovcb.apivotacao.service.VotacaoService;
import br.com.yagovcb.apivotacao.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class VotacaoServiceTestConfiguration {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private VotacaoRepository votacaoRepository;

    @Autowired
    private VotoRepository votoRepository;

    @Autowired
    private PautaRepository pautaRepository;

    @Bean
    public AssociadoService associadoService() { return new AssociadoService(associadoRepository); }

    @Bean
    public VotacaoService votacaoService() { return new VotacaoService(votacaoRepository, pautaRepository); }

    @Bean
    public VotoService votoService() { return new VotoService(votoRepository, votacaoRepository, associadoRepository); }

    @Bean
    public PautaService pautaService() { return new PautaService(pautaRepository); }
}
