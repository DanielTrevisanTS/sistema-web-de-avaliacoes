package br.uel.sistemawebavaliacoes.model;

import java.math.BigDecimal;

public class Resposta {
    private Integer idResposta;
    private Integer idAvaliacao;
    private Integer idAluno;
    private Integer idQuestao;
    private Integer idAlternativaEscolhida;
    private String textoResposta;
    private BigDecimal notaObtida;
    private boolean corrigida;

    private String nomeAluno;
    private String turmaAluno;
    private String tituloAvaliacao;
    private String enunciado;
    private String tipoQuestao;
    private BigDecimal valorMaximo;
    private String textoAlternativaEscolhida;

    public Integer getIdResposta() { return idResposta; }
    public void setIdResposta(Integer idResposta) { this.idResposta = idResposta; }

    public Integer getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Integer idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public Integer getIdAluno() { return idAluno; }
    public void setIdAluno(Integer idAluno) { this.idAluno = idAluno; }

    public Integer getIdQuestao() { return idQuestao; }
    public void setIdQuestao(Integer idQuestao) { this.idQuestao = idQuestao; }

    public Integer getIdAlternativaEscolhida() { return idAlternativaEscolhida; }
    public void setIdAlternativaEscolhida(Integer idAlternativaEscolhida) { this.idAlternativaEscolhida = idAlternativaEscolhida; }

    public String getTextoResposta() { return textoResposta; }
    public void setTextoResposta(String textoResposta) { this.textoResposta = textoResposta; }

    public BigDecimal getNotaObtida() { return notaObtida; }
    public void setNotaObtida(BigDecimal notaObtida) { this.notaObtida = notaObtida; }

    public boolean isCorrigida() { return corrigida; }
    public void setCorrigida(boolean corrigida) { this.corrigida = corrigida; }

    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }

    public String getTurmaAluno() { return turmaAluno; }
    public void setTurmaAluno(String turmaAluno) { this.turmaAluno = turmaAluno; }

    public String getTituloAvaliacao() { return tituloAvaliacao; }
    public void setTituloAvaliacao(String tituloAvaliacao) { this.tituloAvaliacao = tituloAvaliacao; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public String getTipoQuestao() { return tipoQuestao; }
    public void setTipoQuestao(String tipoQuestao) { this.tipoQuestao = tipoQuestao; }

    public BigDecimal getValorMaximo() { return valorMaximo; }
    public void setValorMaximo(BigDecimal valorMaximo) { this.valorMaximo = valorMaximo; }

    public String getTextoAlternativaEscolhida() { return textoAlternativaEscolhida; }
    public void setTextoAlternativaEscolhida(String textoAlternativaEscolhida) { this.textoAlternativaEscolhida = textoAlternativaEscolhida; }
}