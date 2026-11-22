package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.AvaliacaoDAO;
import br.uel.sistemawebavaliacoes.dao.UsuarioDAO;
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
    private final UsuarioDAO usuarioDAO;

    public AvaliacaoController(AvaliacaoDAO avaliacaoDAO, UsuarioDAO usuarioDAO) {
        this.avaliacaoDAO = avaliacaoDAO;
        this.usuarioDAO = usuarioDAO;
    }

    @GetMapping
    public List<Avaliacao> listar() {
        return avaliacaoDAO.listarTodas();
    }

    @GetMapping("/aluno/{idAluno}")
    public List<Avaliacao> listarPorAluno(@PathVariable int idAluno) {
        return avaliacaoDAO.listarPorAluno(idAluno);
    }

    @GetMapping("/{id}")
    public Avaliacao buscarDetalhes(@PathVariable int id, @RequestParam(defaultValue = "false") boolean modoAluno) {
        return avaliacaoDAO.buscarDetalhes(id, modoAluno);
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Avaliacao avaliacao) {
        if (!usuarioDAO.isProfessor(avaliacao.getIdProfessor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Apenas professores podem criar avaliações."));
        }
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> excluir(@PathVariable int id, @RequestParam Integer idUsuario) {
        if (!usuarioDAO.isProfessor(idUsuario)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Apenas professores podem excluir avaliações."));
        }
        try {
            avaliacaoDAO.excluir(id);
            return ResponseEntity.ok(Map.of("mensagem", "Avaliação excluída com sucesso."));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", e.getMessage()));
        }
    }
}