# 💬 ChatApp – Aplicativo de Chat em Tempo Real

O **ChatApp** é uma aplicação de chat em tempo real desenvolvida em **Java**, com interface gráfica em **JavaFX** e comunicação via **Sockets TCP**.  
O sistema permite que múltiplos usuários troquem mensagens em um chat público, enquanto o servidor pode enviar mensagens globais ou privadas diretamente pelo console.

---

## ✨ Funcionalidades

### 🖥 Servidor (ChatServer)
- Atribui **ID único** para cada cliente conectado
- Mantém registro de todos os clientes ativos
- Comandos disponíveis no console:
  - `/broadcast [mensagem]` → Envia mensagem para todos os clientes
  - `/msg [clientId] [mensagem]` → Envia mensagem privada para um cliente específico
  - `/quit` → Encerra o servidor e desconecta todos os clientes
- Exibe no console quando um cliente conecta/desconecta
- Loga todas as mensagens trocadas
- Gerenciamento seguro de threads e sincronização para evitar condições de corrida
- Fechamento adequado de sockets e streams

### 📱 Cliente (ChatClient)
- Interface minimalista com cores suaves, bordas arredondadas e tipografia estilo **SF Pro**
- Efeito *hover* no botão de envio
- Funcionalidades:
  1. Envio de mensagens (Enter ou botão **Enviar**)
  2. Exibição em tempo real das mensagens recebidas
  3. *Scroll* automático para novas mensagens
- Identificação única (ex: `client-123abc`) para mensagens privadas
- Fechamento correto da conexão ao sair

---

## 🛠 Tecnologias Utilizadas

- **Java 17** – Linguagem principal
- **JavaFX** – Interface gráfica
- **Sockets TCP** – Comunicação cliente-servidor
- **Multithreading** – Gerenciamento de múltiplos clientes simultâneos
- **CSS** – Estilização da interface

---

## 🚀 Como Executar

1. **Inicie o servidor**
   ```bash
   java ChatServer
Abra um ou mais clientes

bash
java ChatClient
No console do servidor, utilize os comandos:

/broadcast Olá a todos! → Mensagem global

/msg client-123abc Olá só para você! → Mensagem privada

/quit → Encerra o servidor

📷 Captura de Tela:

![Image](https://github.com/user-attachments/assets/52fd9d41-ae83-49b0-9b5d-b6ff0910a2a7)

🎯 Objetivo do Projeto:
Este projeto foi desenvolvido para praticar conceitos de redes em Java, uso de Sockets TCP, multithreading e JavaFX para construção de interfaces gráficas modernas.

