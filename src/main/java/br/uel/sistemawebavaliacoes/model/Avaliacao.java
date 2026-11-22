package br.uel.sistemawebavaliacoes.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Avaliacao {
    private Integer idAvaliacao;
    private String titulo;
    private String descricao;
    private LocalDate dataAplicacao;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private Integer idProfessor;
    private String nomeProfessor;
    private BigDecimal valorTotal;
    private Integer totalQuestoes;
    private Integer totalAlunos;
    private String statusAluno;
    private LocalDateTime dataSubmissao;
    private BigDecimal notaFinal;
    private List<AvaliacaoQuestao> questoes = new ArrayList<>();
    private List<Integer> alunosIds = new ArrayList<>();
    private String turmaAtribuida;

    public Integer getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Integer idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }

    public LocalTime getHoraInicio() { return horaInicio; }
    public void setHoraInicio(LocalTime horaInicio) { this.horaInicio = horaInicio; }

    public LocalTime getHoraFim() { return horaFim; }
    public void setHoraFim(LocalTime horaFim) { this.horaFim = horaFim; }

    public Integer getIdProfessor() { return idProfessor; }
    public void setIdProfessor(Integer idProfessor) { this.idProfessor = idProfessor; }

    public String getNomeProfessor() { return nomeProfessor; }
    public void setNomeProfessor(String nomeProfessor) { this.nomeProfessor = nomeProfessor; }

    public BigDecimal getValorTotal() { return valorTotal; }
    public void setValorTotal(BigDecimal valorTotal) { this.valorTotal = valorTotal; }

    public String getStatusAluno() { return statusAluno; }
    public void setStatusAluno(String statusAluno) { this.statusAluno = statusAluno; }

    public LocalDateTime getDataSubmissao() { return dataSubmissao; }
    public void setDataSubmissao(LocalDateTime dataSubmissao) { this.dataSubmissao = dataSubmissao; }

    public BigDecimal getNotaFinal() { return notaFinal; }
    public void setNotaFinal(BigDecimal notaFinal) { this.notaFinal = notaFinal; }

    public Integer getTotalQuestoes() { return totalQuestoes; }
    public void setTotalQuestoes(Integer totalQuestoes) { this.totalQuestoes = totalQuestoes; }

    public Integer getTotalAlunos() { return totalAlunos; }
    public void setTotalAlunos(Integer totalAlunos) { this.totalAlunos = totalAlunos; }

    public List<AvaliacaoQuestao> getQuestoes() { return questoes; }
    public void setQuestoes(List<AvaliacaoQuestao> questoes) { this.questoes = questoes; }

    public List<Integer> getAlunosIds() { return alunosIds; }
    public void setAlunosIds(List<Integer> alunosIds) { this.alunosIds = alunosIds; }

    public String getTurmaAtribuida() { return turmaAtribuida; }
    public void setTurmaAtribuida(String turmaAtribuida) { this.turmaAtribuida = turmaAtribuida; }
}