package br.uel.sistemawebavaliacoes.model;

public class Alternativa {
    private Integer idAlternativa;
    private Integer idQuestao;
    private String texto;
    private boolean correta;

    public Integer getIdAlternativa() { return idAlternativa; }
    public void setIdAlternativa(Integer idAlternativa) { this.idAlternativa = idAlternativa; }

    public Integer getIdQuestao() { return idQuestao; }
    public void setIdQuestao(Integer idQuestao) { this.idQuestao = idQuestao; }

    public String getTexto() { return texto; }
    public void setTexto(String texto) { this.texto = texto; }

    public boolean isCorreta() { return correta; }
    public void setCorreta(boolean correta) { this.correta = correta; }
}