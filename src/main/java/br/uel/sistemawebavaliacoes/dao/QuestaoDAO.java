package br.uel.sistemawebavaliacoes.dao;

import br.uel.sistemawebavaliacoes.model.Alternativa;
import br.uel.sistemawebavaliacoes.model.Questao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class QuestaoDAO {

    private final JdbcTemplate jdbcTemplate;

    public QuestaoDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public int cadastrar(Questao questao) {
        String sqlQuestao = "INSERT INTO questao (enunciado, tipo, disciplina, id_professor) VALUES (?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuestao, new String[]{"id_questao"});
            ps.setString(1, questao.getEnunciado());
            ps.setString(2, questao.getTipo());
            ps.setString(3, questao.getDisciplina());
            ps.setInt(4, questao.getIdProfessor());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Falha ao obter o ID da questão cadastrada.");
        }
        int idQuestao = key.intValue();

        if ("MULTIPLA_ESCOLHA".equals(questao.getTipo()) && questao.getAlternativas() != null) {
            String sqlAlt = "INSERT INTO alternativa (id_questao, texto, correta) VALUES (?, ?, ?)";
            for (Alternativa alt : questao.getAlternativas()) {
                jdbcTemplate.update(sqlAlt, idQuestao, alt.getTexto(), alt.isCorreta());
            }
        }

        return idQuestao;
    }

    public List<Questao> listarTodas() {
        String sql = """
                SELECT q.id_questao, q.enunciado, q.tipo, q.disciplina, q.id_professor, u.nome AS nome_professor
                FROM questao q
                JOIN usuario u ON u.id_usuario = q.id_professor
                ORDER BY q.id_questao DESC
                """;

        List<Questao> questoes = jdbcTemplate.query(sql, (rs, rowNum) -> {
            Questao q = new Questao();
            q.setIdQuestao(rs.getInt("id_questao"));
            q.setEnunciado(rs.getString("enunciado"));
            q.setTipo(rs.getString("tipo"));
            q.setDisciplina(rs.getString("disciplina"));
            q.setIdProfessor(rs.getInt("id_professor"));
            q.setNomeProfessor(rs.getString("nome_professor"));
            return q;
        });

        String sqlTodasAlt = "SELECT id_alternativa, id_questao, texto, correta FROM alternativa ORDER BY id_questao, id_alternativa";
        java.util.Map<Integer, List<Alternativa>> mapaAlt = new java.util.HashMap<>();
        jdbcTemplate.query(sqlTodasAlt, rs -> {
            Alternativa a = new Alternativa();
            a.setIdAlternativa(rs.getInt("id_alternativa"));
            a.setIdQuestao(rs.getInt("id_questao"));
            a.setTexto(rs.getString("texto"));
            a.setCorreta(rs.getBoolean("correta"));
            mapaAlt.computeIfAbsent(a.getIdQuestao(), k -> new java.util.ArrayList<>()).add(a);
        });

        for (Questao q : questoes) {
            if ("MULTIPLA_ESCOLHA".equals(q.getTipo())) {
                q.setAlternativas(mapaAlt.getOrDefault(q.getIdQuestao(), List.of()));
            }
        }

        return questoes;
    }

    public List<Alternativa> listarAlternativasPorQuestao(int idQuestao) {
        String sql = "SELECT id_alternativa, id_questao, texto, correta FROM alternativa WHERE id_questao = ? ORDER BY id_alternativa";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Alternativa a = new Alternativa();
            a.setIdAlternativa(rs.getInt("id_alternativa"));
            a.setIdQuestao(rs.getInt("id_questao"));
            a.setTexto(rs.getString("texto"));
            a.setCorreta(rs.getBoolean("correta"));
            return a;
        }, idQuestao);
    }

    @Transactional
    public void excluir(int idQuestao) {
        Integer vinculos = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM avaliacao_questao WHERE id_questao = ?", Integer.class, idQuestao);
        if (vinculos != null && vinculos > 0) {
            throw new IllegalStateException("Não é possível excluir: esta questão já está vinculada a uma avaliação.");
        }
        jdbcTemplate.update("DELETE FROM alternativa WHERE id_questao = ?", idQuestao);
        int removidas = jdbcTemplate.update("DELETE FROM questao WHERE id_questao = ?", idQuestao);
        if (removidas == 0) {
            throw new IllegalArgumentException("Questão não encontrada.");
        }
    }
}