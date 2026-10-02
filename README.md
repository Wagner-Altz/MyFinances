# myFinances

Aplicação desktop em Java Swing para gestão de finanças pessoais: contas, entradas e saídas de dinheiro, com saldos sempre consistentes com o histórico de movimentos.

## Funcionalidades

- **Contas:** criar, editar e remover, com tipo único por conta (sem distinção de maiúsculas).
- **Entradas:** registar, editar e remover; o saldo da conta acompanha cada operação.
- **Saídas:** registar, editar e remover; recusadas se o saldo for insuficiente.
- **Resumo** e **Histórico** de movimentos.
- Dados guardados em ficheiro, sem necessidade de base de dados.

## Regras de negócio

- O saldo de uma conta nunca fica negativo: saídas são recusadas sem saldo, e remover ou editar uma entrada é recusado se a conta ficasse negativa.
- Valores têm de ser positivos (aceita vírgula ou ponto decimal).
- Uma conta com movimentos associados não pode ser removida nem mudar de tipo.
- Os movimentos têm identificadores sequenciais por ano (`ENT-2026001`, `SAI-2026001`), que nunca se repetem após remoções.
- Editar um movimento é atómico: ou todas as validações passam e os saldos são actualizados, ou nada muda.

## Arquitectura

Padrão **MVC + DAO**:

| Pacote | Responsabilidade |
|---|---|
| `Model` | Entidades: `Contas`, `Entradas`, `Saidas`, `Usuario` |
| `View` | Interface Swing (`PainelPrincipal`, `PainelContas`, `PainelEntradas`, `PainelSaidas`, ...) |
| `Controller` | Regras de negócio e validações |
| `Dao` | `Repositorio`: persistência por serialização em `dados/*.dat` |

Os controllers devolvem `null` em caso de sucesso, ou a mensagem de erro, que a vista apresenta ao utilizador.

## Autor

Wagner, estudante universitário. [curso / Universidade Eduardo Mondlane]
