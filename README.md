# Projeto Loja Virtual

Este é um projeto de API de um sistema para uma loja virtual. A API deve gerenciar o catálogo de produtos e os pedidos realizados pelos clientes. O objetivo é criar uma solução robusta, capaz de lidar com um grande volume de dados, garantindo performance, segurança e boas práticas de desenvolvimento, desenvolvido com Spring Boot e MySQL.

## Instalação e Configuração

### Pré-requisitos

- Java 17 ou superior
- Maven 3.x
- MySQL 8.x
- IDE de sua preferência (recomendado IntelliJ IDEA ou Eclipse)

### Passos para Instalação

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/loja-virtual.git
   cd loja-virtual

### Configuração do Banco de Dados:

1. Crie um banco de dados manualmente com o nome lojavirtual.

2. Configure as credenciais do banco de dados no arquivo application.properties:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/lojavirtual
spring.datasource.username=root
spring.datasource.password=admin
```

### Migrations

O projeto utiliza o Flyway para gerenciar as migrations do banco de dados. As migrations estão localizadas em `src/main/resources/db/migration`.

- **Migrations Aplicadas**: O Flyway rastreia as migrations aplicadas na tabela `flyway_schema_history`.
- **Recriação do Banco de Dados**: Ao recriar o banco de dados, o Flyway aplicará todas as migrations novamente.

### Dados Iniciais

Os dados iniciais são inseridos através da migration `V2__populate_data.sql`. Certifique-se de que essa migration esteja configurada corretamente.

## Executando o Projeto:

### Execute o projeto utilizando o Maven:
```bash
mvn spring-boot:run
```

## Utilização da API
### Documentação da API

A documentação da API foi gerada utilizando o Springdoc OpenAPI, que é a biblioteca mais moderna e compatível com o Spring Boot 3.x.

### Acesso à Documentação:

Para acessar a documentação no Swagger, abra o navegador e acesse:

http://localhost:8080/swagger-ui/index.html#/

### Testes no Postman
Para facilitar os testes da API, você pode importar a coleção do Postman disponível no link abaixo:

https://documenter.getpostman.com/view/9651766/2sAYXCkK6g

## Testes Unitários
Foram implementados testes unitários para garantir a qualidade e o funcionamento correto das funcionalidades da API. Os testes foram desenvolvidos utilizando JUnit e Mockito, seguindo as boas práticas de desenvolvimento de software.

### Executando os Testes:

Para executar todos os testes unitários, utilize o seguinte comando:

```bash
mvn test
```

### Dificuldades Encontradas
- Problemas com o Lombok: 
Durante o desenvolvimento, enfrentei dificuldades com o Lombok. Não tenho certeza se foi um problema com a IDE utilizada no projeto, mas mesmo após reinstalar várias vezes, o plugin do Lombok funcionou apenas parcialmente. Tive dificuldades em definir métodos getter e setter nas minhas entidades.

- Utilização do Springdoc OpenAPI: 
Optei por utilizar o Springdoc OpenAPI em vez do Swagger tradicional, pois ele é mais moderno e compatível com o Spring Boot 3.x. A documentação gerada pode ser acessada através do endpoint mencionado acima.

- Migrations com Flyway: 
Utilizei o Flyway para gerenciar as migrations do banco de dados. É necessário criar o banco de dados manualmente com o nome lojavirtual. A criação das tabelas será feita de maneira automática pelo Flyway.

## Contribuição
Se você deseja contribuir com este projeto, sinta-se à vontade para abrir issues ou enviar pull requests. Toda contribuição é bem-vinda!

## Licença
Este projeto está licenciado sob a licença MIT. Consulte o arquivo LICENSE para mais detalhes.

