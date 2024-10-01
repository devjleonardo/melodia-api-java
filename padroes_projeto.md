---

# Boas Práticas e Padrões de Projeto

Este documento detalha as melhores práticas e padrões de projeto que devem ser seguidos para garantir que a API seja escalável, legível, segura e fácil de manter. Seguir essas diretrizes resultará em um código limpo e eficiente, facilitando o trabalho em equipe e a manutenção a longo prazo.

## 1. Estrutura de Projeto

### 1.1 Organização de Pacotes
- *Domain*
  - **domain.model:** Contém as representações das entidades/modelos de dados principais da aplicação.
  - **domain.service:** Inclui os serviços que contêm a lógica de negócios da aplicação.
  - **domain.repository:** Contém interfaces e classes que interagem com o banco de dados.
  - **domain.exception:** Centraliza as exceções personalizadas relacionadas ao "domínio"

- *API*
  - **api.controller:** Responsáveis por receber e responder às requisições HTTP.
  - **api.convert:** Contém classes que fazem a conversão entre os modelos de domínio e os DTOs.
  - **api.dto:** Usados para transportar dados entre camadas e como retorno das APIs.
  - **api.exception:** Centraliza as exceções personalizadas específicas da API

- *Core*
  - **core:** Contém classes e componentes que são compartilhados entre as camadas ou que não se enquadram em nenhuma camada específica, mas são essenciais para o funcionamento da aplicação. Exemplos incluem classes utilitárias, configurações gerais, segurança, gerenciamento de transações, e outros serviços centrais que sustentam a aplicação como um todo.

### 1.2 Nomeação de Pacotes e Classes
- Use nomes significativos e padronizados.
- Pacotes em **minúsculas** e palavras compostas separadas por ponto (`.`, por exemplo, `com.example.api.service`).
- Classes e Interfaces devem ser nomeadas em **PascalCase** (exemplo: `UsuarioService`, `ProdutoRepository`).

## 2. Padrões de Nomenclatura

### 2.1 Variáveis
- Use **camelCase** para variáveis e parâmetros.
- Nomes devem ser descritivos e claros (exemplo: `total`, `usuarioRepository`).
- Evite abreviações que não sejam amplamente conhecidas.

### 2.2 Métodos
- Use **camelCase** para nomear métodos.
- Os nomes dos métodos devem ser verbos e descrever claramente a ação realizada (exemplo: `calculaTotal()`, `buscaUsuarioPorId()`).

### 2.3 Classes
- Use **PascalCase**.
- Nomes de classes devem ser substantivos que descrevam o propósito da classe (exemplo: `UsuarioController`, `OrdemService`).

### 2.4 Constantes
- Use **UPPER_SNAKE_CASE** para constantes.
- Coloque as constantes no início das classes (exemplo: `public static final String VERSAO_API = "v1";`).

## 3. Controllers

### 3.1 Design
- Mantenha os controllers leves e focados em gerenciar requisições e respostas HTTP.
- Toda a lógica de negócios deve ser delegada para as camadas de serviço.

### 3.2 Rotas e Endpoints
- Use verbos HTTP de acordo com o padrão RESTful:
  - `GET`: Para obter recursos.
  - `POST`: Para criar novos recursos.
  - `PUT`: Para atualizar recursos existentes.
  - `DELETE`: Para remover recursos.
- As rotas devem ser em **kebab-case** e no plural (exemplo: `/api/v1/users`, `/api/v1/orders`).
- Utilize versionamento na URL da API (exemplo: `/api/v1`).

### 3.3 Retornos e Status HTTP
- Use os códigos de status HTTP corretos com o EntityManager:
  - `200 OK`: Sucesso na operação.
  - `201 Created`: Recurso criado com sucesso.
  - `204 No Content`: Operação concluída com sucesso, sem conteúdo para retornar.
  - `400 Bad Request`: Requisição inválida.
  - `401 Unauthorized`: Falta de autenticação.
  - `403 Forbidden`: Acesso negado.
  - `404 Not Found`: Recurso não encontrado.
  - `500 Internal Server Error`: Erro inesperado no servidor.

