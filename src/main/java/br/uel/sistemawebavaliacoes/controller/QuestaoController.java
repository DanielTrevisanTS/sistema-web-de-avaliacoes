package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.QuestaoDAO;
import br.uel.sistemawebavaliacoes.model.Alternativa;
import br.uel.sistemawebavaliacoes.model.Questao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questoes")
public class QuestaoController {

    private final QuestaoDAO questaoDAO;

    public QuestaoController(QuestaoDAO questaoDAO) {
        this.questaoDAO = questaoDAO;
    }

    @GetMapping
    public List<Questao> listar() {
        return questaoDAO.listarTodas();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Questao questao) {
        if (questao.getEnunciado() == null || questao.getEnunciado().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O enunciado é obrigatório."));
        }
        if ("MULTIPLA_ESCOLHA".equals(questao.getTipo())) {
            boolean temCorreta = questao.getAlternativas() != null &&
                    questao.getAlternativas().stream().anyMatch(Alternativa::isCorreta);
            if (!temCorreta) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Marque uma alternativa correta."));
            }
        }
        int id = questaoDAO.cadastrar(questao);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("idQuestao", id));
    }
}