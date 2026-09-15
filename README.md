# 📱 QR Code Generator API

<p align="center">
  <img src="https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 21" />
  <img src="https://img.shields.io/badge/Spring_Boot-3.3.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot 3.3.4" />
  <img src="https://img.shields.io/badge/Amazon_S3-FF9900?style=for-the-badge&logo=amazons3&logoColor=white" alt="Amazon S3" />
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" />
  <img src="https://img.shields.io/badge/License-MIT-blue.svg?style=for-the-badge" alt="License MIT" />
</p>

---

## 📌 Visão Geral

A **QR Code Generator API** é um microsserviço moderno desenvolvido em **Java 21** e **Spring Boot 3.3.4**, projetado para gerar códigos QR em tempo real a partir de textos ou links e armazená-los de forma escalável na nuvem através do **Amazon AWS S3**.

O projeto adota os princípios da **Arquitetura Hexagonal (Ports & Adapters)**, garantindo baixo acoplamento, alta testabilidade e separação clara entre a lógica de negócios e os serviços de infraestrutura externa.

---

## 🚀 Funcionalidades

- ⚡ **Geração Dinâmica de QR Code**: Renderização rápida de imagens no formato PNG com matriz 200x200 via biblioteca **Google ZXing**.
- ☁️ **Integração com AWS S3**: Armazenamento em nuvem seguro com retorno imediato da URL pública do arquivo gerado.
- 🔑 **Nomes Únicos com UUID**: Prevenção de conflito e sobrescrita de arquivos utilizando identificadores únicos universais.
- 🧩 **Arquitetura Hexagonal (Ports and Adapters)**: Camada de domínio independente de provedores de infraestrutura específicos.
- 🐳 **Pronto para Produção com Docker**: Build otimizado em múltiplos estágios (*multi-stage build*) para imagens leves e seguras.

---

## 🏛️ Arquitetura

O projeto segue a **Arquitetura Hexagonal (Ports & Adapters)**:

```mermaid
flowchart LR
    subgraph Client ["Cliente"]
        Req["HTTP POST /qrcode"]
    end

    subgraph Presentation ["Camada de Apresentação"]
        Controller["QrCodeController"]
    end

    subgraph Core ["Núcleo da Aplicação"]
        Service["QrCodeGeneratorService"]
        ZXing["Google ZXing Engine"]
        Port["<< Interface >>\nStoragePort"]
    end

    subgraph Infrastructure ["Infraestrutura"]
        Adapter["S3StorageAdapter\n(AWS SDK v2)"]
        S3[("Amazon S3 Bucket")]
    end

    Req --> Controller
    Controller --> Service
    Service --> ZXing
    Service --> Port
    Port -.-> Adapter
    Adapter --> S3
```

---

## 🛠️ Tecnologias e Bibliotecas

