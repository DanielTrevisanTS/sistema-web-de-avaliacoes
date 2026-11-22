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

        int idQuestao = keyHolder.getKey().intValue();

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

        for (Questao q : questoes) {
            if ("MULTIPLA_ESCOLHA".equals(q.getTipo())) {
                q.setAlternativas(listarAlternativasPorQuestao(q.getIdQuestao()));
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
        jdbcTemplate.update("DELETE FROM alternativa WHERE id_questao = ?", idQuestao);
        jdbcTemplate.update("DELETE FROM questao WHERE id_questao = ?", idQuestao);
    }
}