package br.uel.sistemawebavaliacoes.dao;

import br.uel.sistemawebavaliacoes.model.RelatorioItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Repository
public class RelatorioDAO {

    private final JdbcTemplate jdbcTemplate;

    public RelatorioDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<RelatorioItem> mediaPorAvaliacao(LocalDate dataInicio, LocalDate dataFim, String turma) {
        LocalDate ini = (dataInicio != null) ? dataInicio : LocalDate.of(2020, 1, 1);
        LocalDate fim = (dataFim != null) ? dataFim : LocalDate.of(2030, 12, 31);
        String filtroTurma = (turma != null && !turma.isBlank()) ? turma.trim() : "%";

        String sql = """
                WITH notas_avaliacao AS (
                    SELECT
                        a.id_avaliacao,
                        a.titulo,
                        a.data_aplicacao,
                        au.id_aluno,
                        u.turma,
                        COALESCE(SUM(r.nota_obtida), 0) AS nota_aluno
                    FROM avaliacao a
                    INNER JOIN avaliacao_usuario au ON au.id_avaliacao = a.id_avaliacao
                    INNER JOIN usuario u ON u.id_usuario = au.id_aluno
                    LEFT JOIN resposta r ON r.id_avaliacao = a.id_avaliacao AND r.id_aluno = au.id_aluno
                    WHERE au.status <> 'PENDENTE'
                      AND a.data_aplicacao BETWEEN ? AND ?
                      AND COALESCE(u.turma, '') LIKE ?
                    GROUP BY a.id_avaliacao, a.titulo, a.data_aplicacao, au.id_aluno, u.turma
                )
                SELECT
                    na.id_avaliacao,
                    na.titulo,
                    na.data_aplicacao,
                    COUNT(na.id_aluno) AS qtd_entregas,
                    ROUND(AVG(na.nota_aluno), 2) AS media_notas,
                    (SELECT COALESCE(SUM(aq.valor), 0) FROM avaliacao_questao aq WHERE aq.id_avaliacao = na.id_avaliacao) AS valor_maximo,
                    ROUND(
                        (AVG(na.nota_aluno) / NULLIF((SELECT SUM(aq.valor) FROM avaliacao_questao aq WHERE aq.id_avaliacao = na.id_avaliacao), 0)) * 100,
                        2
                    ) AS percentual_aproveitamento
                FROM notas_avaliacao na
                GROUP BY na.id_avaliacao, na.titulo, na.data_aplicacao
                HAVING COUNT(na.id_aluno) >= 1
                ORDER BY na.data_aplicacao ASC, na.id_avaliacao ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RelatorioItem item = new RelatorioItem();
            item.setIdAvaliacao(rs.getInt("id_avaliacao"));
            item.setTitulo(rs.getString("titulo"));
            item.setDataAplicacao(rs.getDate("data_aplicacao").toLocalDate());
            item.setQtdEntregas(rs.getLong("qtd_entregas"));
            item.setMediaNotas(rs.getBigDecimal("media_notas"));
            item.setValorMaximo(rs.getBigDecimal("valor_maximo"));
            item.setPercentualAproveitamento(rs.getBigDecimal("percentual_aproveitamento"));
            return item;
        }, Date.valueOf(ini), Date.valueOf(fim), filtroTurma);
    }

    public List<RelatorioItem> desempenhoAlunoVsTurma(int idAluno) {
        String sql = """
                WITH notas_alunos AS (
                    SELECT
                        au.id_avaliacao,
                        a.titulo,
                        a.data_aplicacao,
                        au.id_aluno,
                        u.nome AS nome_aluno,
                        u.turma,
                        COALESCE(SUM(r.nota_obtida), 0) AS nota_obtida
                    FROM avaliacao_usuario au
                    INNER JOIN avaliacao a ON a.id_avaliacao = au.id_avaliacao
                    INNER JOIN usuario u ON u.id_usuario = au.id_aluno
                    LEFT JOIN resposta r ON r.id_avaliacao = au.id_avaliacao AND r.id_aluno = au.id_aluno
                    WHERE au.status <> 'PENDENTE'
                    GROUP BY au.id_avaliacao, a.titulo, a.data_aplicacao, au.id_aluno, u.nome, u.turma
                )
                SELECT
                    na.id_avaliacao,
                    na.titulo,
                    na.data_aplicacao,
                    na.nome_aluno,
                    na.turma,
                    ROUND(na.nota_obtida, 2) AS nota_aluno,
                    (
                        SELECT ROUND(AVG(na2.nota_obtida), 2)
                        FROM notas_alunos na2
                        WHERE na2.id_avaliacao = na.id_avaliacao
                          AND na2.turma IS NOT DISTINCT FROM na.turma
                    ) AS media_turma,
                    (SELECT COALESCE(SUM(aq.valor), 0) FROM avaliacao_questao aq WHERE aq.id_avaliacao = na.id_avaliacao) AS valor_maximo
                FROM notas_alunos na
                WHERE na.id_aluno = ?
                ORDER BY na.data_aplicacao ASC, na.id_avaliacao ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RelatorioItem item = new RelatorioItem();
            item.setIdAvaliacao(rs.getInt("id_avaliacao"));
            item.setTitulo(rs.getString("titulo"));
            item.setDataAplicacao(rs.getDate("data_aplicacao").toLocalDate());
            item.setNomeAluno(rs.getString("nome_aluno"));
            item.setTurma(rs.getString("turma"));
            item.setNotaAluno(rs.getBigDecimal("nota_aluno"));
            item.setMediaTurma(rs.getBigDecimal("media_turma"));
            item.setValorMaximo(rs.getBigDecimal("valor_maximo"));
            return item;
        }, idAluno);
    }

    public List<RelatorioItem> rankingQuestoesMaisErradas() {
        String sql = """
                WITH metricas_questao AS (
                    SELECT
                        q.id_questao,
                        q.disciplina,
                        q.tipo,
                        q.enunciado,
                        COUNT(r.id_resposta) AS total_respostas,
                        ROUND(AVG((r.nota_obtida / NULLIF(aq.valor, 0)) * 100), 2) AS percentual_acerto,
                        ROUND(100 - AVG((r.nota_obtida / NULLIF(aq.valor, 0)) * 100), 2) AS percentual_erro
                    FROM resposta r
                    INNER JOIN questao q ON q.id_questao = r.id_questao
                    INNER JOIN avaliacao_questao aq ON aq.id_avaliacao = r.id_avaliacao AND aq.id_questao = r.id_questao
                    WHERE r.corrigida = TRUE
                      AND q.tipo IN ('MULTIPLA_ESCOLHA', 'TEXTO_LIVRE')
                    GROUP BY q.id_questao, q.disciplina, q.tipo, q.enunciado
                    HAVING COUNT(r.id_resposta) >= 1
                )
                SELECT
                    RANK() OVER (
                        ORDER BY percentual_erro DESC, total_respostas DESC
                    ) AS posicao,
                    id_questao,
                    disciplina,
                    tipo,
                    enunciado,
                    total_respostas,
                    percentual_acerto,
                    percentual_erro
                FROM metricas_questao
                ORDER BY percentual_erro DESC, total_respostas DESC
                FETCH FIRST 10 ROWS ONLY
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RelatorioItem item = new RelatorioItem();
            item.setPosicao(rs.getLong("posicao"));
            item.setIdQuestao(rs.getInt("id_questao"));
            item.setDisciplina(rs.getString("disciplina"));
            item.setTipo(rs.getString("tipo"));
            item.setEnunciado(rs.getString("enunciado"));
            item.setTotalRespostas(rs.getLong("total_respostas"));
            item.setPercentualAcerto(rs.getBigDecimal("percentual_acerto"));
            item.setPercentualErro(rs.getBigDecimal("percentual_erro"));
            return item;
        });
    }

    public List<RelatorioItem> resumoParticipacaoAvaliacoes() {
        String sql = """
                SELECT
                    a.id_avaliacao,
                    a.titulo,
                    a.data_aplicacao,
                    COUNT(au.id_aluno) AS total_atribuidos,
                    COUNT(CASE WHEN au.status = 'PENDENTE' THEN 1 END) AS total_pendentes,
                    COUNT(CASE WHEN au.status <> 'PENDENTE' THEN 1 END) AS total_respondidas
                FROM avaliacao a
                LEFT JOIN avaliacao_usuario au ON au.id_avaliacao = a.id_avaliacao
                GROUP BY a.id_avaliacao, a.titulo, a.data_aplicacao
                ORDER BY total_pendentes DESC, a.data_aplicacao ASC
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RelatorioItem item = new RelatorioItem();
            item.setIdAvaliacao(rs.getInt("id_avaliacao"));
            item.setTitulo(rs.getString("titulo"));
            item.setDataAplicacao(rs.getDate("data_aplicacao").toLocalDate());
            item.setTotalAtribuidos(rs.getLong("total_atribuidos"));
            item.setTotalPendentes(rs.getLong("total_pendentes"));
            item.setTotalRespondidas(rs.getLong("total_respondidas"));
            return item;
        });
    }

    public List<RelatorioItem> listarAlunosPendentes() {
        String sql = """
                SELECT
                    a.titulo AS avaliacao,
                    a.data_aplicacao,
                    u.nome AS aluno,
                    u.turma,
                    au.status
                FROM avaliacao a
                INNER JOIN avaliacao_usuario au ON au.id_avaliacao = a.id_avaliacao
                INNER JOIN usuario u ON u.id_usuario = au.id_aluno
                WHERE au.status IN ('PENDENTE')
                ORDER BY a.data_aplicacao ASC, u.turma, u.nome
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            RelatorioItem item = new RelatorioItem();
            item.setAvaliacao(rs.getString("avaliacao"));
            item.setDataAplicacao(rs.getDate("data_aplicacao").toLocalDate());
            item.setAluno(rs.getString("aluno"));
            item.setTurma(rs.getString("turma"));
            item.setStatus(rs.getString("status"));
            return item;
        });
    }
}