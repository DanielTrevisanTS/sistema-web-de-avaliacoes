package br.uel.sistemawebavaliacoes.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RelatorioItem {

    private Integer idAvaliacao;
    private Integer idQuestao;
    private Long posicao;
    private String titulo;
    private String avaliacao;
    private String disciplina;
    private String tipo;
    private String enunciado;
    private String nomeAluno;
    private String aluno;
    private String turma;
    private String status;
    private LocalDate dataAplicacao;
    private Long qtdEntregas;
    private Long totalRespostas;
    private Long totalAtribuidos;
    private Long totalPendentes;
    private Long totalRespondidas;
    private BigDecimal mediaNotas;
    private BigDecimal valorMaximo;
    private BigDecimal percentualAproveitamento;
    private BigDecimal notaAluno;
    private BigDecimal mediaTurma;
    private BigDecimal percentualAcerto;
    private BigDecimal percentualErro;

    public Integer getIdAvaliacao() { return idAvaliacao; }
    public void setIdAvaliacao(Integer idAvaliacao) { this.idAvaliacao = idAvaliacao; }

    public Integer getIdQuestao() { return idQuestao; }
    public void setIdQuestao(Integer idQuestao) { this.idQuestao = idQuestao; }

    public Long getPosicao() { return posicao; }
    public void setPosicao(Long posicao) { this.posicao = posicao; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAvaliacao() { return avaliacao; }
    public void setAvaliacao(String avaliacao) { this.avaliacao = avaliacao; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String enunciado) { this.enunciado = enunciado; }

    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }

    public String getAluno() { return aluno; }
    public void setAluno(String aluno) { this.aluno = aluno; }

    public String getTurma() { return turma; }
    public void setTurma(String turma) { this.turma = turma; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getDataAplicacao() { return dataAplicacao; }
    public void setDataAplicacao(LocalDate dataAplicacao) { this.dataAplicacao = dataAplicacao; }

    public Long getQtdEntregas() { return qtdEntregas; }
    public void setQtdEntregas(Long qtdEntregas) { this.qtdEntregas = qtdEntregas; }

    public Long getTotalRespostas() { return totalRespostas; }
    public void setTotalRespostas(Long totalRespostas) { this.totalRespostas = totalRespostas; }

    public Long getTotalAtribuidos() { return totalAtribuidos; }
    public void setTotalAtribuidos(Long totalAtribuidos) { this.totalAtribuidos = totalAtribuidos; }

    public Long getTotalPendentes() { return totalPendentes; }
    public void setTotalPendentes(Long totalPendentes) { this.totalPendentes = totalPendentes; }

    public Long getTotalRespondidas() { return totalRespondidas; }
    public void setTotalRespondidas(Long totalRespondidas) { this.totalRespondidas = totalRespondidas; }

    public BigDecimal getMediaNotas() { return mediaNotas; }
    public void setMediaNotas(BigDecimal mediaNotas) { this.mediaNotas = mediaNotas; }

    public BigDecimal getValorMaximo() { return valorMaximo; }
    public void setValorMaximo(BigDecimal valorMaximo) { this.valorMaximo = valorMaximo; }

    public BigDecimal getPercentualAproveitamento() { return percentualAproveitamento; }
    public void setPercentualAproveitamento(BigDecimal percentualAproveitamento) { this.percentualAproveitamento = percentualAproveitamento; }

    public BigDecimal getNotaAluno() { return notaAluno; }
    public void setNotaAluno(BigDecimal notaAluno) { this.notaAluno = notaAluno; }

    public BigDecimal getMediaTurma() { return mediaTurma; }
    public void setMediaTurma(BigDecimal mediaTurma) { this.mediaTurma = mediaTurma; }

    public BigDecimal getPercentualAcerto() { return percentualAcerto; }
    public void setPercentualAcerto(BigDecimal percentualAcerto) { this.percentualAcerto = percentualAcerto; }

    public BigDecimal getPercentualErro() { return percentualErro; }
    public void setPercentualErro(BigDecimal percentualErro) { this.percentualErro = percentualErro; }
}