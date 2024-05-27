CREATE TABLE seguranca.usuario(
	id SERIAL NOT NULL,
	email varchar(255) UNIQUE NOT NULL,
	nome varchar(255) NOT NULL,
	senha varchar(255) NOT NULL
);

ALTER TABLE seguranca.usuario
   ADD PRIMARY KEY (id);   