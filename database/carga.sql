INSERT INTO usuario (id_usuario, nome, email, senha, perfil, turma) VALUES
    (1,  'Prof. Carlos Mendes',   'carlos.mendes@uel.br',   '123456', 'PROFESSOR', NULL),
    (2,  'Profa. Mariana Costa',  'mariana.costa@uel.br',   '123456', 'PROFESSOR', NULL),
    (3,  'Prof. Ricardo Alves',   'ricardo.alves@uel.br',   '123456', 'PROFESSOR', NULL),
    (4,  'Ana Clara Silva',       'ana.silva@uel.br',       '123456', 'ALUNO', 'CC-2026-A'),
    (5,  'Bruno Oliveira',        'bruno.oliveira@uel.br',  '123456', 'ALUNO', 'CC-2026-A'),
    (6,  'Camila Santos',         'camila.santos@uel.br',   '123456', 'ALUNO', 'CC-2026-A'),
    (7,  'Daniel Trevisan',       'daniel.trevisan@uel.br', '123456', 'ALUNO', 'CC-2026-A'),
    (8,  'Eduarda Lima',          'eduarda.lima@uel.br',    '123456', 'ALUNO', 'CC-2026-A'),
    (9,  'Felipe Rocha',          'felipe.rocha@uel.br',    '123456', 'ALUNO', 'CC-2026-A'),
    (10, 'Gabriela Martins',      'gabriela.martins@uel.br','123456', 'ALUNO', 'CC-2026-A'),
    (11, 'Henrique Souza',        'henrique.souza@uel.br',  '123456', 'ALUNO', 'CC-2026-A'),
    (12, 'Isabela Ferreira',      'isabela.ferreira@uel.br','123456', 'ALUNO', 'CC-2026-A'),
    (13, 'João Pedro Nunes',      'joao.nunes@uel.br',      '123456', 'ALUNO', 'CC-2026-A'),
    (14, 'Larissa Gomes',         'larissa.gomes@uel.br',   '123456', 'ALUNO', 'CC-2026-B'),
    (15, 'Lucas Ribeiro',         'lucas.ribeiro@uel.br',   '123456', 'ALUNO', 'CC-2026-B'),
    (16, 'Mateus Carvalho',       'mateus.carvalho@uel.br', '123456', 'ALUNO', 'CC-2026-B'),
    (17, 'Natália Barbosa',       'natalia.barbosa@uel.br', '123456', 'ALUNO', 'CC-2026-B'),
    (18, 'Otávio Pereira',        'otavio.pereira@uel.br',  '123456', 'ALUNO', 'CC-2026-B'),
    (19, 'Paula Castro',          'paula.castro@uel.br',    '123456', 'ALUNO', 'CC-2026-B'),
    (20, 'Rafael Dias',           'rafael.dias@uel.br',     '123456', 'ALUNO', 'CC-2026-B'),
    (21, 'Sofia Almeida',         'sofia.almeida@uel.br',   '123456', 'ALUNO', 'CC-2026-B'),
    (22, 'Thiago Fernandes',      'thiago.fernandes@uel.br','123456', 'ALUNO', 'CC-2026-B'),
    (23, 'Vitória Cardoso',       'vitoria.cardoso@uel.br', '123456', 'ALUNO', 'CC-2026-B');


