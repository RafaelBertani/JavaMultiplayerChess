# JavaMultiplayerChess - Jogo de Xadrez Multiplayer - Cliente/Servidor

Este é um projeto de um **jogo de xadrez multiplayer** desenvolvido em Java como **trabalho de programação da faculdade para o 2º semestre**, utilizando, de acordo com os requisitos:
- Interface gráfica com **Java Swing**
- Comunicação cliente/servidor com **Sockets**
- Persistência de dados com **MySQL (banco de dados relacional)**, cujo código é executado automaticamente ao iniciar o servidor
- Suporte a **múltiplos idiomas**

O jogo permite que multiplos jogadores se conectem a um servidor e sejam redirecionados a salas e joguem xadrez em tempo real. Também estão incluídas funcionalidades como ranking geral dos jogadores e suporte a cinco idiomas.

## ⚙️ Tecnologias Utilizadas

- Java (JDK 8+)
- Java Swing
- Java Sockets
- MySQL
- JDBC

## Instruções para rodar

Para rodar a aplicação em sua máquina, você deve editar o arquivo em `Server/src/database/Database.java` e colocar seu usuário e senha MySQL local.

Exemplo:

```java
private static final String USER = "root";
private static final String PASSWORD = "your_mysql_password_here";

- Inicialize o servidor e um terminal separado
- Inicialize quantas aplicações de cliente quiser, crie ou faça login e jogue
