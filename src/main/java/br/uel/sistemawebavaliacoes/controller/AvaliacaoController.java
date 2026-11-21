package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.AvaliacaoDAO;
import br.uel.sistemawebavaliacoes.model.Avaliacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoDAO avaliacaoDAO;

    public AvaliacaoController(AvaliacaoDAO avaliacaoDAO) {
        this.avaliacaoDAO = avaliacaoDAO;
    }

    @GetMapping
    public List<Avaliacao> listar() {
        return avaliacaoDAO.listarTodas();
    }

    @GetMapping("/{id}")
    public Avaliacao buscarDetalhes(@PathVariable int id) {
        return avaliacaoDAO.buscarDetalhes(id);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Avaliacao avaliacao) {
        if (avaliacao.getQuestoes() == null || avaliacao.getQuestoes().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Selecione ao menos uma questão para a avaliação."));
        }
        if (avaliacao.getHoraFim() != null && avaliacao.getHoraInicio() != null
                && !avaliacao.getHoraFim().isAfter(avaliacao.getHoraInicio())) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O horário de fim deve ser posterior ao início."));
        }
        int id = avaliacaoDAO.cadastrarCompleta(avaliacao);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("idAvaliacao", id));
    }

    @PostMapping("/{id}/atribuir")
    public ResponseEntity<?> atribuir(@PathVariable int id, @RequestBody Map<String, Object> payload) {
        String turma = (String) payload.get("turma");
        @SuppressWarnings("unchecked")
        List<Integer> alunosIds = (List<Integer>) payload.get("alunosIds");
        avaliacaoDAO.atribuirAlunos(id, alunosIds, turma);
        return ResponseEntity.ok(Map.of("mensagem", "Alunos atribuídos com sucesso."));
    }
}