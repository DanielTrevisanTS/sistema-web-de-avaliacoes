package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.AvaliacaoDAO;
import br.uel.sistemawebavaliacoes.dao.UsuarioDAO;
import br.uel.sistemawebavaliacoes.model.Avaliacao;
import br.uel.sistemawebavaliacoes.model.AvaliacaoQuestao;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/avaliacoes")
public class AvaliacaoController {

    private final AvaliacaoDAO avaliacaoDAO;
    private final UsuarioDAO usuarioDAO;

    public AvaliacaoController(AvaliacaoDAO avaliacaoDAO, UsuarioDAO usuarioDAO) {
        this.avaliacaoDAO = avaliacaoDAO;
        this.usuarioDAO = usuarioDAO;
    }

    public record AtribuicaoRequest(Integer idProfessor, String turma, List<Integer> alunosIds) {}

    @GetMapping
    public List<Avaliacao> listar() {
        return avaliacaoDAO.listarTodas();
    }

    @GetMapping("/aluno/{idAluno}")
    public List<Avaliacao> listarPorAluno(@PathVariable int idAluno) {
        return avaliacaoDAO.listarPorAluno(idAluno);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarDetalhes(@PathVariable int id, @RequestParam(defaultValue = "false") boolean modoAluno) {
        try {
            return ResponseEntity.ok(avaliacaoDAO.buscarDetalhes(id, modoAluno));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        }
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Avaliacao avaliacao) {
        if (!usuarioDAO.isProfessor(avaliacao.getIdProfessor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Apenas professores podem criar avaliações."));
        }
        if (avaliacao.getTitulo() == null || avaliacao.getTitulo().isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O título da avaliação é obrigatório."));
        }
        if (avaliacao.getDataAplicacao() == null || avaliacao.getHoraInicio() == null || avaliacao.getHoraFim() == null) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Informe a data, o horário de início e o horário de fim."));
        }
        if (!avaliacao.getHoraFim().isAfter(avaliacao.getHoraInicio())) {
            return ResponseEntity.badRequest().body(Map.of("erro", "O horário de fim deve ser posterior ao início."));
        }
        if (avaliacao.getQuestoes() == null || avaliacao.getQuestoes().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Selecione ao menos uma questão para a avaliação."));
        }

        Set<Integer> questoesUnicas = new HashSet<>();
        for (AvaliacaoQuestao item : avaliacao.getQuestoes()) {
            if (item == null || item.getIdQuestao() == null || !questoesUnicas.add(item.getIdQuestao())) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Lista de questões inválida ou duplicada."));
            }
            if (item.getValor() == null || item.getValor().compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body(Map.of("erro", "Todas as questões selecionadas devem ter valor maior que zero."));
            }
        }

        boolean semTurma = avaliacao.getTurmaAtribuida() == null || avaliacao.getTurmaAtribuida().isBlank();
        boolean semAlunos = avaliacao.getAlunosIds() == null || avaliacao.getAlunosIds().isEmpty();
        if (semTurma && semAlunos) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Selecione uma turma ou pelo menos um aluno (RF4)."));
        }

        avaliacao.setTitulo(avaliacao.getTitulo().trim());
        try {
            int id = avaliacaoDAO.cadastrarCompleta(avaliacao);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("idAvaliacao", id));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Não foi possível criar a avaliação: verifique as questões e alunos informados."));
        }
    }

    @PostMapping("/{id}/atribuir")
    public ResponseEntity<?> atribuir(@PathVariable int id, @RequestBody AtribuicaoRequest req) {
        if (!usuarioDAO.isProfessor(req.idProfessor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Apenas professores podem atribuir alunos."));
        }
        boolean semTurma = req.turma() == null || req.turma().isBlank();
        boolean semAlunos = req.alunosIds() == null || req.alunosIds().isEmpty();
        if (semTurma && semAlunos) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Selecione uma turma ou ao menos um aluno."));
        }
        try {
            avaliacaoDAO.atribuirAlunos(id, req.alunosIds(), req.turma());
            return ResponseEntity.ok(Map.of("mensagem", "Alunos atribuídos com sucesso."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("erro", e.getMessage()));
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", "Verifique os IDs de alunos informados."));
        }
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
        } catch (IllegalStateException | DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("erro", e.getMessage()));
        }
    }
}