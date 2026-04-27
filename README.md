# 🍕 PizzaHub API

## 📌 Descrição

API REST para gerenciamento de pedidos de pizzaria, com cálculo automático de valores, controle de status e timeline.

---

## 🚀 Tecnologias

* Java 17
* Spring Web
* Spring Data JPA
* H2 Database
* Lombok
* OpenFeign
* Swagger / OpenAPI
* ViaCEP API

---

## 📂 Funcionalidades

* 👤 Cadastro de clientes com endereço via ViaCEP
* 🍕 Cadastro e controle de pizzas
* 🧾 Criação de pedidos com múltiplos itens
* 💰 Cálculo automático (pedido + entrega)
* 🚚 Taxa de entrega integrada
* ⏱ Tempo estimado automático
* 🔄 Atualização de status com validação
* 📊 Timeline de pedidos
* 📚 Documentação com Swagger
* 🧠 Tratamento global de erros

---

## 💰 Regras de Negócio

* Soma automática dos itens
* Valor de entrega fixo (R$5)
* Total = pedido + entrega
* Só permite transições válidas de status
* Pizza deve estar disponível

---

## 🔗 Endpoints

### 👤 Clientes

* `POST /clientes` → Criar
* `GET /clientes` → Listar
* `GET /clientes/{id}` → Buscar por Id
* `PUT /clientes/{id}` → Atualizar
* `DELETE /clientes/{id}` → Deletar

---

### 🍕 Pizzas

* `POST /pizzas` → Criar
* `GET /pizzas` → Listar
* `GET /pizzas/{id}` → Buscar por Id
* `PUT /pizzas/{id}` → Atualizar
* `DELETE /pizzas/{id}` → Deletar

---

### 📦 Pedidos

* `POST /pedidos` → Criar pedido
* `GET /pedidos` → Listar
* `GET /pedidos/{id}` → Buscar por Id
* `GET /pedidos/status?status=CRIADO` → Buscar Por Status
* `GET /pedidos/{id}/timeline` → Rastrear Timeline
* `PATCH /pedidos/{id}/status?status=EM_PREPARO` → Atualizar Status
* `DELETE /pedidos/{id}` → Deletar

---

## 🗄️ Banco de Dados

* H2 (em memória)

Acesso:

```
http://localhost:8080/h2-console
```

---

## 📑 Swagger

Acesse a documentação:

```
http://localhost:8080/swagger-ui.html
```

---

## ⚠️ Tratamento de Erros

Formato padrão:

```json
{
  "timestamp": "27/04/2026 13:40:29",
  "status": 400,
  "error": "Bad Request",
  "message": "Mensagem do erro",
  "path": "/endpoint"
}
```

---

## ▶️ Como rodar

```bash
mvn spring-boot:run
```

---

## 👨‍💻 Autor

Vitor Duarte
