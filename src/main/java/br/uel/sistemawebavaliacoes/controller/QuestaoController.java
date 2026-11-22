package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.QuestaoDAO;
import br.uel.sistemawebavaliacoes.dao.UsuarioDAO;
import br.uel.sistemawebavaliacoes.model.Alternativa;
import br.uel.sistemawebavaliacoes.model.Questao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questoes")
public class QuestaoController {

    private final QuestaoDAO questaoDAO;
    private final UsuarioDAO usuarioDAO;

    public QuestaoController(QuestaoDAO questaoDAO, UsuarioDAO usuarioDAO) {
        this.questaoDAO = questaoDAO;
        this.usuarioDAO = usuarioDAO;
    }

    @GetMapping
    public List<Questao> listar() {
        return questaoDAO.listarTodas();
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Questao questao) {
        if (!usuarioDAO.isProfessor(questao.getIdProfessor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Apenas professores podem cadastrar questões."));
        }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable int id, @RequestParam Integer idUsuario) {
        if (!usuarioDAO.isProfessor(idUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Apenas professores podem excluir questões."));
        }
        try {
            questaoDAO.excluir(id);
            return ResponseEntity.ok(Map.of("mensagem", "Questão excluída com sucesso."));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "Não é possível excluir: esta questão já está vinculada a uma avaliação."));
        }
    }
}