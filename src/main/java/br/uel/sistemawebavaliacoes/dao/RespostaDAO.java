package br.uel.sistemawebavaliacoes.dao;

import br.uel.sistemawebavaliacoes.model.Resposta;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RespostaDAO {

    private final JdbcTemplate jdbcTemplate;

    public RespostaDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    public void submeterAvaliacao(int idAvaliacao, int idAluno, List<Resposta> respostasEnviadas) {
        List<String> statusList = jdbcTemplate.queryForList(
                "SELECT status FROM avaliacao_usuario WHERE id_avaliacao = ? AND id_aluno = ?",
                String.class, idAvaliacao, idAluno);

        if (statusList.isEmpty()) {
            throw new IllegalStateException("Esta avaliação não está atribuída a você.");
        }
        if (!"PENDENTE".equals(statusList.get(0))) {
            throw new IllegalStateException("Esta avaliação já foi respondida.");
        }

        String sqlItens = """
                SELECT aq.id_questao, aq.valor, q.tipo
                FROM avaliacao_questao aq
                JOIN questao q ON q.id_questao = aq.id_questao
                WHERE aq.id_avaliacao = ?
                """;

        record ItemMeta(BigDecimal valor, String tipo) {}
        Map<Integer, ItemMeta> mapaQuestoes = new HashMap<>();
        jdbcTemplate.query(sqlItens, rs -> {
            mapaQuestoes.put(
                    rs.getInt("id_questao"),
                    new ItemMeta(rs.getBigDecimal("valor"), rs.getString("tipo"))
            );
        }, idAvaliacao);

        if (mapaQuestoes.isEmpty()) {
            throw new IllegalStateException("Esta avaliação não possui questões cadastradas.");
        }

        Map<Integer, Resposta> mapaEnviadas = new HashMap<>();
        if (respostasEnviadas != null) {
            for (Resposta r : respostasEnviadas) {
                mapaEnviadas.put(r.getIdQuestao(), r);
            }
        }

        // Validação: todas as questões (objetivas e discursivas) devem ser respondidas
        for (Map.Entry<Integer, ItemMeta> entry : mapaQuestoes.entrySet()) {
            Resposta resp = mapaEnviadas.get(entry.getKey());
            if ("MULTIPLA_ESCOLHA".equals(entry.getValue().tipo())) {
                if (resp == null || resp.getIdAlternativaEscolhida() == null) {
                    throw new IllegalArgumentException("Todas as questões de múltipla escolha devem ser respondidas antes do envio.");
                }
            } else {
                if (resp == null || resp.getTextoResposta() == null || resp.getTextoResposta().isBlank()) {
                    throw new IllegalArgumentException("Todas as questões discursivas devem ser preenchidas antes do envio.");
                }
            }
        }

        boolean possuiDiscursiva = false;
        String sqlInsert = """
                INSERT INTO resposta (id_avaliacao, id_aluno, id_questao, id_alternativa_escolhida, texto_resposta, nota_obtida, corrigida)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        for (Map.Entry<Integer, ItemMeta> entry : mapaQuestoes.entrySet()) {
            int idQuestao = entry.getKey();
            ItemMeta meta = entry.getValue();
            Resposta resp = mapaEnviadas.get(idQuestao);

            if ("MULTIPLA_ESCOLHA".equals(meta.tipo())) {
                Integer idAlt = resp.getIdAlternativaEscolhida();
                Integer pertenceCount = jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM alternativa WHERE id_alternativa = ? AND id_questao = ?",
                        Integer.class, idAlt, idQuestao);
                if (pertenceCount == null || pertenceCount == 0) {
                    throw new IllegalArgumentException("Alternativa inválida selecionada para a questão #" + idQuestao + ".");
                }

                Integer corretaCount = jdbcTemplate.queryForObject(
                        "SELECT COUNT(*) FROM alternativa WHERE id_alternativa = ? AND id_questao = ? AND correta = TRUE",
                        Integer.class, idAlt, idQuestao);
                boolean acertou = (corretaCount != null && corretaCount > 0);

                BigDecimal nota = acertou ? meta.valor() : BigDecimal.ZERO;
                jdbcTemplate.update(sqlInsert, idAvaliacao, idAluno, idQuestao, idAlt, null, nota, true);
            } else {
                possuiDiscursiva = true;
                String textoLimpo = resp.getTextoResposta().trim();
                jdbcTemplate.update(sqlInsert, idAvaliacao, idAluno, idQuestao, null, textoLimpo, null, false);
            }
        }

        String novoStatus = possuiDiscursiva ? "RESPONDIDA" : "CORRIGIDA";
        jdbcTemplate.update(
                "UPDATE avaliacao_usuario SET status = ?, data_submissao = CURRENT_TIMESTAMP WHERE id_avaliacao = ? AND id_aluno = ?",
                novoStatus, idAvaliacao, idAluno);
    }

    public List<Resposta> listarDiscursivasPendentes() {
        String sql = """
                SELECT r.id_resposta, r.id_avaliacao, r.id_aluno, r.id_questao, r.texto_resposta, r.nota_obtida, r.corrigida,
                       u.nome AS nome_aluno, u.turma AS turma_aluno,
                       a.titulo AS titulo_avaliacao,
                       q.enunciado, aq.valor AS valor_maximo
                FROM resposta r
                JOIN usuario u ON u.id_usuario = r.id_aluno
                JOIN avaliacao a ON a.id_avaliacao = r.id_avaliacao
                JOIN questao q ON q.id_questao = r.id_questao
                JOIN avaliacao_questao aq ON aq.id_avaliacao = r.id_avaliacao AND aq.id_questao = r.id_questao
                WHERE q.tipo = 'TEXTO_LIVRE' AND r.corrigida = FALSE
                ORDER BY a.id_avaliacao, u.nome, aq.ordem
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Resposta r = new Resposta();
            r.setIdResposta(rs.getInt("id_resposta"));
            r.setIdAvaliacao(rs.getInt("id_avaliacao"));
            r.setIdAluno(rs.getInt("id_aluno"));
            r.setIdQuestao(rs.getInt("id_questao"));
            r.setTextoResposta(rs.getString("texto_resposta"));
            r.setNotaObtida(rs.getBigDecimal("nota_obtida"));
            r.setCorrigida(rs.getBoolean("corrigida"));
            r.setNomeAluno(rs.getString("nome_aluno"));
            r.setTurmaAluno(rs.getString("turma_aluno"));
            r.setTituloAvaliacao(rs.getString("titulo_avaliacao"));
            r.setEnunciado(rs.getString("enunciado"));
            r.setValorMaximo(rs.getBigDecimal("valor_maximo"));
            return r;
        });
    }

    @Transactional
    public void corrigirDiscursiva(int idResposta, BigDecimal nota) {
        String sqlMeta = """
                SELECT r.id_avaliacao, r.id_aluno, aq.valor
                FROM resposta r
                JOIN avaliacao_questao aq ON aq.id_avaliacao = r.id_avaliacao AND aq.id_questao = r.id_questao
                WHERE r.id_resposta = ?
                """;

        List<Map<String, Object>> metas = jdbcTemplate.queryForList(sqlMeta, idResposta);
        if (metas.isEmpty()) {
            throw new IllegalArgumentException("Resposta não encontrada.");
        }
        Map<String, Object> meta = metas.get(0);
        BigDecimal valorMaximo = (BigDecimal) meta.get("valor");
        int idAvaliacao = (Integer) meta.get("id_avaliacao");
        int idAluno = (Integer) meta.get("id_aluno");

        if (nota == null || nota.compareTo(BigDecimal.ZERO) < 0 || nota.compareTo(valorMaximo) > 0) {
            throw new IllegalArgumentException("A nota deve estar entre 0 e " + valorMaximo + " pontos.");
        }

        jdbcTemplate.update(
                "UPDATE resposta SET nota_obtida = ?, corrigida = TRUE WHERE id_resposta = ?",
                nota, idResposta);

        Integer pendentes = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM resposta WHERE id_avaliacao = ? AND id_aluno = ? AND corrigida = FALSE",
                Integer.class, idAvaliacao, idAluno);

        if (pendentes != null && pendentes == 0) {
            jdbcTemplate.update(
                    "UPDATE avaliacao_usuario SET status = 'CORRIGIDA' WHERE id_avaliacao = ? AND id_aluno = ?",
                    idAvaliacao, idAluno);
        }
    }

    public List<Resposta> listarBoletimAluno(int idAvaliacao, int idAluno) {
        String sql = """
                SELECT r.id_resposta, r.id_questao, r.texto_resposta, r.nota_obtida, r.corrigida,
                       q.enunciado, q.tipo AS tipo_questao, aq.valor AS valor_maximo,
                       alt.texto AS texto_alternativa
                FROM resposta r
                JOIN questao q ON q.id_questao = r.id_questao
                JOIN avaliacao_questao aq ON aq.id_avaliacao = r.id_avaliacao AND aq.id_questao = r.id_questao
                LEFT JOIN alternativa alt ON alt.id_alternativa = r.id_alternativa_escolhida
                WHERE r.id_avaliacao = ? AND r.id_aluno = ?
                ORDER BY aq.ordem
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Resposta r = new Resposta();
            r.setIdResposta(rs.getInt("id_resposta"));
            r.setIdQuestao(rs.getInt("id_questao"));
            r.setTextoResposta(rs.getString("texto_resposta"));
            r.setNotaObtida(rs.getBigDecimal("nota_obtida"));
            r.setCorrigida(rs.getBoolean("corrigida"));
            r.setEnunciado(rs.getString("enunciado"));
            r.setTipoQuestao(rs.getString("tipo_questao"));
            r.setValorMaximo(rs.getBigDecimal("valor_maximo"));
            r.setTextoAlternativaEscolhida(rs.getString("texto_alternativa"));
            return r;
        }, idAvaliacao, idAluno);
    }
}