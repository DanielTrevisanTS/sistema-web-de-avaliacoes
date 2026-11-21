package br.uel.sistemawebavaliacoes.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoQuestao {
    private Integer idAvaliacao;
    private Integer idQuestao;
    private Integer ordem;
    private BigDecimal valor;
    private String enunciado;
    private String tipo;
    private String disciplina;
    private List<Alternativa> alternativas = new ArrayList<>();

    public Integer getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Integer idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public Integer getIdQuestao() { return idQuestao; }
    public void setIdQuestao(Integer idQuestao) { this.idQuestao = idQuestao; }

    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public List<Alternativa> getAlternativas() { return alternativas; }
    public void setAlternativas(List<Alternativa> alternativas) { this.alternativas = alternativas; }
}