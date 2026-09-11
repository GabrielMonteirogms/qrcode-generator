# QR Code Generator API

API REST desenvolvida em **Java 21** e **Spring Boot** para geração automatizada e persistência em nuvem de QR Codes. O projeto foi estruturado aplicando os conceitos de **Arquitetura Hexagonal (Ports and Adapters)** para garantir desacoplamento entre as regras de negócio, os protocolos de entrada (HTTP) e os serviços de infraestrutura (AWS S3).

## Stack Tecnológica

* **Java 21** (LTS)
* **Spring Boot**
* **AWS SDK for Java v2** (`S3Client`)
* **Docker**

---

## Decisões Arquiteturais & Padrões

* **Arquitetura Hexagonal:** O domínio da aplicação e os casos de uso isolam completamente a lógica de negócio de frameworks e SDKs externos. Portas (interfaces) definem os contratos de comunicação, enquanto os adaptadores implementam as tecnologias concretas (como persistência em nuvem).
* **AWS SDK v2:** Utilização da versão 2 do SDK da AWS, que traz melhorias de performance, suporte a programação reativa/assíncrona opcional e melhor controle de ciclo de vida de clientes HTTP (`S3Client`).
* **Containerização:** Empacotado via Docker com injeção segura de credenciais e variáveis de ambiente em tempo de execução, simulando um ambiente de produção real.

---

## Estrutura do Projeto

```text
src/main/java/com/gabrielmonteiro/qrcode/generator/
├── controller/          # Adaptadores de Entrada (REST Controllers)
├── service/             # Casos de Uso / Regras de Negócio
├── port/                # Portas de Saída (Contratos de Domínio)
└── infrastructure/      # Adaptadores de Saída (Implementação AWS S3)
