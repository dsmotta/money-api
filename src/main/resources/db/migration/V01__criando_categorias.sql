CREATE TABLE categoria (
    codigo serial NOT NULL,
    nome character varying(50) NOT NULL,
    CONSTRAINT pk_categoria_codigo PRIMARY KEY (codigo)
);

insert into categoria (nome) values ('Lazer');
