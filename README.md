# API de Gestão Hospitalar 🏥

Bem-vindo à documentação da API de Gestão Hospitalar. Esta API oferece endpoints para gerenciar médicos, pacientes e consultas médicas em um sistema de gestão hospitalar.

👉 **Importante**: Certifique-se de ter as seguintes ferramentas instaladas antes de executar o projeto:

- [Docker](https://www.docker.com/) 🐳
- [Docker Compose](https://docs.docker.com/compose/install/) 🐙
- [Maven](https://maven.apache.org/) 📦

## Tecnologias utilizadas 🛠️

A API de Gestão Hospitalar foi desenvolvida utilizando as seguintes tecnologias:

- Java 11 🚀
- Spring Boot 2.7.11 🌟
- Spring Security 🔒
- Springdoc OpenAPI 📖
- MapStruct 🗺️
- JJWT 🔑
- PostgreSQL 🐘
- iText PDF 📝
- Maven 📦
- Docker 🐳

## Endpoints 📚

**Médicos** 👨‍⚕️

| Método   | Endpoint                      | Descrição                                         |
| -------- | ----------------------------- | ------------------------------------------------- |
| ✅ GET   | `/medicos`                      | Retorna uma lista de todos os médicos             |
| ✅ GET   | `/medicos/{id}`                 | Retorna o médico com o ID especificado            |
| ✅ GET   | `/medicos/periodo`              | Retorna médicos que trabalharam em um período de datas |
| ✅ GET   | `/medicos/pacientes/{idPaciente}` | Retorna os médicos que atenderam um determinado paciente |
| ✅ POST  | `/medicos`                      | Cria um novo médico                               |
| ✅ PUT   | `/medicos/{id}`                 | Atualiza o médico com o ID especificado            |
| ✅ PATCH | `/medicos/{id}`                 | Atualiza parcialmente o médico com o ID especificado |
| ❌ DELETE | `/medicos/{id}`                 | Exclui o médico com o ID especificado              |

**Pacientes** 🏥

| Método   | Endpoint            | Descrição                                       |
| -------- | ------------------- | ----------------------------------------------- |
| ✅ GET   | `/pacientes`          | Retorna uma lista de todos os pacientes         |
| ✅ GET   | `/pacientes/{id}`     | Retorna o paciente com o ID especificado        |
| ✅ GET   | `/pacientes/medicos/{idMedico}` | Lista os pacientes de um determinado médico |
| ✅ GET   | `/pacientes/pdf`       | Gera um pdf com todos os pacientes              |
| ✅ POST  | `/pacientes`          | Cria um novo paciente                            |
| ✅ PUT   | `/pacientes/{id}`     | Atualiza o paciente com o ID especificado       |
| ❌ DELETE | `/pacientes/{id}`     | Exclui o paciente com o ID especificado         |

**Atendimentos** ⚕️

| Método   | Endpoint                      | Descrição                                       |
| -------- | ----------------------------- | ----------------------------------------------- |
| ✅ POST  | `/atendimentos`               | Cadastra um novo atendimento                    |
| ✅ PUT   | `/atendimentos/{id}`          | Atualiza um atendimento                         |
| ✅ GET   | `/atendimentos/periodo`       | Busca atendimentos por período                  |

 **Usuários** 🧑‍💻

| Método   | Endpoint              | Descrição                               |
| -------- | --------------------- | --------------------------------------- |
| POST     | `/usuarios/login`     | Loga um usuário e retorna um token JWT  |
| POST     | `/usuarios/cadastro`  | Cadastra um novo usuário no sistema     |
| POST     | `/usuarios/logout`    | Desloga um usuário                      |

## Tabela de Dados 📋

As tabelas abaixo foram criadas para gerenciar os médicos, pacientes e consultas médicas.

**Tabela** `Pacientes`🏥:
| Coluna               | Tipo          | Nulo   | Descrição                        |
| -------------------- | ------------- | ------ | -------------------------------- |
| Identificador        |               |   ❌   | Identificador do paciente        |
| Nome                 |               |   ❌   | Nome do paciente                 |
| CPF                  |               |   ❌   | CPF do paciente                  |
| Data de Nascimento   |               |   ✅   | Data de nascimento do paciente   |
| Sexo                 |               |   ❌   | Sexo do paciente                 |

**Tabela** `Médicos`👨‍⚕️:
| Coluna               | Tipo             | Nulo   | Descrição                              |
| -------------------- | ---------------- | ------ | -------------------------------------- |
| id                   | bigint           | ❌     | Identificador do médico                |
| cpf                  | varchar(11)      | ❌     | CPF do médico                          |
| data_nascimento      | date             | ✅     | Data de nascimento do médico           |
| nome                 | varchar(50)      | ❌     | Nome do médico                         |
| sexo                 | varchar(10)      | ❌     | Sexo do médico                         |
| crm                  | varchar(8)       | ❌     | CRM do médico                          |

**Tabela** `Atendimentos`⚕️:
| Coluna               | Tipo             | Nulo   | Descrição                              |
| -------------------- | ---------------- | ------ | -------------------------------------- |
| id                   | bigint           | ❌     | Identificador do atendimento           |
| data_atendimento     | date             | ✅     | Data do atendimento                    |
| observacao           | varchar(255)     | ❌     | Observação do atendimento              |
| status_atendimento   | varchar(15)      | ❌     | Status do atendimento                  |
| medico_id            | bigint           | ✅     | ID do médico responsável               |
| paciente_id          | bigint           | ✅     | ID do paciente atendido                |

## Executando o Projeto ▶️

Para executar o projeto, siga as etapas abaixo:

1. Clone o repositório em seu ambiente local.
2. Abra o projeto em sua IDE preferida.
3. Execute o comando `mvn clean package -DskipTests` para gerar o JAR do projeto.
4. Rode o comando `docker-compose up -d` para iniciar os containers do projeto.

## Documentação 📚

1. Você pode acessar a documentação da API [aqui](http://localhost:8080/gestao-hospitalar/swagger-ui.html) para visualizar e interagir com os endpoints disponíveis.
![Swagger](https://img.shields.io/badge/-Swagger-000?&logo=Swagger)

2. Você também pode importar o arquivo `gestao-hospitalar.postman_collection.json` que está no diretório principal do projeto e exportar no Postman para ter acesso aos endpoints já configurados.
![Postman](https://img.shields.io/badge/-Postman-000?&logo=Postman)

Agora você está pronto para utilizar a API de Gestão Hospitalar e gerenciar médicos, pacientes e consultas médicas com facilidade. 🚀

## 🚧 Em Construção 🚧
