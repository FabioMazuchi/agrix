# Agrix 🌱

API REST para gerenciamento de fazendas, plantações e fertilizantes, desenvolvida com **Java 17**, **Spring Boot 3**, **Spring Data JPA**, **Spring Security**, **JWT**, **H2** e **Swagger/OpenAPI**.


## 👨‍💻 Sobre

Projeto desenvolvido para demonstrar conhecimentos em desenvolvimento back-end com Java e Spring Boot, construção de APIs REST, persistência com JPA, autenticação/autorização com Spring Security e JWT, documentação com Swagger e deploy em ambiente cloud.
#
### Este README apresenta duas formas de executar e testar a aplicação: localmente e pela versão publicada na Render.

## 🔗 Acessos

| Recurso | Endereço |
|---|---|
| API na Render | https://agrix-kv2e.onrender.com |
| Swagger na Render | https://agrix-kv2e.onrender.com/swagger-ui/index.html |
| OpenAPI JSON | https://agrix-kv2e.onrender.com/v3/api-docs |
| Swagger local | http://localhost:8080/swagger-ui/index.html |

> **Atenção:** a instância gratuita da Render pode ficar inativa após um período sem acessos. A primeira requisição pode demorar enquanto o serviço é iniciado.

## 🧰 Tecnologias

- Java 17
- Spring Boot 3.1.1
- Maven
- Spring Web
- Spring Data JPA / Hibernate
- Spring Security
- Autenticação JWT (java-jwt)
- H2 Database
- MySQL Connector (dependência disponível no projeto)
- Springdoc OpenAPI / Swagger UI

## ✨ Funcionalidades

- Cadastro de usuários com perfil de acesso.
- Autenticação com usuário e senha.
- Geração e validação de token JWT.
- Cadastro e consulta de fazendas.
- Cadastro e consulta de plantações.
- Consulta de plantações por fazenda.
- Pesquisa de plantações por intervalo de datas de colheita.
- Cadastro e consulta de fertilizantes.
- Associação de fertilizantes às plantações.
- Controle de acesso por perfil (`ADMIN`, `MANAGER` e `USER`).
- Documentação interativa dos endpoints com Swagger.

## ▶️ Executar localmente

### Pré-requisitos

- JDK 17.
- Git.
- Maven instalado **ou** utilização do Maven Wrapper incluído no projeto.

Confira as versões:

```bash
java -version
mvn -version
```
### 1. Configurar o segredo JWT

A aplicação utiliza a propriedade `api.security.token.secret`. Para desenvolvimento, existe um valor padrão no `application.properties`. Para executar com um segredo próprio, defina a variável de ambiente antes de iniciar:

**Linux/macOS:**

```bash
export JWT_SECRET="uma-chave-local-longa-e-aleatoria"
```

**Windows PowerShell:**

```powershell
$env:JWT_SECRET="uma-chave-local-longa-e-aleatoria"
```

### 2. Iniciar a aplicação

Com Maven Wrapper no Linux/macOS:

```bash
chmod +x mvnw
./mvnw spring-boot:run
```

Ou com Maven instalado:

```bash
mvn spring-boot:run
```

Por padrão, o Spring Boot inicia na porta `8080`. Se a variável `PORT` estiver definida no ambiente, a configuração Docker utilizada na Render considera essa porta no deploy. Para testar localmente em outra porta, por exemplo `3000`, execute:

**Linux/macOS:**

```bash
PORT=3000 ./mvnw spring-boot:run
```

**Windows PowerShell:**

```powershell
$env:PORT="3000"
./mvnw spring-boot:run
```

A URL local será `http://localhost:8080` (ou a porta que você configurou).

### 3. Abrir o Swagger local

Acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

Se estiver usando a porta `3000`:

```text
http://localhost:3000/swagger-ui/index.html
```

## ☁️ Testar a aplicação publicada na Render

A aplicação está publicada em:

```text
https://agrix-kv2e.onrender.com
```

Swagger:

```text
https://agrix-kv2e.onrender.com/swagger-ui/index.html
```

### Observação sobre o banco H2 na Render

A configuração padrão do projeto utiliza H2 em memória:

```properties
spring.datasource.url=jdbc:h2:mem:agrixdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
```

A instância na Render possui seu próprio banco em memória, separado do banco local. Portanto:

- Os cadastros feitos localmente não aparecem na Render.
- Os cadastros feitos na Render não aparecem localmente.
- Os dados em memória podem ser perdidos quando a aplicação reiniciar ou for recriada.
- A versão publicada utiliza H2 sem exigir um serviço PostgreSQL separado.

O projeto também contém `application-prod.properties` com configuração para PostgreSQL. **Não ative o perfil `prod` na implantação H2**; mantenha o perfil padrão para utilizar o H2 definido em `application.properties`.

## 🔐 Fluxo de autenticação JWT

Os endpoints protegidos exigem um token no cabeçalho:

```http
Authorization: Bearer SEU_TOKEN_JWT
```

### 1. Criar um usuário para teste

Execute `POST /persons` no Swagger.

Exemplo de corpo:

```json
{
  "username": "recrutador",
  "password": "SenhaForte123!",
  "role": "ADMIN"
}
```