INSERT INTO questao (id_questao, enunciado, tipo, disciplina, id_professor) VALUES
    (1,  'Qual comando SQL DDL é utilizado para criar uma nova tabela no banco de dados?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (2,  'Qual restrição garante que todos os valores de uma coluna sejam distintos entre si?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (3,  'Em normalização, o que caracteriza uma relação na Terceira Forma Normal (3FN)?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (4,  'Qual cláusula SQL é usada para filtrar grupos formados pelo GROUP BY?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (5,  'Qual tipo de junção retorna todas as linhas da tabela à esquerda mesmo sem correspondência à direita?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (6,  'Qual comando DML remove registros específicos de uma tabela respeitando uma condição WHERE?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (7,  'O que significa a propriedade Atomicidade (A) nas transações ACID?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (8,  'Qual função de agregação calcula a média aritmética de uma coluna numérica?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (9,  'No Modelo Entidade-Relacionamento (MER), o que representa um losango?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 2),
    (10, 'Quando utilizamos uma entidade associativa (agregação) no MER?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 2),
    (11, 'Como é mapeado um relacionamento N:M no modelo relacional?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 2),
    (12, 'O que indica a cardinalidade mínima (0,N) de uma entidade A em relação a B?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 2),
    (13, 'Qual atributo deve ser sublinhado em uma entidade no diagrama de Peter Chen?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 2),
    (14, 'Qual nível de isolamento evita leituras sujas (Dirty Reads) no PostgreSQL?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (15, 'Qual estrutura de dados é padrão para índices criados com CREATE INDEX no PostgreSQL?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (16, 'Qual comando encerra uma transação confirmando definitivamente suas alterações?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (17, 'Qual comando desfaz todas as operações realizadas dentro de uma transação ativa?', 'MULTIPLA_ESCOLHA', 'Banco de Dados', 1),
    (18, 'Na arquitetura em camadas, qual é a responsabilidade principal da camada DAO?', 'MULTIPLA_ESCOLHA', 'Engenharia de Software', 3),
    (19, 'Qual interface JDBC é recomendada para executar consultas SQL parametrizadas evitando SQL Injection?', 'MULTIPLA_ESCOLHA', 'Engenharia de Software', 3),
    (20, 'Qual método HTTP é semanticamente mais adequado para cadastrar um novo recurso em uma API REST?', 'MULTIPLA_ESCOLHA', 'Engenharia de Software', 3),
    (21, 'Qual código de status HTTP indica que um recurso foi criado com sucesso?', 'MULTIPLA_ESCOLHA', 'Engenharia de Software', 3),
    (22, 'Em React, qual hook gerencia o estado local de um componente funcional?', 'MULTIPLA_ESCOLHA', 'Engenharia de Software', 3),
    (23, 'Qual padrão de projeto separa a representação visual da lógica de controle e acesso a dados?', 'MULTIPLA_ESCOLHA', 'Engenharia de Software', 3),
    (24, 'Qual comando Git envia os commits locais para o repositório remoto?', 'MULTIPLA_ESCOLHA', 'Engenharia de Software', 3),
    (25, 'Explique com suas palavras a diferença entre chave primária (PRIMARY KEY) e chave estrangeira (FOREIGN KEY).', 'TEXTO_LIVRE', 'Banco de Dados', 1),
    (26, 'Descreva um cenário prático em que o controle de transações com COMMIT e ROLLBACK é indispensável.', 'TEXTO_LIVRE', 'Banco de Dados', 1),
    (27, 'Por que não devemos escrever comandos SQL diretamente nas telas ou nos controllers da aplicação?', 'TEXTO_LIVRE', 'Engenharia de Software', 3),
    (28, 'Explique a diferença entre INNER JOIN e FULL OUTER JOIN com um exemplo.', 'TEXTO_LIVRE', 'Banco de Dados', 1),
    (29, 'Quais são as vantagens de utilizar PreparedStatement em relação a Statement no JDBC?', 'TEXTO_LIVRE', 'Engenharia de Software', 3),
    (30, 'Defina dependência funcional parcial e explique como ela é eliminada na 2FN.', 'TEXTO_LIVRE', 'Banco de Dados', 2);


INSERT INTO alternativa (id_alternativa, id_questao, texto, correta) VALUES
-- Q1 (Correta: 2)
(1,  1, 'INSERT TABLE', FALSE),
(2,  1, 'CREATE TABLE', TRUE),
(3,  1, 'ALTER TABLE', FALSE),
(4,  1, 'NEW TABLE', FALSE),
-- Q2 (Correta: 5)
(5,  2, 'UNIQUE', TRUE),
(6,  2, 'NOT NULL', FALSE),
(7,  2, 'CHECK', FALSE),
(8,  2, 'DEFAULT', FALSE),
-- Q3 (Correta: 11)
(9,  3, 'Possuir apenas atributos multivalorados', FALSE),
(10, 3, 'Não possuir chave primária composta', FALSE),
(11, 3, 'Estar na 2FN e não possuir dependências transitivas entre atributos não-chave', TRUE),
(12, 3, 'Eliminar todas as chaves estrangeiras da tabela', FALSE),
-- Q4 (Correta: 13)
(13, 4, 'HAVING', TRUE),
(14, 4, 'WHERE', FALSE),
(15, 4, 'ORDER BY', FALSE),
(16, 4, 'DISTINCT', FALSE),
-- Q5 (Correta: 20)
(17, 5, 'INNER JOIN', FALSE),
(18, 5, 'RIGHT JOIN', FALSE),
(19, 5, 'CROSS JOIN', FALSE),
(20, 5, 'LEFT JOIN', TRUE),
-- Q6 (Correta: 22)
(21, 6, 'DROP', FALSE),
(22, 6, 'DELETE', TRUE),
(23, 6, 'TRUNCATE', FALSE),
(24, 6, 'REMOVE', FALSE),
-- Q7 (Correta: 25)
(25, 7, 'Todas as operações da transação ocorrem por completo ou nenhuma é efetivada', TRUE),
(26, 7, 'Os dados permanecem gravados mesmo após queda de energia', FALSE),
(27, 7, 'Duas transações nunca acessam a mesma tabela', FALSE),
(28, 7, 'O banco compacta os índices automaticamente', FALSE),
-- Q8 (Correta: 31)
(29, 8, 'SUM()', FALSE),
(30, 8, 'COUNT()', FALSE),
(31, 8, 'AVG()', TRUE),
(32, 8, 'MEDIAN()', FALSE),
-- Q9 (Correta: 33)
(33, 9, 'Um relacionamento entre entidades', TRUE),
(34, 9, 'Um atributo identificador', FALSE),
(35, 9, 'Uma entidade fraca', FALSE),
(36, 9, 'Uma restrição de domínio', FALSE),
-- Q10 (Correta: 38)
(37, 10, 'Sempre que uma entidade tiver mais de cinco atributos', FALSE),
(38, 10, 'Quando um relacionamento N:M precisa se associar a outra entidade', TRUE),
(39, 10, 'Para substituir chaves primárias simples', FALSE),
(40, 10, 'Apenas em relacionamentos unários 1:1', FALSE),
-- Q11 (Correta: 41)
(41, 11, 'Criando uma nova tabela associativa com as chaves estrangeiras das entidades envolvidas', TRUE),
(42, 11, 'Adicionando a chave primária de um lado no outro aleatoriamente', FALSE),
(43, 11, 'Unindo as duas tabelas em uma única relação', FALSE),
(44, 11, 'Não é possível mapear relacionamentos N:M', FALSE),
-- Q12 (Correta: 47)
(45, 12, 'A participação de A é obrigatória', FALSE),
(46, 12, 'A pode se ligar a no máximo uma instância de B', FALSE),
(47, 12, 'A participação de A é opcional e pode se ligar a várias instâncias de B', TRUE),
(48, 12, 'B não possui chave primária', FALSE),
-- Q13 (Correta: 49)
(49, 13, 'O atributo identificador (chave primária)', TRUE),
(50, 13, 'Qualquer atributo opcional', FALSE),
(51, 13, 'Apenas atributos derivados', FALSE),
(52, 13, 'Todos os atributos textuais', FALSE),
-- Q14 (Correta: 54)
(53, 14, 'READ UNCOMMITTED', FALSE),
(54, 14, 'READ COMMITTED', TRUE),
(55, 14, 'NO ISOLATION', FALSE),
(56, 14, 'DIRTY READ ONLY', FALSE),
-- Q15 (Correta: 57)
(57, 15, 'B-Tree (Árvore B)', TRUE),
(58, 15, 'Pilha (LIFO)', FALSE),
(59, 15, 'Fila Circular', FALSE),
(60, 15, 'Lista Encadeada Simples', FALSE),
-- Q16 (Correta: 63)
(61, 16, 'SAVEPOINT', FALSE),
(62, 16, 'ROLLBACK', FALSE),
(63, 16, 'COMMIT', TRUE),
(64, 16, 'GRANT', FALSE),
-- Q17 (Correta: 65)
(65, 17, 'ROLLBACK', TRUE),
(66, 17, 'COMMIT', FALSE),
(67, 17, 'REVOKE', FALSE),
(68, 17, 'DROP TRANSACTION', FALSE),
-- Q18 (Correta: 70)
(69, 18, 'Renderizar gráficos na interface do usuário', FALSE),
(70, 18, 'Encapsular o acesso ao banco de dados e as operações de persistência', TRUE),
(71, 18, 'Gerenciar rotas HTTP e sessões web', FALSE),
(72, 18, 'Compilar folhas de estilo CSS', FALSE),
-- Q19 (Correta: 73)
(73, 19, 'PreparedStatement', TRUE),
(74, 19, 'ResultSetMetaData', FALSE),
(75, 19, 'DriverManager', FALSE),
(76, 19, 'SQLException', FALSE),
-- Q20 (Correta: 79)
(77, 20, 'GET', FALSE),
(78, 20, 'DELETE', FALSE),
(79, 20, 'POST', TRUE),
(80, 20, 'HEAD', FALSE),
-- Q21 (Correta: 81)
(81, 21, '201 Created', TRUE),
(82, 21, '404 Not Found', FALSE),
(83, 21, '500 Internal Server Error', FALSE),
(84, 21, '403 Forbidden', FALSE),
-- Q22 (Correta: 86)
(85, 22, 'useMemo', FALSE),
(86, 22, 'useState', TRUE),
(87, 22, 'useRef', FALSE),
(88, 22, 'useId', FALSE),
-- Q23 (Correta: 89)
(89, 23, 'MVC (Model-View-Controller)', TRUE),
(90, 23, 'Singleton', FALSE),
(91, 23, 'Flyweight', FALSE),
(92, 23, 'Prototype', FALSE),
-- Q24 (Correta: 95)
(93, 24, 'git pull', FALSE),
(94, 24, 'git clone', FALSE),
(95, 24, 'git push', TRUE),
(96, 24, 'git status', FALSE);


INSERT INTO avaliacao (id_avaliacao, titulo, descricao, data_aplicacao, hora_inicio, hora_fim, id_professor) VALUES
    (1, 'Prova 1 de Banco de Dados',          'Modelagem relacional, DDL, restrições e conceitos fundamentais.', '2026-09-18', '19:15:00', '21:00:00', 1),
    (2, 'Simulado SQL e Normalização',        'Consultas SQL, junções, agregações e formas normais (1FN a 3FN).', '2026-10-05', '19:15:00', '20:45:00', 1),
    (3, 'Quiz de Modelagem Conceitual',       'Entidades, relacionamentos, cardinalidades e agregações no MER.',  '2026-10-23', '21:00:00', '22:00:00', 2),
    (4, 'Prova 2 - Transações e Índices',     'Controle de concorrência, propriedades ACID e indexação.',        '2026-11-06', '19:15:00', '21:00:00', 1),
    (5, 'Avaliação Integrada de Sistemas',    'Arquitetura em camadas, DAO, JDBC, REST e React.',                '2026-11-16', '19:15:00', '21:15:00', 3);

-- 5. COMPOSIÇÃO DAS AVALIAÇÕES (Reuso de Q1 com valores diferentes nas Avaliações 1 e 2; Q28..Q30 ficam sem uso)
INSERT INTO avaliacao_questao (id_avaliacao, id_questao, ordem, valor) VALUES
-- Avaliação 1 (Total = 10.00)
(1, 1,  1, 1.50),
(1, 2,  2, 1.50),
(1, 3,  3, 2.00),
(1, 4,  4, 2.00),
(1, 25, 5, 3.00),
-- Avaliação 2 (Total = 10.00 - reusa Q1 valendo 1.00)
(2, 1,  1, 1.00),
(2, 5,  2, 2.00),
(2, 6,  3, 2.00),
(2, 7,  4, 2.50),
(2, 8,  5, 2.50),
-- Avaliação 3 (Total = 10.00)
(3, 9,  1, 2.00),
(3, 10, 2, 2.00),
(3, 11, 3, 2.00),
(3, 12, 4, 2.00),
(3, 13, 5, 2.00),
-- Avaliação 4 (Total = 10.00)
(4, 14, 1, 1.50),
(4, 15, 2, 1.50),
(4, 16, 3, 2.00),
(4, 17, 4, 2.00),
(4, 26, 5, 3.00),
-- Avaliação 5 (Total = 10.00)
(5, 18, 1, 1.00),
(5, 19, 2, 1.00),
(5, 20, 3, 1.50),
(5, 21, 4, 1.50),
(5, 22, 5, 1.00),
(5, 23, 6, 1.00),
(5, 24, 7, 1.00),
(5, 27, 8, 2.00);


INSERT INTO avaliacao_usuario (id_avaliacao, id_aluno, status, data_submissao) VALUES
-- Avaliação 1 (Turma CC-2026-A: alunos 4 a 13)
(1, 4,  'CORRIGIDA',  '2026-09-18 20:10:00'),
(1, 5,  'CORRIGIDA',  '2026-09-18 20:25:00'),
(1, 6,  'CORRIGIDA',  '2026-09-18 20:30:00'),
(1, 7,  'CORRIGIDA',  '2026-09-18 20:42:00'),
(1, 8,  'CORRIGIDA',  '2026-09-18 20:15:00'),
(1, 9,  'CORRIGIDA',  '2026-09-18 20:50:00'),
(1, 10, 'CORRIGIDA',  '2026-09-18 20:55:00'),
(1, 11, 'CORRIGIDA',  '2026-09-18 20:38:00'),
(1, 12, 'RESPONDIDA', '2026-09-18 20:58:00'),
(1, 13, 'PENDENTE',   NULL),

-- Avaliação 2 (Turmas A e B: alunos 4..9 e 14..18)
(2, 4,  'CORRIGIDA',  '2026-10-05 19:55:00'),
(2, 5,  'CORRIGIDA',  '2026-10-05 20:10:00'),
(2, 6,  'CORRIGIDA',  '2026-10-05 20:12:00'),
(2, 7,  'CORRIGIDA',  '2026-10-05 20:30:00'),
(2, 8,  'PENDENTE',   NULL),
(2, 9,  'PENDENTE',   NULL),
(2, 14, 'CORRIGIDA',  '2026-10-05 19:50:00'),
(2, 15, 'CORRIGIDA',  '2026-10-05 20:05:00'),
(2, 16, 'CORRIGIDA',  '2026-10-05 20:20:00'),
(2, 17, 'CORRIGIDA',  '2026-10-05 20:35:00'),
(2, 18, 'PENDENTE',   NULL),

-- Avaliação 3 (Turma CC-2026-B: alunos 14 a 23)
(3, 14, 'CORRIGIDA',  '2026-10-23 21:20:00'),
(3, 15, 'CORRIGIDA',  '2026-10-23 21:25:00'),
(3, 16, 'CORRIGIDA',  '2026-10-23 21:30:00'),
(3, 17, 'CORRIGIDA',  '2026-10-23 21:35:00'),
(3, 18, 'CORRIGIDA',  '2026-10-23 21:40:00'),
(3, 19, 'CORRIGIDA',  '2026-10-23 21:45:00'),
(3, 20, 'CORRIGIDA',  '2026-10-23 21:50:00'),
(3, 21, 'CORRIGIDA',  '2026-10-23 21:55:00'),
(3, 22, 'PENDENTE',   NULL),
(3, 23, 'PENDENTE',   NULL),

-- Avaliação 4 (Turma CC-2026-A: alunos 4 a 10)
(4, 4,  'CORRIGIDA',  '2026-11-06 20:15:00'),
(4, 5,  'CORRIGIDA',  '2026-11-06 20:25:00'),
(4, 6,  'CORRIGIDA',  '2026-11-06 20:30:00'),
(4, 7,  'CORRIGIDA',  '2026-11-06 20:40:00'),
(4, 8,  'CORRIGIDA',  '2026-11-06 20:45:00'),
(4, 9,  'RESPONDIDA', '2026-11-06 20:52:00'),
(4, 10, 'PENDENTE',   NULL),

-- Avaliação 5 (Turma CC-2026-B: alunos 14 a 20)
(5, 14, 'CORRIGIDA',  '2026-11-16 20:30:00'),
(5, 15, 'CORRIGIDA',  '2026-11-16 20:40:00'),
(5, 16, 'CORRIGIDA',  '2026-11-16 20:45:00'),
(5, 17, 'CORRIGIDA',  '2026-11-16 20:55:00'),
(5, 18, 'CORRIGIDA',  '2026-11-16 21:00:00'),
(5, 19, 'PENDENTE',   NULL),
(5, 20, 'PENDENTE',   NULL);


INSERT INTO resposta (id_avaliacao, id_aluno, id_questao, id_alternativa_escolhida, texto_resposta, nota_obtida, corrigida) VALUES
-- ==================== AVALIAÇÃO 1 ====================
-- Aluno 4 (Total: 9.50)
(1, 4, 1,  2,    NULL, 1.50, TRUE),
(1, 4, 2,  5,    NULL, 1.50, TRUE),
(1, 4, 3,  11,   NULL, 2.00, TRUE),
(1, 4, 4,  13,   NULL, 2.00, TRUE),
(1, 4, 25, NULL, 'PRIMARY KEY identifica unicamente cada tupla da tabela; FOREIGN KEY referencia a chave primária de outra tabela para manter integridade.', 2.50, TRUE),
-- Aluno 5 (Total: 6.00)
(1, 5, 1,  2,    NULL, 1.50, TRUE),
(1, 5, 2,  6,    NULL, 0.00, TRUE),
(1, 5, 3,  9,    NULL, 0.00, TRUE),
(1, 5, 4,  13,   NULL, 2.00, TRUE),
(1, 5, 25, NULL, 'PK é o identificador único da tabela e FK faz a ligação entre duas tabelas.', 2.50, TRUE),
-- Aluno 6 (Total: 8.00)
(1, 6, 1,  2,    NULL, 1.50, TRUE),
(1, 6, 2,  5,    NULL, 1.50, TRUE),
(1, 6, 3,  10,   NULL, 0.00, TRUE),
(1, 6, 4,  13,   NULL, 2.00, TRUE),
(1, 6, 25, NULL, 'A chave primária garante unicidade e não nulidade; a estrangeira estabelece vínculo referencial entre relações.', 3.00, TRUE),
-- Aluno 7 (Total: 4.50)
(1, 7, 1,  2,    NULL, 1.50, TRUE),
(1, 7, 2,  7,    NULL, 0.00, TRUE),
(1, 7, 3,  10,   NULL, 0.00, TRUE),
(1, 7, 4,  14,   NULL, 0.00, TRUE),
(1, 7, 25, NULL, 'Chave primária é o ID principal e chave estrangeira vem de fora.', 3.00, TRUE),
-- Aluno 8 (Total: 10.00)
(1, 8, 1,  2,    NULL, 1.50, TRUE),
(1, 8, 2,  5,    NULL, 1.50, TRUE),
(1, 8, 3,  11,   NULL, 2.00, TRUE),
(1, 8, 4,  13,   NULL, 2.00, TRUE),
(1, 8, 25, NULL, 'PRIMARY KEY garante identificação única de registros na tabela, enquanto FOREIGN KEY assegura integridade referencial entre tabelas relacionadas.', 3.00, TRUE),
-- Aluno 9 (Total: 7.00)
(1, 9, 1,  2,    NULL, 1.50, TRUE),
(1, 9, 2,  5,    NULL, 1.50, TRUE),
(1, 9, 3,  9,    NULL, 0.00, TRUE),
(1, 9, 4,  13,   NULL, 2.00, TRUE),
(1, 9, 25, NULL, 'PK identifica a linha e FK liga tabelas.', 2.00, TRUE),
-- Aluno 10 (Total: 5.50)
(1, 10, 1, 1,    NULL, 0.00, TRUE),
(1, 10, 2, 5,    NULL, 1.50, TRUE),
(1, 10, 3, 12,   NULL, 0.00, TRUE),
(1, 10, 4, 13,   NULL, 2.00, TRUE),
(1, 10, 25, NULL, 'PK não pode repetir e FK aponta para outra PK.', 2.00, TRUE),
-- Aluno 11 (Total: 8.50)
(1, 11, 1, 2,    NULL, 1.50, TRUE),
(1, 11, 2, 5,    NULL, 1.50, TRUE),
(1, 11, 3, 11,   NULL, 2.00, TRUE),
(1, 11, 4, 14,   NULL, 0.00, TRUE),
(1, 11, 25, NULL, 'A PK identifica unicamente cada tupla; a FK referencia uma PK para garantir consistência entre entidades.', 3.50 - 0.50, TRUE),
-- Aluno 12 (RESPONDIDA - Q25 aguardando correção do professor)
(1, 12, 1, 2,    NULL, 1.50, TRUE),
(1, 12, 2, 5,    NULL, 1.50, TRUE),
(1, 12, 3, 10,   NULL, 0.00, TRUE),
(1, 12, 4, 13,   NULL, 2.00, TRUE),
(1, 12, 25, NULL, 'PRIMARY KEY define a identidade da tabela e FOREIGN KEY conecta tabelas relacionadas.', NULL, FALSE),

-- ==================== AVALIAÇÃO 2 ====================
-- Aluno 4 (Total: 10.00)
(2, 4, 1, 2,  NULL, 1.00, TRUE),
(2, 4, 5, 20, NULL, 2.00, TRUE),
(2, 4, 6, 22, NULL, 2.00, TRUE),
(2, 4, 7, 25, NULL, 2.50, TRUE),
(2, 4, 8, 31, NULL, 2.50, TRUE),
-- Aluno 5 (Total: 7.50)
(2, 5, 1, 2,  NULL, 1.00, TRUE),
(2, 5, 5, 20, NULL, 2.00, TRUE),
(2, 5, 6, 22, NULL, 2.00, TRUE),
(2, 5, 7, 26, NULL, 0.00, TRUE),
(2, 5, 8, 31, NULL, 2.50, TRUE),
-- Aluno 6 (Total: 5.50)
(2, 6, 1, 2,  NULL, 1.00, TRUE),
(2, 6, 5, 17, NULL, 0.00, TRUE),
(2, 6, 6, 22, NULL, 2.00, TRUE),
(2, 6, 7, 26, NULL, 0.00, TRUE),
(2, 6, 8, 31, NULL, 2.50, TRUE),
-- Aluno 7 (Total: 8.00)
(2, 7, 1, 2,  NULL, 1.00, TRUE),
(2, 7, 5, 18, NULL, 0.00, TRUE),
(2, 7, 6, 22, NULL, 2.00, TRUE),
(2, 7, 7, 25, NULL, 2.50, TRUE),
(2, 7, 8, 31, NULL, 2.50, TRUE),
-- Aluno 14 (Total: 7.50)
(2, 14, 1, 2,  NULL, 1.00, TRUE),
(2, 14, 5, 20, NULL, 2.00, TRUE),
(2, 14, 6, 22, NULL, 2.00, TRUE),
(2, 14, 7, 27, NULL, 0.00, TRUE),
(2, 14, 8, 31, NULL, 2.50, TRUE),
-- Aluno 15 (Total: 10.00)
(2, 15, 1, 2,  NULL, 1.00, TRUE),
(2, 15, 5, 20, NULL, 2.00, TRUE),
(2, 15, 6, 22, NULL, 2.00, TRUE),
(2, 15, 7, 25, NULL, 2.50, TRUE),
(2, 15, 8, 31, NULL, 2.50, TRUE),
-- Aluno 16 (Total: 5.00)
(2, 16, 1, 3,  NULL, 0.00, TRUE),
(2, 16, 5, 17, NULL, 0.00, TRUE),
(2, 16, 6, 21, NULL, 0.00, TRUE),
(2, 16, 7, 25, NULL, 2.50, TRUE),
(2, 16, 8, 31, NULL, 2.50, TRUE),
-- Aluno 17 (Total: 8.00)
(2, 17, 1, 2,  NULL, 1.00, TRUE),
(2, 17, 5, 18, NULL, 0.00, TRUE),
(2, 17, 6, 22, NULL, 2.00, TRUE),
(2, 17, 7, 25, NULL, 2.50, TRUE),
(2, 17, 8, 31, NULL, 2.50, TRUE),

-- ==================== AVALIAÇÃO 3 ====================
-- Aluno 14 (Total: 8.00)
(3, 14, 9,  33, NULL, 2.00, TRUE),
(3, 14, 10, 38, NULL, 2.00, TRUE),
(3, 14, 11, 41, NULL, 2.00, TRUE),
(3, 14, 12, 45, NULL, 0.00, TRUE),
(3, 14, 13, 49, NULL, 2.00, TRUE),
-- Aluno 15 (Total: 6.00)
(3, 15, 9,  33, NULL, 2.00, TRUE),
(3, 15, 10, 37, NULL, 0.00, TRUE),
(3, 15, 11, 41, NULL, 2.00, TRUE),
(3, 15, 12, 46, NULL, 0.00, TRUE),
(3, 15, 13, 49, NULL, 2.00, TRUE),
-- Aluno 16 (Total: 10.00)
(3, 16, 9,  33, NULL, 2.00, TRUE),
(3, 16, 10, 38, NULL, 2.00, TRUE),
(3, 16, 11, 41, NULL, 2.00, TRUE),
(3, 16, 12, 47, NULL, 2.00, TRUE),
(3, 16, 13, 49, NULL, 2.00, TRUE),
-- Aluno 17 (Total: 4.00)
(3, 17, 9,  33, NULL, 2.00, TRUE),
(3, 17, 10, 39, NULL, 0.00, TRUE),
(3, 17, 11, 42, NULL, 0.00, TRUE),
(3, 17, 12, 45, NULL, 0.00, TRUE),
(3, 17, 13, 49, NULL, 2.00, TRUE),
-- Aluno 18 (Total: 8.00)
(3, 18, 9,  33, NULL, 2.00, TRUE),
(3, 18, 10, 38, NULL, 2.00, TRUE),
(3, 18, 11, 41, NULL, 2.00, TRUE),
(3, 18, 12, 47, NULL, 2.00, TRUE),
(3, 18, 13, 50, NULL, 0.00, TRUE),
-- Aluno 19 (Total: 6.00)
(3, 19, 9,  34, NULL, 0.00, TRUE),
(3, 19, 10, 38, NULL, 2.00, TRUE),
(3, 19, 11, 41, NULL, 2.00, TRUE),
(3, 19, 12, 46, NULL, 0.00, TRUE),
(3, 19, 13, 49, NULL, 2.00, TRUE),
-- Aluno 20 (Total: 10.00)
(3, 20, 9,  33, NULL, 2.00, TRUE),
(3, 20, 10, 38, NULL, 2.00, TRUE),
(3, 20, 11, 41, NULL, 2.00, TRUE),
(3, 20, 12, 47, NULL, 2.00, TRUE),
(3, 20, 13, 49, NULL, 2.00, TRUE),
-- Aluno 21 (Total: 8.00)
(3, 21, 9,  33, NULL, 2.00, TRUE),
(3, 21, 10, 37, NULL, 0.00, TRUE),
(3, 21, 11, 41, NULL, 2.00, TRUE),
(3, 21, 12, 47, NULL, 2.00, TRUE),
(3, 21, 13, 49, NULL, 2.00, TRUE),

-- ==================== AVALIAÇÃO 4 ====================
-- Aluno 4 (Total: 8.50)
(4, 4, 14, 54,   NULL, 1.50, TRUE),
(4, 4, 15, 58,   NULL, 0.00, TRUE),
(4, 4, 16, 63,   NULL, 2.00, TRUE),
(4, 4, 17, 65,   NULL, 2.00, TRUE),
(4, 4, 26, NULL, 'Em uma transferência bancária entre duas contas, o débito e o crédito devem ocorrer juntos via COMMIT ou serem desfeitos via ROLLBACK.', 3.00, TRUE),
-- Aluno 5 (Total: 9.00)
(4, 5, 14, 54,   NULL, 1.50, TRUE),
(4, 5, 15, 57,   NULL, 1.50, TRUE),
(4, 5, 16, 63,   NULL, 2.00, TRUE),
(4, 5, 17, 65,   NULL, 2.00, TRUE),
(4, 5, 26, NULL, 'Ao salvar uma prova com várias respostas, se der erro na última questão, o ROLLBACK evita gravação parcial no banco.', 2.00, TRUE),
-- Aluno 6 (Total: 6.50)
(4, 6, 14, 53,   NULL, 0.00, TRUE),
(4, 6, 15, 58,   NULL, 0.00, TRUE),
(4, 6, 16, 63,   NULL, 2.00, TRUE),
(4, 6, 17, 65,   NULL, 2.00, TRUE),
(4, 6, 26, NULL, 'Em sistemas financeiros para não perder dinheiro se cair a conexão.', 2.50, TRUE),
-- Aluno 7 (Total: 7.00)
(4, 7, 14, 54,   NULL, 1.50, TRUE),
(4, 7, 15, 57,   NULL, 1.50, TRUE),
(4, 7, 16, 61,   NULL, 0.00, TRUE),
(4, 7, 17, 65,   NULL, 2.00, TRUE),
(4, 7, 26, NULL, 'Quando inserimos dados em tabelas pai e filha simultaneamente.', 2.00, TRUE),
-- Aluno 8 (Total: 9.50)
(4, 8, 14, 54,   NULL, 1.50, TRUE),
(4, 8, 15, 57,   NULL, 1.50, TRUE),
(4, 8, 16, 63,   NULL, 2.00, TRUE),
(4, 8, 17, 65,   NULL, 2.00, TRUE),
(4, 8, 26, NULL, 'Qualquer operação composta em múltiplas tabelas (como fechar pedido e baixar estoque) exige atomicidade via COMMIT/ROLLBACK.', 2.50, TRUE),
-- Aluno 9 (RESPONDIDA - Q26 aguardando correção)
(4, 9, 14, 54,   NULL, 1.50, TRUE),
(4, 9, 15, 58,   NULL, 0.00, TRUE),
(4, 9, 16, 63,   NULL, 2.00, TRUE),
(4, 9, 17, 65,   NULL, 2.00, TRUE),
(4, 9, 26, NULL, 'Na matrícula de alunos em disciplinas com vagas limitadas para evitar inconsistência.', NULL, FALSE),

-- ==================== AVALIAÇÃO 5 ====================
-- Aluno 14 (Total: 10.00)
(5, 14, 18, 70,   NULL, 1.00, TRUE),
(5, 14, 19, 73,   NULL, 1.00, TRUE),
(5, 14, 20, 79,   NULL, 1.50, TRUE),
(5, 14, 21, 81,   NULL, 1.50, TRUE),
(5, 14, 22, 86,   NULL, 1.00, TRUE),
(5, 14, 23, 89,   NULL, 1.00, TRUE),
(5, 14, 24, 95,   NULL, 1.00, TRUE),
(5, 14, 27, NULL, 'Separar SQL nas classes DAO facilita manutenção, reutilização de consultas, controle de transações e evita acoplamento com a interface.', 2.00, TRUE),
-- Aluno 15 (Total: 8.50)
(5, 15, 18, 70,   NULL, 1.00, TRUE),
(5, 15, 19, 73,   NULL, 1.00, TRUE),
(5, 15, 20, 79,   NULL, 1.50, TRUE),
(5, 15, 21, 82,   NULL, 0.00, TRUE),
(5, 15, 22, 86,   NULL, 1.00, TRUE),
(5, 15, 23, 89,   NULL, 1.00, TRUE),
(5, 15, 24, 95,   NULL, 1.00, TRUE),
(5, 15, 27, NULL, 'Para manter o código organizado em camadas e facilitar testes e alterações no banco.', 2.00, TRUE),
-- Aluno 16 (Total: 7.00)
(5, 16, 18, 70,   NULL, 1.00, TRUE),
(5, 16, 19, 74,   NULL, 0.00, TRUE),
(5, 16, 20, 79,   NULL, 1.50, TRUE),
(5, 16, 21, 81,   NULL, 1.50, TRUE),
(5, 16, 22, 85,   NULL, 0.00, TRUE),
(5, 16, 23, 89,   NULL, 1.00, TRUE),
(5, 16, 24, 95,   NULL, 1.00, TRUE),
(5, 16, 27, NULL, 'Deixar SQL na tela mistura regra de apresentação com acesso a dados e prejudica a segurança.', 1.00, TRUE),
-- Aluno 17 (Total: 6.50)
(5, 17, 18, 70,   NULL, 1.00, TRUE),
(5, 17, 19, 73,   NULL, 1.00, TRUE),
(5, 17, 20, 77,   NULL, 0.00, TRUE),
(5, 17, 21, 81,   NULL, 1.50, TRUE),
(5, 17, 22, 86,   NULL, 1.00, TRUE),
(5, 17, 23, 90,   NULL, 0.00, TRUE),
(5, 17, 24, 95,   NULL, 1.00, TRUE),
(5, 17, 27, NULL, 'Organiza o projeto e evita repetir código SQL em vários lugares.', 1.00, TRUE),
-- Aluno 18 (Total: 9.00)
(5, 18, 18, 70,   NULL, 1.00, TRUE),
(5, 18, 19, 73,   NULL, 1.00, TRUE),
(5, 18, 20, 79,   NULL, 1.50, TRUE),
(5, 18, 21, 81,   NULL, 1.50, TRUE),
(5, 18, 22, 86,   NULL, 1.00, TRUE),
(5, 18, 23, 89,   NULL, 1.00, TRUE),
(5, 18, 24, 95,   NULL, 1.00, TRUE),
(5, 18, 27, NULL, 'Garante baixo acoplamento e alta coesão entre interface, regras de negócio e persistência.', 1.00, TRUE);

-- 8. SINCRONIZAÇÃO DAS SEQUÊNCIAS SERIAL APÓS CARGA COM IDs EXPLÍCITOS
SELECT setval(pg_get_serial_sequence('usuario', 'id_usuario'), COALESCE(MAX(id_usuario), 1)) FROM usuario;
SELECT setval(pg_get_serial_sequence('questao', 'id_questao'), COALESCE(MAX(id_questao), 1)) FROM questao;
SELECT setval(pg_get_serial_sequence('alternativa', 'id_alternativa'), COALESCE(MAX(id_alternativa), 1)) FROM alternativa;
SELECT setval(pg_get_serial_sequence('avaliacao', 'id_avaliacao'), COALESCE(MAX(id_avaliacao), 1)) FROM avaliacao;
SELECT setval(pg_get_serial_sequence('resposta', 'id_resposta'), COALESCE(MAX(id_resposta), 1)) FROM resposta;
