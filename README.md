# Webchat online com Chatbot - FURIA

Webchat online desenvolvido para a vaga de Assistente de Engenharia de Software da FURIA Tech

# ⚒️ Funcionalidades
* Envio de mensagens públicas
* Envio de mensagens privadas (mensagem começando com **/private:{Usuário}**)
* Interação com bot (mensagem começando com **/bot**)
* Destaques na coloração para diferenciação de mensagens públicas e privadas

## 🛠️ Tecnologias utilizadas
* Java
* Spring Boot
* WebSocket com STOMP
* Docker
* Ollama (para rodar LLM localmente)
* Spring AI (Ollama)

## 📷 Layout da aplicação

### Tela inicial
![image](https://github.com/user-attachments/assets/c7969c42-1ccc-4c47-8bea-a53e7737a0bf)

### Usuário logado
![image](https://github.com/user-attachments/assets/0ee52fe4-b6ac-4546-859a-f92363d3730f)

## 🐳 Docker
Para utilizar a aplicação via Docker, siga os passos:
1. Clonar o repositório:
```bash
git clone https://github.com/h-Soares/web-chat-furia
```

2. Dentro da pasta clonada, rodar a aplicação
(**ATENÇÃO**: aguardar o download completo do LLM *gemma3:1b* antes de usar!)
```bash
docker-compose up
```

## 👀 Exemplo de uso
https://github.com/user-attachments/assets/2b7a6c92-6b70-4d58-b76c-af9fba29de71


