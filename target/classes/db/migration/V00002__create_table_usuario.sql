CREATE TABLE seguranca.usuario(
	id SERIAL NOT NULL,
	email varchar(255) NULL,
	nome varchar(255) NULL,
	senha varchar(255) NULL
);

ALTER TABLE seguranca.usuario
   ADD PRIMARY KEY (id);   