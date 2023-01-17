CREATE TABLE pessoa (
    id serial NOT NULL,
    nome varchar(100) not null,
    ativo boolean NOT NULL,
    logradouro varchar(150),
    numero varchar(10),
    complemento varchar(50),
    bairro varchar(80),
    cep varchar(10),
    cidade varchar(100),
    estado varchar(2),    
    CONSTRAINT pk_pessoa_id PRIMARY KEY (id)
);

INSERT INTO public.pessoa
(nome, ativo, logradouro, numero, complemento, bairro, cep, cidade, estado)
VALUES('Douglas', true, 'Conde Francisco', '891', 'Anexo A', 'Centro', '14580-000', 'Guará', 'SP');
