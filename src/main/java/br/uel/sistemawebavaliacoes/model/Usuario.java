package br.uel.sistemawebavaliacoes.model;

public class Usuario {
    private Integer idUsuario;
    private String nome;
    private String email;
    private String senha;
    private String perfil;
    private String turma;

    public Integer getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Integer idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getPerfil() { return perfil; }
    public void setPerfil(String perfil) { this.perfil = perfil; }

    public String getTurma() { return turma; }
    public void setTurma(String turma) { this.turma = turma; }
}
