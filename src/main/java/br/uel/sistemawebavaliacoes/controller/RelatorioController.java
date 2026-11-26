package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.RelatorioDAO;
import br.uel.sistemawebavaliacoes.model.RelatorioItem;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioDAO relatorioDAO;

    public RelatorioController(RelatorioDAO relatorioDAO) {
        this.relatorioDAO = relatorioDAO;
    }

    @GetMapping("/media-avaliacoes")
    public List<RelatorioItem> mediaPorAvaliacao(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim,
            @RequestParam(required = false) String turma) {
        return relatorioDAO.mediaPorAvaliacao(dataInicio, dataFim, turma);
    }

    @GetMapping("/desempenho-aluno/{idAluno}")
    public List<RelatorioItem> desempenhoAluno(@PathVariable int idAluno) {
        return relatorioDAO.desempenhoAlunoVsTurma(idAluno);
    }

    @GetMapping("/ranking-questoes-erradas")
    public List<RelatorioItem> rankingQuestoesErradas() {
        return relatorioDAO.rankingQuestoesMaisErradas();
    }

    @GetMapping("/participacao")
    public Map<String, List<RelatorioItem>> participacao() {
        Map<String, List<RelatorioItem>> resposta = new HashMap<>();
        resposta.put("resumo", relatorioDAO.resumoParticipacaoAvaliacoes());
        resposta.put("pendentes", relatorioDAO.listarAlunosPendentes());
        return resposta;
    }
}