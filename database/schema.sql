CREATE TABLE usuario(
    id_usuario SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    perfil VARCHAR(20) NOT NULL CHECK (perfil IN ('PROFESSOR', 'ALUNO')),
    turma VARCHAR(30)
);

CREATE TABLE questao(
    id_questao SERIAL PRIMARY KEY,
    enunciado TEXT NOT NULL,
    tipo VARCHAR(20) NOT NULL CHECK (tipo IN ('MULTIPLA_ESCOLHA', 'TEXTO_LIVRE')),
    disciplina VARCHAR(60) NOT NULL,
    id_professor INT NOT NULL REFERENCES usuario(id_usuario) ON DELETE RESTRICT
);

CREATE TABLE alternativa(
    id_alternativa SERIAL PRIMARY KEY,
    id_questao INT NOT NULL REFERENCES questao(id_questao) ON DELETE CASCADE,
    texto TEXT NOT NULL,
    correta BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_alternativa_questao_texto UNIQUE (id_questao, texto)
);

CREATE TABLE avaliacao(
    id_avaliacao SERIAL PRIMARY KEY,
    titulo VARCHAR(120) NOT NULL,
    descricao TEXT,
    data_aplicacao DATE NOT NULL,
    hora_inicio TIME NOT NULL,
    hora_fim TIME NOT NULL,
    id_professor INT NOT NULL REFERENCES usuario(id_usuario) ON DELETE RESTRICT,
    CONSTRAINT chk_horario_valido CHECK (hora_fim > hora_inicio)
);

CREATE TABLE avaliacao_questao(
    id_avaliacao INT NOT NULL REFERENCES avaliacao(id_avaliacao) ON DELETE CASCADE,
    id_questao INT NOT NULL REFERENCES questao(id_questao) ON DELETE RESTRICT,
    ordem INT NOT NULL CHECK (ordem > 0),
    valor NUMERIC(5,2) NOT NULL CHECK (valor > 0),
    PRIMARY KEY (id_avaliacao, id_questao),
    CONSTRAINT uq_avaliacao_ordem UNIQUE (id_avaliacao, ordem)
);

CREATE TABLE avaliacao_usuario(
    id_avaliacao INT NOT NULL REFERENCES avaliacao(id_avaliacao) ON DELETE CASCADE,
    id_aluno INT NOT NULL REFERENCES usuario(id_usuario) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDENTE' CHECK (status IN ('PENDENTE', 'RESPONDIDA', 'CORRIGIDA')),
    data_submissao TIMESTAMP,
    PRIMARY KEY (id_avaliacao, id_aluno)
);

CREATE TABLE resposta(
    id_resposta SERIAL PRIMARY KEY,
    id_avaliacao INT NOT NULL,
    id_aluno INT NOT NULL,
    id_questao INT NOT NULL,
    id_alternativa_escolhida INT REFERENCES alternativa(id_alternativa) ON DELETE RESTRICT,
    texto_resposta TEXT,
    nota_obtida NUMERIC(5,2) CHECK (nota_obtida >= 0),
    corrigida BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_resposta_participacao
        FOREIGN KEY (id_avaliacao, id_aluno) REFERENCES avaliacao_usuario(id_avaliacao, id_aluno) ON DELETE CASCADE,
    CONSTRAINT fk_resposta_item_avaliacao
        FOREIGN KEY (id_avaliacao, id_questao) REFERENCES avaliacao_questao(id_avaliacao, id_questao) ON DELETE RESTRICT,
    CONSTRAINT uq_resposta_unica UNIQUE (id_avaliacao, id_aluno, id_questao),
    CONSTRAINT chk_resposta_preenchida CHECK (id_alternativa_escolhida IS NOT NULL OR texto_resposta IS NOT NULL)
);