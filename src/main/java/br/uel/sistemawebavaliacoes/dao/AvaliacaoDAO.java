package br.uel.sistemawebavaliacoes.dao;

import br.uel.sistemawebavaliacoes.model.Alternativa;
import br.uel.sistemawebavaliacoes.model.Avaliacao;
import br.uel.sistemawebavaliacoes.model.AvaliacaoQuestao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Repository
public class AvaliacaoDAO {

    private final JdbcTemplate jdbcTemplate;
    private final UsuarioDAO usuarioDAO;
    private final QuestaoDAO questaoDAO;

    public AvaliacaoDAO(JdbcTemplate jdbcTemplate, UsuarioDAO usuarioDAO, QuestaoDAO questaoDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.usuarioDAO = usuarioDAO;
        this.questaoDAO = questaoDAO;
    }

    @Transactional
    public int cadastrarCompleta(Avaliacao avaliacao) {
        String sqlAvaliacao = """
                INSERT INTO avaliacao (titulo, descricao, data_aplicacao, hora_inicio, hora_fim, id_professor)
                VALUES (?, ?, ?, ?, ?, ?)
                """;
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlAvaliacao, new String[]{"id_avaliacao"});
            ps.setString(1, avaliacao.getTitulo());
            ps.setString(2, avaliacao.getDescricao());
            ps.setDate(3, Date.valueOf(avaliacao.getDataAplicacao()));
            ps.setTime(4, Time.valueOf(avaliacao.getHoraInicio()));
            ps.setTime(5, Time.valueOf(avaliacao.getHoraFim()));
            ps.setInt(6, avaliacao.getIdProfessor());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key == null) {
            throw new IllegalStateException("Falha ao obter o ID da avaliação cadastrada.");
        }
        int idAvaliacao = key.intValue();

        String sqlItem = "INSERT INTO avaliacao_questao (id_avaliacao, id_questao, ordem, valor) VALUES (?, ?, ?, ?)";
        int ordemSequencial = 1;
        for (AvaliacaoQuestao item : avaliacao.getQuestoes()) {
            int ordem = (item.getOrdem() != null && item.getOrdem() > 0) ? item.getOrdem() : ordemSequencial;
            jdbcTemplate.update(sqlItem, idAvaliacao, item.getIdQuestao(), ordem, item.getValor());
            ordemSequencial++;
        }

        atribuirAlunosInterno(idAvaliacao, avaliacao.getAlunosIds(), avaliacao.getTurmaAtribuida());
        return idAvaliacao;
    }

    @Transactional
    public void atribuirAlunos(int idAvaliacao, List<Integer> alunosIds, String turma) {
        Integer existe = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM avaliacao WHERE id_avaliacao = ?", Integer.class, idAvaliacao);
        if (existe == null || existe == 0) {
            throw new IllegalArgumentException("Avaliação não encontrada.");
        }
        atribuirAlunosInterno(idAvaliacao, alunosIds, turma);
    }

    private void atribuirAlunosInterno(int idAvaliacao, List<Integer> alunosIds, String turma) {
        Set<Integer> destinatarios = new LinkedHashSet<>();
        if (turma != null && !turma.isBlank()) {
            destinatarios.addAll(usuarioDAO.listarIdsAlunosPorTurma(turma));
        }
        if (alunosIds != null) {
            destinatarios.addAll(alunosIds);
        }

        String sqlAtribuicao = """
                INSERT INTO avaliacao_usuario (id_avaliacao, id_aluno, status)
                VALUES (?, ?, 'PENDENTE')
                ON CONFLICT (id_avaliacao, id_aluno) DO NOTHING
                """;
        for (Integer idAluno : destinatarios) {
            jdbcTemplate.update(sqlAtribuicao, idAvaliacao, idAluno);
        }
    }

    @Transactional
    public void excluir(int idAvaliacao) {
        Integer respostasCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM resposta WHERE id_avaliacao = ?", Integer.class, idAvaliacao);
        if (respostasCount != null && respostasCount > 0) {
            throw new IllegalStateException("Não é possível excluir: já existem respostas enviadas para esta avaliação.");
        }
        jdbcTemplate.update("DELETE FROM avaliacao_usuario WHERE id_avaliacao = ?", idAvaliacao);
        jdbcTemplate.update("DELETE FROM avaliacao_questao WHERE id_avaliacao = ?", idAvaliacao);
        jdbcTemplate.update("DELETE FROM avaliacao WHERE id_avaliacao = ?", idAvaliacao);
    }

    public List<Avaliacao> listarTodas() {
        String sql = """
                SELECT a.id_avaliacao, a.titulo, a.descricao, a.data_aplicacao, a.hora_inicio, a.hora_fim,
                       a.id_professor, u.nome AS nome_professor,
                       COALESCE((SELECT SUM(aq.valor) FROM avaliacao_questao aq WHERE aq.id_avaliacao = a.id_avaliacao), 0) AS valor_total,
                       (SELECT COUNT(*) FROM avaliacao_questao aq WHERE aq.id_avaliacao = a.id_avaliacao) AS total_questoes,
                       (SELECT COUNT(*) FROM avaliacao_usuario au WHERE au.id_avaliacao = a.id_avaliacao) AS total_alunos
                FROM avaliacao a
                JOIN usuario u ON u.id_usuario = a.id_professor
                ORDER BY a.data_aplicacao DESC, a.id_avaliacao DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getInt("id_avaliacao"));
            a.setTitulo(rs.getString("titulo"));
            a.setDescricao(rs.getString("descricao"));
            a.setDataAplicacao(rs.getDate("data_aplicacao").toLocalDate());
            a.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
            a.setHoraFim(rs.getTime("hora_fim").toLocalTime());
            a.setIdProfessor(rs.getInt("id_professor"));
            a.setNomeProfessor(rs.getString("nome_professor"));
            a.setValorTotal(rs.getBigDecimal("valor_total"));
            a.setTotalQuestoes(rs.getInt("total_questoes"));
            a.setTotalAlunos(rs.getInt("total_alunos"));
            return a;
        });
    }

    public List<Avaliacao> listarPorAluno(int idAluno) {
        String sql = """
                SELECT a.id_avaliacao, a.titulo, a.descricao, a.data_aplicacao, a.hora_inicio, a.hora_fim,
                       u.nome AS nome_professor, au.status, au.data_submissao,
                       COALESCE((SELECT SUM(aq.valor) FROM avaliacao_questao aq WHERE aq.id_avaliacao = a.id_avaliacao), 0) AS valor_total,
                       COALESCE((SELECT SUM(r.nota_obtida) FROM resposta r WHERE r.id_avaliacao = a.id_avaliacao AND r.id_aluno = au.id_aluno), 0) AS nota_final
                FROM avaliacao_usuario au
                JOIN avaliacao a ON a.id_avaliacao = au.id_avaliacao
                JOIN usuario u ON u.id_usuario = a.id_professor
                WHERE au.id_aluno = ?
                ORDER BY a.data_aplicacao DESC, a.id_avaliacao DESC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getInt("id_avaliacao"));
            a.setTitulo(rs.getString("titulo"));
            a.setDescricao(rs.getString("descricao"));
            a.setDataAplicacao(rs.getDate("data_aplicacao").toLocalDate());
            a.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
            a.setHoraFim(rs.getTime("hora_fim").toLocalTime());
            a.setNomeProfessor(rs.getString("nome_professor"));
            a.setStatusAluno(rs.getString("status"));
            Timestamp ts = rs.getTimestamp("data_submissao");
            if (ts != null) {
                a.setDataSubmissao(ts.toLocalDateTime());
            }
            a.setValorTotal(rs.getBigDecimal("valor_total"));
            a.setNotaFinal(rs.getBigDecimal("nota_final"));
            return a;
        }, idAluno);
    }

    public Avaliacao buscarDetalhes(int idAvaliacao, boolean ocultarGabarito) {
        String sqlAvaliacao = """
                SELECT a.id_avaliacao, a.titulo, a.descricao, a.data_aplicacao, a.hora_inicio, a.hora_fim,
                       a.id_professor, u.nome AS nome_professor
                FROM avaliacao a
                JOIN usuario u ON u.id_usuario = a.id_professor
                WHERE a.id_avaliacao = ?
                """;

        List<Avaliacao> encontradas = jdbcTemplate.query(sqlAvaliacao, (rs, rowNum) -> {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getInt("id_avaliacao"));
            a.setTitulo(rs.getString("titulo"));
            a.setDescricao(rs.getString("descricao"));
            a.setDataAplicacao(rs.getDate("data_aplicacao").toLocalDate());
            a.setHoraInicio(rs.getTime("hora_inicio").toLocalTime());
            a.setHoraFim(rs.getTime("hora_fim").toLocalTime());
            a.setIdProfessor(rs.getInt("id_professor"));
            a.setNomeProfessor(rs.getString("nome_professor"));
            return a;
        }, idAvaliacao);

        if (encontradas.isEmpty()) {
            throw new IllegalArgumentException("Avaliação não encontrada.");
        }
        Avaliacao avaliacao = encontradas.get(0);

        String sqlQuestoes = """
                SELECT aq.id_avaliacao, aq.id_questao, aq.ordem, aq.valor,
                       q.enunciado, q.tipo, q.disciplina
                FROM avaliacao_questao aq
                JOIN questao q ON q.id_questao = aq.id_questao
                WHERE aq.id_avaliacao = ?
                ORDER BY aq.ordem
                """;

        List<AvaliacaoQuestao> itens = jdbcTemplate.query(sqlQuestoes, (rs, rowNum) -> {
            AvaliacaoQuestao item = new AvaliacaoQuestao();
            item.setIdAvaliacao(rs.getInt("id_avaliacao"));
            item.setIdQuestao(rs.getInt("id_questao"));
            item.setOrdem(rs.getInt("ordem"));
            item.setValor(rs.getBigDecimal("valor"));
            item.setEnunciado(rs.getString("enunciado"));
            item.setTipo(rs.getString("tipo"));
            item.setDisciplina(rs.getString("disciplina"));
            if ("MULTIPLA_ESCOLHA".equals(item.getTipo())) {
                List<Alternativa> alts = questaoDAO.listarAlternativasPorQuestao(item.getIdQuestao());
                if (ocultarGabarito) {
                    alts.forEach(alt -> alt.setCorreta(false));
                }
                item.setAlternativas(alts);
            }
            return item;
        }, idAvaliacao);

        if (avaliacao != null) {
            avaliacao.setQuestoes(itens);
        }
        return avaliacao;
    }
}