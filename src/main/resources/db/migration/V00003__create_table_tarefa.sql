CREATE TYPE status AS ENUM ('PENDENTE', 'CONCLUIDO');

CREATE TABLE public.tarefa(
	id SERIAL NOT NULL,
	titulo VARCHAR(120) NOT NULL,
	descricao VARCHAR(255) NOT NULL,
	status STATUS NOT NULL,
	data_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_DATE,
	id_usuario_criacao INTEGER NOT NULL,
	data_atualizacao TIMESTAMP,
	id_usuario_atualizacao INTEGER
);

ALTER TABLE public.tarefa
  ADD CONSTRAINT fk_tarefa_usuario_criacao
    FOREIGN KEY (id_usuario_criacao) REFERENCES seguranca.usuario(id);

ALTER TABLE public.tarefa
  ADD CONSTRAINT fk_tarefa_usuario_atualizacao
    FOREIGN KEY (id_usuario_atualizacao) REFERENCES seguranca.usuario(id);

ALTER TABLE public.tarefa
  ADD PRIMARY KEY (id);
