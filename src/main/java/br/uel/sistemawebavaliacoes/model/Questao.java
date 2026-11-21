package br.uel.sistemawebavaliacoes.model;

import java.util.ArrayList;
import java.util.List;

public class Questao {
    private Integer idQuestao;
    private String enunciado;
    private String tipo;
    private String disciplina;
    private Integer idProfessor;
    private String nomeProfessor;
    private List<Alternativa> alternativas = new ArrayList<>();

    public Integer getIdQuestao() { return idQuestao; }
    public void setIdQuestao(Integer idQuestao) { this.idQuestao = idQuestao; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public Integer getIdProfessor() { return idProfessor; }
    public void setIdProfessor(Integer idProfessor) { this.idProfessor = idProfessor; }

    public String getNomeProfessor() { return nomeProfessor; }
    public void setNomeProfessor(String nomeProfessor) { this.nomeProfessor = nomeProfessor; }

    public List<Alternativa> getAlternativas() { return alternativas; }
    public void setAlternativas(List<Alternativa> alternativas) { this.alternativas = alternativas; }
}