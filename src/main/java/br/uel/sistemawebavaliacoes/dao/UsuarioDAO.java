package br.uel.sistemawebavaliacoes.dao;

import br.uel.sistemawebavaliacoes.model.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioDAO {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Usuario> usuarioMapper = (rs, rowNum) -> {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setPerfil(rs.getString("perfil"));
        u.setTurma(rs.getString("turma"));
        return u;
    };

    public Optional<Usuario> autenticar(String email, String senha) {
        String sql = "SELECT id_usuario, nome, email, perfil, turma FROM usuario WHERE email = ? AND senha = ?";
        List<Usuario> lista = jdbcTemplate.query(sql, usuarioMapper, email, senha);
        return lista.stream().findFirst();
    }

    public List<Usuario> listarAlunos() {
        String sql = "SELECT id_usuario, nome, email, perfil, turma FROM usuario WHERE perfil = 'ALUNO' ORDER BY turma, nome";
        return jdbcTemplate.query(sql, usuarioMapper);
    }

    public List<String> listarTurmas() {
        String sql = "SELECT DISTINCT turma FROM usuario WHERE perfil = 'ALUNO' AND turma IS NOT NULL ORDER BY turma";
        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<Integer> listarIdsAlunosPorTurma(String turma) {
        String sql = "SELECT id_usuario FROM usuario WHERE perfil = 'ALUNO' AND turma = ?";
        return jdbcTemplate.queryForList(sql, Integer.class, turma);
    }
}