CREATE TABLE seguranca.token (
	id serial4 NOT NULL,
	expired bool NOT NULL,
	revoked bool NOT NULL,
	id_usuario int4 NOT NULL,
	"token" varchar(255) NULL,
	token_type varchar(255) NULL
);

ALTER TABLE seguranca.token
  ADD PRIMARY KEY (id);
  
ALTER TABLE seguranca.token
  ADD CONSTRAINT token_token_key UNIQUE (token);
  
ALTER TABLE seguranca.token
  ADD CONSTRAINT token_token_type_check CHECK (((token_type)::text = 'BEARER'::text));
  
ALTER TABLE seguranca.token
  ADD CONSTRAINT fk_token_usuario FOREIGN KEY (id_usuario) REFERENCES seguranca.usuario(id);
