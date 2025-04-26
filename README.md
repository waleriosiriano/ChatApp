📌 ChatApp
É uma aplicação de chat em tempo real desenvolvida em Java, utilizando:

-> JavaFX para a interface gráfica

-> Sockets TCP para comunicação cliente-servidor

-> Multithreading para lidar com múltiplos clientes simultaneamente

-> A aplicação permite que vários usuários troquem mensagens em um chat público, enquanto o servidor pode enviar mensagens globais ou privadas via console.

💻 Servidor (ChatServer)
Cada cliente recebe um ID único para identificação.
O servidor mantém um registro de todos os clientes conectados.
Comandos pelo console do servidor como: 
1. /broadcast [mensagem] → Envia uma mensagem para todos os clientes.

2. /msg [clientId] [mensagem] → Envia uma mensagem privada para um cliente específico.

3. /quit → Encerra o servidor graciosamente, desconectando todos os clientes.

Exibe no console quando um cliente conecta/desconecta.
Loga todas as mensagens trocadas.
Gerenciamento seguro de threads
Sincronização para evitar condições de corrida.
Fechamento adequado de sockets e streams.

📱 Cliente (ChatClient)

Design minimalista com cores suaves.
Bordas arredondadas e tipografia SF Pro-like.
Efeito hover no botão de envio.
Funcionalidades do chat:

1.Envio de mensagens (pressionar Enter ou clicar em Enviar).

2.Exibição em tempo real das mensagens recebidas.

3.Scroll automático para novas mensagens.

Identificação única. Cada cliente recebe um ID (ex: client-123abc).
Pode ser usado para mensagens privadas pelo servidor.
Fecha a conexão corretamente ao sair.

🚀 Como Usar:

1.Inicie o servidor (ChatServer).

2.Abra um ou mais clientes (ChatClient).

3.No console do servidor, digite:

/broadcast Olá a todos! → Mensagem global.

/msg client-123abc Olá só para você! → Mensagem privada.

/quit → Encerra o servidor.
___________________________________________________________

![Image](https://github.com/user-attachments/assets/52fd9d41-ae83-49b0-9b5d-b6ff0910a2a7)
