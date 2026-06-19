GestaoDeContas
Sistema de gestão financeira pessoal desenvolvido em Java, com persistência em ficheiro e arquitetura MVC + DAO, construído em NetBeans.
Funcionalidades
Gestão de Contas — CRUD completo (criar, listar, editar, remover)
Gestão de Transações — CRUD completo de entradas e saídas, com reflexo direto no saldo da conta associada
Gestão de Usuário — controlo de dados do usuário do sistema
Arquitetura
O projeto segue o fluxo padrão Model → DAO → Controller → View:
Código
Camadas
Model — representa as entidades do domínio: usuário, contas, entradas e saídas.
Dao — camada de persistência, responsável por ler e gravar os dados em ficheiro através da classe Repositorio.
Controller — contém a lógica de negócio: validação, registro, edição e remoção de contas e transações, e a atualização dos saldos.
View — interfaces gráficas em Swing, responsáveis pela interação com o usuário.
Persistência
Os dados são armazenados em ficheiro, sem uso de banco de dados relacional. A leitura e escrita são centralizadas na camada Dao.
Como executar
Abrir o projeto no NetBeans
Executar a classe Main
O sistema cria/usa o ficheiro de dados local automaticamente
Tecnologias
Java
Swing (interface gráfica)
Persistência em ficheiro (sem JDBC/SGBD)
NetBeans
Status do projeto
Em desenvolvimento — CRUD de contas e transações implementados; lógica de saldo entre entradas/saídas em ajuste.
