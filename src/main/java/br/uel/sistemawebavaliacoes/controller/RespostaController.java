package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.RespostaDAO;
import br.uel.sistemawebavaliacoes.dao.UsuarioDAO;
import br.uel.sistemawebavaliacoes.model.Resposta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/respostas")
public class RespostaController {

    private final RespostaDAO respostaDAO;
    private final UsuarioDAO usuarioDAO;

    public RespostaController(RespostaDAO respostaDAO, UsuarioDAO usuarioDAO) {
        this.respostaDAO = respostaDAO;
        this.usuarioDAO = usuarioDAO;
    }

    public record SubmissaoRequest(int idAvaliacao, int idAluno, List<Resposta> respostas) {}
    public record CorrecaoRequest(Integer idProfessor, BigDecimal nota) {}

    @PostMapping("/submeter")
    public ResponseEntity<?> submeter(@RequestBody SubmissaoRequest req) {
        try {
            respostaDAO.submeterAvaliacao(req.idAvaliacao(), req.idAluno(), req.respostas());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(Map.of("mensagem", "Avaliação enviada com sucesso! Questões objetivas já foram corrigidas."));
        } catch (IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("erro", "Erro ao gravar respostas: " + e.getMessage()));
        }
    }

    @GetMapping("/pendentes")
    public List<Resposta> listarPendentes() {
        return respostaDAO.listarDiscursivasPendentes();
    }

    @PutMapping("/{idResposta}/corrigir")
    public ResponseEntity<?> corrigir(@PathVariable int idResposta, @RequestBody CorrecaoRequest req) {
        if (!usuarioDAO.isProfessor(req.idProfessor())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(Map.of("erro", "Apenas professores podem atribuir notas."));
        }
        try {
            respostaDAO.corrigirDiscursiva(idResposta, req.nota());
            return ResponseEntity.ok(Map.of("mensagem", "Nota registrada com sucesso!"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("erro", e.getMessage()));
        }
    }

    @GetMapping("/boletim")
    public List<Resposta> boletim(@RequestParam int idAvaliacao, @RequestParam int idAluno) {
        return respostaDAO.listarBoletimAluno(idAvaliacao, idAluno);
    }
}