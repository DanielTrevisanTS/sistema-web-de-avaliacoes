package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.QuestaoDAO;
import br.uel.sistemawebavaliacoes.dao.UsuarioDAO;
import br.uel.sistemawebavaliacoes.model.Alternativa;
import br.uel.sistemawebavaliacoes.model.Questao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        if (questao.getDisciplina() == null || questao.getDisciplina().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "A disciplina é obrigatória."));
        }
        if (!"MULTIPLA_ESCOLHA".equals(questao.getTipo()) && !"TEXTO_LIVRE".equals(questao.getTipo())) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Tipo de questão inválido."));
        }

        questao.setEnunciado(questao.getEnunciado().trim());
        questao.setDisciplina(questao.getDisciplina().trim());

        if ("MULTIPLA_ESCOLHA".equals(questao.getTipo())) {
            List<Alternativa> validas = new ArrayList<>();
            Set<String> textos = new HashSet<>();
            if (questao.getAlternativas() != null) {
                for (Alternativa alt : questao.getAlternativas()) {
                    if (alt != null && alt.getTexto() != null && !alt.getTexto().isBlank()) {
                        String textoLimpo = alt.getTexto().trim();
                        if (!textos.add(textoLimpo)) {
                            return ResponseEntity.badRequest()
                                    .body(Map.of("erro", "Não é permitido cadastrar alternativas com texto duplicado."));
                        }
                        alt.setTexto(textoLimpo);
                        validas.add(alt);
                    }
                }
            }
            if (validas.size() < 2) {
                return ResponseEntity.badRequest()
                        .body(Map.of("erro", "Informe pelo menos 2 alternativas preenchidas para questões de múltipla escolha."));
            }
            long qtdCorretas = validas.stream().filter(Alternativa::isCorreta).count();
            if (qtdCorretas != 1) {
                return ResponseEntity.badRequest()
                        .body(Map.of("erro", "Marque exatamente uma alternativa preenchida como correta."));
            }
            questao.setAlternativas(validas);
        }

        try {
            int id = questaoDAO.cadastrar(questao);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("idQuestao", id));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Dados inválidos para cadastro da questão."));
        }
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
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (IllegalStateException | DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("erro", "Não é possível excluir: esta questão já está vinculada a uma avaliação."));
        }
    }
}