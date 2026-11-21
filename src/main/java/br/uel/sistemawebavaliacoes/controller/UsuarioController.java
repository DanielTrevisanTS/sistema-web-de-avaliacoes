package br.uel.sistemawebavaliacoes.controller;

import br.uel.sistemawebavaliacoes.dao.UsuarioDAO;
import br.uel.sistemawebavaliacoes.model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioDAO usuarioDAO;

    public UsuarioController(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciais) {
        String email = credenciais.get("email");
        String senha = credenciais.get("senha");

        return usuarioDAO.autenticar(email, senha)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("erro", "E-mail ou senha inválidos.")));
    }

    @GetMapping("/alunos")
    public List<Usuario> listarAlunos() {
        return usuarioDAO.listarAlunos();
    }

    @GetMapping("/turmas")
    public List<String> listarTurmas() {
        return usuarioDAO.listarTurmas();
    }
}