## 4. Serviços (Services)

### 4.1 Design
- Serviços devem conter toda a lógica de negócios da aplicação.
- Devem ser injetados nos controllers via injeção de dependência `@Autowired`.
- O `@Autowired` deve ser inserido a cima da variável, não via construtor.

### 4.2 Boas Práticas
- Métodos de serviços devem ser pequenos e focados em uma única responsabilidade.
- Caso o método seja complexo, considere dividir em métodos privados dentro do mesmo serviço.

## 5. Repositórios (Repositories)

### 5.1 Design
- Repositórios devem ser responsáveis por todas as operações de banco de dados.
- Evite colocar lógica de negócios nos repositórios.

### 5.2 Padrões de Projeto
- Utilize o padrão de projeto **Repository** para abstrair a persistência de dados.
- Métodos de repositório devem ser intuitivos e descritivos (exemplo: `findByEmail(String email)`), especialmente aqueles que não são padrões do repositório.

## 6. Tratamento de Exceções

### 6.1 Exceções Customizadas
- Crie exceções customizadas para erros específicos de negócios.
- Utilize um controlador global para tratar exceções `@ControllerAdvice`.

### 6.2 Respostas de Erro
- Padronize a estrutura de resposta de erro com campos como `timestamp`, `status`, `error`, `message`, `path`.
- Exemplo:
  ```json
  {
    "timestamp": "2024-08-25T14:35:00Z",
    "status": 404,
    "error": "Not Found",
    "message": "Resource not found",
    "path": "/api/v1/users/123"
  }
  ```

## 7. DTO (Data Transfer Object) 

### 7.1 Priorize a criação de DTOs por meio de Records.
- As validações dos campos devem ser feitas no DTO.
- Se necessário, e SOMENTE se necessário, a criação de novas anotações de validações estão permitidas.

## 8. Validação

### 8.1 Validação de Entradas
- Use anotações de validação (exemplo: `@NotNull`, `@Size`) nos DTOs para garantir a integridade dos dados.
- Valide as entradas no início dos métodos de serviço.

### 8.2 Mensagens de Erro
- Customize as mensagens de erro de validação para serem claras e informativas.
- Exemplo: `@NotNull(message = "O campo nome é obrigatório")`.

## 9. Segurança

### 9.1 Autenticação e Autorização
- Use OAuth 2.0 ou JWT para autenticação.
- Restrinja o acesso a endpoints sensíveis usando roles e permissions.

### 9.2 CORS
- Configure adequadamente o CORS para permitir o acesso de clientes confiáveis.
- Evite permissões globais que possam comprometer a segurança da API.

## 10. Testes

### 10.1 Testes Unitários
- Priorize a cobertura de testes unitários para garantir que cada componente funcione isoladamente.
- Use mocks para isolar dependências.

### 10.2 Testes de Integração
- Teste a interação entre diferentes partes da aplicação.
- Inclua testes que validem a integração com o banco de dados.

### 10.3 Testes de API
- Use ferramentas como Postman ou Insomnia para testar manualmente os endpoints.
- Automatize os testes de API usando frameworks como JUnit.

## 11. Documentação

### 11.1 Documentação de Endpoints
- Documente todos os endpoints usando Swagger ou outra ferramenta de documentação API (como o próprio GitHub).
- Inclua detalhes como parâmetros, exemplos de requisição e resposta, e códigos de status.

### 11.2 Comentários no Código
- Comente o código de maneira clara e concisa onde necessário.
- Evite comentários redundantes que apenas repitam o que o código já expressa.

## 12. Versionamento e Deploy

### 12.1 Versionamento de Código
- Utilize o controle de versão (Git) para gerenciar mudanças no código.
- Siga a convenção de commits definida no fluxo de desenvolvimento.

---

# Estrutura de Pastas

A organização de pastas do projeto é essencial para garantir a clareza e a manutenibilidade do código. Nesta estrutura, cada entidade possui seu próprio pacote, onde estão contidas todas as classes e arquivos relacionados, exceto os controllers, que são centralizados em um pacote específico. A estrutura é dividida entre as camadas **domain** e **infra**, proporcionando uma clara separação de responsabilidades.

