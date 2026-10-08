# Digital Menu API (Em Desenvolvimento)

API REST desenvolvida em **Java 21** e **Spring Boot** para orquestrar o fluxo de pedidos em restaurantes via QR Code. 

Nascido originalmente como um serviço isolado para geração e armazenamento em nuvem de QR Codes, o sistema evoluiu para o *core backend* de um **Menu Digital** completo. Atualmente, a API gerencia o ciclo de vida das comandas, alocação de mesas, catálogo de produtos e cálculos financeiros de pedidos em tempo real.

## Stack Tecnológica

* **Linguagem & Framework:** Java 21 (LTS) + Spring Boot 3
* **Persistência de Dados:** Spring Data JPA / Hibernate (Bancos Relacionais)
* **Cloud & Storage:** AWS SDK for Java v2 (`S3Client`) para persistência dos QR Codes gerados
* **Infraestrutura:** Docker (Containerização e injeção segura de credenciais)
* **Design da API:** RESTful, com validação de dados via `jakarta.validation`

## Decisões Arquiteturais e Padrões de Projeto

O projeto é guiado pelos princípios da **Arquitetura Hexagonal (Ports and Adapters)**, garantindo que o coração da aplicação (Regras de Negócio) fique completamente blindado contra mudanças de infraestrutura, banco de dados ou protocolos de comunicação.

* **Desacoplamento Rigoroso:** Controllers lidam apenas com HTTP, Services contêm regras de negócio puras, e Repositories/Adapters cuidam da persistência.
* **Integridade Financeira (Snapshot de Preços):** Implementação de regras críticas de mercado. O preço dos itens nas comandas é congelado no ato da compra (`unitPrice` snapshot), imunizando o histórico de vendas contra flutuações futuras no catálogo de produtos.
* **Single Source of Truth (Fonte Única de Verdade):** Cálculos financeiros (como o total da comanda) são processados dinamicamente em memória via Java Streams, evitando armazenamento redundante e risco de inconsistência no banco de dados.
* **Data Transfer Objects (DTOs):** Uso intenso de *Records* do Java para imutabilidade e tráfego seguro de dados entre as camadas, sem expor as entidades do banco de dados ao cliente.

## 📂 Estrutura do Projeto

A organização dos pacotes reflete as camadas de isolamento da arquitetura:

```text
src/main/java/com/gabrielmonteiro/qrcode/generator/
├── controller/          # Adaptadores de Entrada (REST API, manipulação de DTOs)
├── dto/                 # Contratos de entrada e saída (Records imutáveis)
├── service/             # Casos de Uso e Regras de Negócio Core
├── entities/            # Modelos de Domínio (Order, OrderItem, Product, RestaurantTable)
├── repositories/        # Portas de Saída (Interfaces JPA)
└── infrastructure/      # Adaptadores de Saída (Integração AWS S3, configurações externas)
