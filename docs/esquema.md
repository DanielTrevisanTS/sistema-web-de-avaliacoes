# Esquema Relacional e Normalização (3FN)

## 1. DER → Relacional

* **USUARIO** (<u>id_usuario</u>, nome, email, senha, perfil, turma)


* **QUESTAO** (<u>id_questao</u>, enunciado, tipo, disciplina, *id_professor*(id_usuario→USUARIO))


* **ALTERNATIVA** (<u>id_alternativa</u>, *id_questao*(id_questao→QUESTAO), texto, correta)


* **AVALIACAO** (<u>id_avaliacao</u>, titulo, descricao, data_aplicacao, hora_inicio, hora_fim, *id_professor*(id_usuario→USUARIO))


* **AVALIACAO_QUESTAO** (<u>*id_avaliacao*</u>(id_avaliacao→AVALIACAO), <u>*id_questao*</u>(id_questao→QUESTAO), ordem, valor)


* **AVALIACAO_USUARIO** (<u>*id_avaliacao*</u>(id_avaliacao→AVALIACAO), <u>*id_aluno*</u>(id_usuario→USUARIO), status, data_submissao)


* **RESPOSTA** (<u>id_resposta</u>, *id_avaliacao*, *id_aluno*, *id_questao*, *id_alternativa_escolhida*(id_alternativa→ALTERNATIVA), texto_resposta, nota_obtida, corrigida)
    * (*id_avaliacao*, *id_aluno*) referencia **AVALIACAO_USUARIO**(*id_avaliacao*, *id_aluno*)
    * (*id_avaliacao*, *id_questao*) referencia **AVALIACAO_QUESTAO**(*id_avaliacao*, *id_questao*)
    * *id_alternativa_escolhida* referencia **ALTERNATIVA**(*id_alternativa*)

## 2. Conferindo a 3FN

* **Primeira Forma Normal (1FN):** Todas as relações possuem chaves primárias definidas e todos os atributos são atômicos e monovalorados (as alternativas de uma questão foram isoladas na tabela `ALTERNATIVA` em vez de armazenadas em lista ou colunas repetidas).
* **Segunda Forma Normal (2FN):** Nas tabelas com chave primária composta (`AVALIACAO_QUESTAO` e `AVALIACAO_USUARIO`), todos os atributos não-chave dependem funcionalmente da chave inteira (`valor` e `ordem` dependem do par `id_avaliacao + id_questao`; `status` e `data_submissao` dependem do par `id_avaliacao + id_aluno`).
* **Terceira Forma Normal (3FN):** Não existem dependências transitivas entre atributos não-chave em nenhuma tabela, e atributos derivados (como a nota total de um aluno em uma avaliação ou a média da turma) não são armazenados de forma redundante, sendo calculados por consultas no SQL (`SUM` / `AVG`).