### 1. Pacote `domain`

O pacote `domain` é onde residem as entidades e a lógica de negócios central do projeto. Cada entidade tem seu próprio subpacote, que contém todos os elementos relacionados àquela entidade, como serviços, repositórios, DTOs e enums.

#### Exemplo de Estrutura de Pastas:

```
src/
└── main/
    └── java/
        └── com/
            └── example/
                └── project/
                    ├── domain/
                    │   ├── model/
                    │   │   ├── Musica.java
                    │   │   ├── Artista.java
                    │   ├── service/
                    │   │   ├── MusicaService.java
                    │   │   ├── ArtistaService.java
                    │   ├── repository/
                    │   │   ├── MusicaRepository.java
                    │   │   ├── ArtistaRepository.java
                    │   └── exception/
                    │       ├── CustomNotFoundException.java
                    ├── api/
                    │   ├── controller/
                    │   │   ├── MusicaController.java
                    │   │   ├── ArtistaController.java
                    │   ├── convert/
                    │   │   ├── MusicaConvert.java
                    │   │   ├── ArtistaConvert.java
                    │   ├── dto/
                    │   │   ├── MusicaDTO.java
                    │   │   ├── ArtistaDTO.java
                    │   └── exception/
                    │       ├── GlobalExceptionHandler.java
                    │       ├── ErrorResponse.java
                    └── core/
                        ├── config/
                        │   ├── SecurityConfig.java
                        │   ├── CorsConfig.java
                        │   └── SwaggerConfig.java
                        └── util/
                            ├── DateUtil.java
                            └── StringUtil.java
```

### 1.1 Pacote `domain`

- **model**: Todas as entidades do sistema (por exemplo, `Musica`, `Artista`) estão centralizadas no pacote `domain.model`. Isso garante que todas as representações das entidades e modelos de dados principais da aplicação estejam agrupadas, facilitando a organização e o acesso às classes de domínio.

- **service**: A lógica de negócios relacionada às entidades é implementada em classes de serviço (ex: `MusicaService`, `ArtistaService`). Esses serviços são responsáveis por gerenciar as operações de negócio, delegando a lógica de persistência para os repositórios.

- **repository**: Interfaces que estendem `JpaRepository` ou `CrudRepository`, responsáveis pela interação com o banco de dados para as entidades específicas (ex: `MusicaRepository`, `ArtistaRepository`).

- **exception**: Exceções personalizadas relacionadas ao domínio são centralizadas em `domain.exception`. Isso facilita o tratamento de erros específicos das regras de negócio e entidades do domínio.

### 1.2 Pacote `api`

- **controller**: Os controllers são responsáveis por gerenciar as requisições HTTP e delegar a lógica de negócio para os serviços correspondentes (ex: `MusicaController`, `ArtistaController`). Eles estão agrupados no pacote `api.controller`.

- **convert**: Classes que fazem a conversão entre os modelos de domínio e os DTOs estão no pacote `api.convert`. Elas são responsáveis por adaptar os dados do domínio para formatos que a API possa manipular e vice-versa (ex: `MusicaConvert`, `ArtistaConvert`).

- **dto**: Objetos de Transferência de Dados são utilizados para transportar dados entre camadas e como retorno das APIs. Estão centralizados no pacote `api.dto` (ex: `MusicaDTO`, `ArtistaDTO`).

- **exception**: No pacote `api.exception`, são centralizadas as exceções personalizadas específicas da API. Isso inclui manipuladores globais de exceções e respostas de erro (ex: `GlobalExceptionHandler`, `ErrorResponse`).

### 1.3 Pacote `core`

- **config**: Todas as configurações da aplicação, como segurança, CORS, Swagger, estão localizadas no pacote `core.config` (ex: `SecurityConfig`, `CorsConfig`, `SwaggerConfig`).

- **util**: Funções e classes utilitárias que são compartilhadas entre as camadas da aplicação, mas que não se enquadram em nenhuma camada específica, estão em `core.util` (ex: `DateUtil`, `StringUtil`).