A senha é armazenada com hash BCrypt. O endpoint de cadastro está liberado para permitir a criação inicial de usuário.

> **Nota de segurança:** o cadastro público permite informar o perfil no corpo da requisição. Isso é adequado apenas para a demonstração deste projeto. Em um sistema real, a atribuição de `ADMIN` deve ser restrita e controlada no servidor. Não utilize senhas reais ou reutilizadas.

Se o usuário já existir, escolha outro nome de usuário para o teste.

### 2. Fazer login

Execute `POST /auth/login` com:

```json
{
  "username": "recrutador",
  "password": "SenhaForte123!"
}
```

A resposta contém o token:

```json
{
  "token": "eyJ..."
}
```

### 3. Autorizar no Swagger

1. Copie somente o valor do campo `token`.
2. Clique em **Authorize** no topo do Swagger.
3. Cole o token no campo Bearer.
4. Confirme em **Authorize** e feche a janela.
5. Execute os endpoints protegidos.

O Swagger envia o cabeçalho `Authorization: Bearer ...` automaticamente.

O token expira após **2 horas**. Faça login novamente quando expirar.

### Perfis de acesso

| Perfil | Descrição |
|---|---|
| `ADMIN` | Administrador |
| `MANAGER` | Gerente |
| `USER` | Usuário |

O endpoint `GET /fertilizers` exige o perfil `ADMIN`. Um token válido de `USER` ou `MANAGER` não é suficiente para acessar esse endpoint.

## 🧪 Roteiro sugerido para testar a API

Os exemplos abaixo seguem os DTOs e rotas implementados no projeto. Execute as operações na ordem sugerida para criar os relacionamentos necessários.

### 1. Usuários e autenticação

| Método | Endpoint | Acesso |
|---|---|---|
| `POST` | `/persons` | Público |
| `POST` | `/auth/login` | Público |

### 2. Fazendas

| Método | Endpoint | Acesso |
|---|---|---|
| `POST` | `/farms` | Autenticado |
| `GET` | `/farms` | `ADMIN`, `MANAGER` ou `USER` |
| `GET` | `/farms/{farmId}` | Autenticado |
| `POST` | `/farms/{farmId}/crops` | Autenticado |
| `GET` | `/farms/{farmId}/crops` | Autenticado |

Exemplo de `POST /farms`:

```json
{
  "name": "Fazenda Boa Esperança",
  "size": 150.5
}
```

### 3. Plantações

| Método | Endpoint | Acesso |
|---|---|---|
| `GET` | `/crops` | `ADMIN` ou `MANAGER` |
| `GET` | `/crops/{cropId}` | Autenticado |
| `GET` | `/crops/search?start=2025-01-01&end=2025-12-31` | Autenticado |
| `POST` | `/farms/{farmId}/crops` | Autenticado |
| `GET` | `/farms/{farmId}/crops` | Autenticado |
| `POST` | `/crops/{cropId}/fertilizers/{fertilizerId}` | Autenticado |
| `GET` | `/crops/{cropId}/fertilizers` | Autenticado |

Exemplo de `POST /farms/{farmId}/crops`:

```json
{
  "name": "Milho",
  "plantedArea": 50.0,
  "plantedDate": "2025-01-10",
  "harvestDate": "2025-06-10"
}
```

### 4. Fertilizantes

| Método | Endpoint | Acesso |
|---|---|---|
| `POST` | `/fertilizers` | Autenticado |
| `GET` | `/fertilizers` | Somente `ADMIN` |
| `GET` | `/fertilizers/{fertilizerId}` | Autenticado |

Exemplo de `POST /fertilizers`:

```json
{
  "name": "Adubo Orgânico",
  "brand": "AgriSolo",
  "composition": "Nitrogênio, fósforo e potássio"
}
```

Depois de criar uma plantação e um fertilizante, associe-os:

```text
POST /crops/{cropId}/fertilizers/{fertilizerId}
```

Por exemplo:

```text
POST /crops/1/fertilizers/1
```

## 📬 Testar com Postman ou cURL

O repositório contém a coleção `Agrix.postman_collection.json`.

1. Abra o Postman.
2. Importe o arquivo `Agrix.postman_collection.json`.
3. Atualize a URL-base das requisições para `http://localhost:8080` ou `https://agrix-kv2e.onrender.com`.
4. Faça o cadastro e o login.
5. Copie o JWT retornado e configure o Bearer Token nas requisições protegidas.

Exemplo de login com cURL:

```bash
curl -X POST "https://agrix-kv2e.onrender.com/auth/login" \
  -H "Content-Type: application/json" \
  -d '{"username":"recrutador","password":"SenhaForte123!"}'
```

Exemplo de consulta de fertilizantes (substitua pelo token retornado no login):

```bash
curl -X GET "https://agrix-kv2e.onrender.com/fertilizers" \
  -H "Accept: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_JWT"
```

## 📁 Estrutura principal

```text
src/
└── main/
    ├── java/com/betrybe/agrix/
    │   ├── AgrixApplication.java
    │   └── ebytr/staff/
    │       ├── controller/
    │       ├── entity/
    │       ├── exception/
    │       ├── repository/
    │       ├── security/
    │       └── service/
    └── resources/
        ├── application.properties
        └── application-prod.properties
```