| Tecnologia | Versão | Descrição |
| :--- | :--- | :--- |
| **[Java](https://www.oracle.com/java/)** | 21 (LTS) | Linguagem de programação |
| **[Spring Boot](https://spring.io/projects/spring-boot)** | 3.3.4 | Framework base da aplicação |
| **[Google ZXing](https://github.com/zxing/zxing)** | 3.5.2 | Biblioteca para geração e processamento de QR Codes |
| **[AWS SDK for Java v2 (S3)](https://aws.amazon.com/sdk-for-java/)** | 2.24.12 | Cliente oficial para integração com Amazon S3 |
| **[Docker](https://www.docker.com/)** | - | Containerização e deploy |
| **[Maven](https://maven.apache.org/)** | 3.9+ | Gerenciador de dependências e build |

---

## 📁 Estrutura do Projeto

```text
qrcode-generator/
├── src/
│   ├── main/
│   │   ├── java/com/jbe/qrcode_generator/
│   │   │   ├── controller/               # Controladores REST (Entrypoints)
│   │   │   │   └── QrCodeController.java
│   │   │   ├── dto/                      # Data Transfer Objects (Records)
│   │   │   │   ├── QrCodeGenerateRequest.java
│   │   │   │   └── QrCodeGenerateResponse.java
│   │   │   ├── service/                  # Regras de Negócio e Geração de Imagem
│   │   │   │   └── QrCodeGeneratorService.java
│   │   │   ├── ports/                    # Interfaces de Portas (Arquitetura Hexagonal)
│   │   │   │   └── StoragePort.java
│   │   │   ├── infrastructure/           # Adaptadores de Infraestrutura (S3 Client)
│   │   │   │   └── S3StorageAdapter.java
│   │   │   └── QrcodeGeneratorApplication.java
│   │   └── resources/
│   │       └── application.properties    # Configurações do Spring Boot
│   └── test/
│       └── java/com/jbe/qrcode_generator/
│           └── QrcodeGeneratorApplicationTests.java
├── Dockerfile                            # Dockerfile com Multi-Stage Build
├── pom.xml                               # Dependências Maven
└── README.md                             # Documentação do projeto
```

---

## ⚙️ Variáveis de Ambiente

A aplicação necessita das seguintes variáveis de ambiente configuradas para conectar ao AWS S3:

| Variável | Descrição | Exemplo |
| :--- | :--- | :--- |
| `AWS_REGION` | Região do bucket AWS S3 | `us-east-1` |
| `AWS_BUCKET_NAME` | Nome do bucket S3 onde as imagens serão salvas | `meu-bucket-qrcode` |
| `AWS_ACCESS_KEY_ID` | Chave de acesso IAM da AWS | `AKIAIOSFODNN7EXAMPLE` |
| `AWS_SECRET_ACCESS_KEY` | Chave secreta de acesso IAM da AWS | `wJalrXUtnFEMI/K7MDENG/bPxRfiCYEXAMPLEKEY` |

> 💡 **Nota:** O AWS SDK v2 detecta automaticamente as credenciais via variáveis de ambiente padrão (`AWS_ACCESS_KEY_ID` e `AWS_SECRET_ACCESS_KEY`) ou via AWS CLI Credentials (`~/.aws/credentials`).

---

## 🚀 Como Executar

### Pré-requisitos
- **Java 21** instalado.
- **Maven** instalado (ou utilize o wrapper `./mvnw` / `mvnw.cmd`).
- Conta AWS com um bucket S3 configurado e credenciais IAM com permissão `s3:PutObject`.
- **Docker** (opcional, para execução em container).

---

### 1. Executando Localmente com Maven

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/JBEstevan/qrcode-generator.git
   cd qrcode-generator
   ```

2. **Defina as variáveis de ambiente:**

   **No Linux / macOS:**
   ```bash
   export AWS_REGION=us-east-1
   export AWS_BUCKET_NAME=nome-do-seu-bucket
   export AWS_ACCESS_KEY_ID=sua_access_key
   export AWS_SECRET_ACCESS_KEY=sua_secret_key
   ```

   **No Windows (PowerShell):**
   ```powershell
   $env:AWS_REGION="us-east-1"
   $env:AWS_BUCKET_NAME="nome-do-seu-bucket"
   $env:AWS_ACCESS_KEY_ID="sua_access_key"
   $env:AWS_SECRET_ACCESS_KEY="sua_secret_key"
   ```

3. **Inicie a aplicação:**

   **No Linux / macOS:**
   ```bash
   ./mvnw spring-boot:run
   ```

   **No Windows:**
   ```cmd
   mvnw.cmd spring-boot:run
   ```

   A aplicação estará disponível em `http://localhost:8080`.

---

### 2. Executando com Docker

Você pode construir e rodar a imagem com build em múltiplos estágios:

1. **Build da imagem:**
   ```bash
   docker build -t qrcode-generator .
   ```

2. **Executar o container passando as variáveis de ambiente:**
   ```bash
   docker run -d -p 8080:8080 \
     -e AWS_REGION="us-east-1" \
     -e AWS_BUCKET_NAME="nome-do-seu-bucket" \
     -e AWS_ACCESS_KEY_ID="sua_access_key" \
     -e AWS_SECRET_ACCESS_KEY="sua_secret_key" \
     --name qrcode-api qrcode-generator
   ```

---

## 📖 Documentação da API

### Gerar QR Code e Fazer Upload no S3

Gera a imagem de QR Code correspondente ao texto/link fornecido, salva o arquivo PNG no bucket S3 e retorna o link direto da imagem.

- **Método:** `POST`
- **Rota:** `/qrcode`
- **Content-Type:** `application/json`

#### Corpo da Requisição (Request Body)
```json
{
  "text": "https://github.com/JBEstevan"
}
```

#### Resposta de Sucesso (`200 OK`)
```json
{
  "url": "https://meu-bucket-qrcode.s3.us-east-1.amazonaws.com/e7a25b1f-361c-4b5a-932d-94bb8a7e3bf9"
}
```

#### Resposta de Erro (`500 Internal Server Error`)
Retornado caso ocorra falha no processamento da imagem ou comunicação com a AWS.

---

### Exemplos de Chamada

#### cURL
```bash
curl -X POST http://localhost:8080/qrcode \
  -H "Content-Type: application/json" \
  -d '{"text": "https://github.com/JBEstevan"}'
```

#### HTTPie
```bash
http POST :8080/qrcode text="https://github.com/JBEstevan"
```

#### PowerShell
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/qrcode" `
  -Method POST `
  -ContentType "application/json" `
  -Body '{"text": "https://github.com/JBEstevan"}'
```

---

## 🧪 Executando os Testes

Para executar os testes automatizados da aplicação:

```bash
./mvnw test
# ou no Windows:
mvnw.cmd test
```

---

## 📄 Licença

Este projeto está sob a licença [MIT](file:///LICENSE) - veja o arquivo de licença para mais detalhes.

---

## 👨‍💻 Autor

Desenvolvido por **[Juan Estevan](https://github.com/JBEstevan)**.