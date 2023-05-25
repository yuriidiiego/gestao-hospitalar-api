-- Pacientes
INSERT INTO pacientes(cpf, data_nascimento, nome, sexo)
    VALUES ('72670208100', '1995-02-10', 'Joana Oliveira', 'FEMININO');

INSERT INTO pacientes(cpf, data_nascimento, nome, sexo)
    VALUES ('67167247618', '1998-10-02', 'Carlos Santos', 'MASCULINO');

INSERT INTO pacientes(cpf, data_nascimento, nome, sexo)
    VALUES ('05313247883', '1990-01-03', 'Ana Silva', 'FEMININO');

INSERT INTO pacientes(cpf, data_nascimento, nome, sexo)
    VALUES ('62117840825', '2015-05-12', 'Rafaela Costa', 'FEMININO');

INSERT INTO pacientes(cpf, data_nascimento, nome, sexo)
    VALUES ('52239493828', '2010-12-05', 'Lucas Oliveira', 'MASCULINO');

-- Medicos
INSERT INTO medicos(cpf, data_nascimento, nome, sexo, crm)
    VALUES ('85067858559', '1889-07-07', 'Fernanda Rocha', 'FEMININO', '123456/PA');

INSERT INTO medicos(cpf, data_nascimento, nome, sexo, crm)
    VALUES ('15743715688', '1888-08-10', 'Ricardo Mendes', 'MASCULINO', '246810/SP');

INSERT INTO medicos(cpf, data_nascimento, nome, sexo, crm)
    VALUES ('38138078510', '1996-02-05', 'Juliana Castro', 'FEMININO', '135792/AM');

-- Roles
INSERT INTO perfis(nome)
    VALUES ('ROLE_USER');

INSERT INTO perfis(nome)
    VALUES ('ROLE_ADMIN');

INSERT INTO perfis(nome)
    VALUES ('ROLE_MODERATOR');

