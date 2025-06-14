# Sistema de Gestão de Oficina Mecânica

Este projeto implementa um sistema básico para gestão de uma oficina mecânica, permitindo o cadastro e gerenciamento de clientes, veículos, mecânicos e ordens de serviço. O sistema foi desenvolvido em Java, aplicando os principais conceitos de Programação Orientada a Objetos (POO) e persistência de dados em arquivos de texto.

## Funcionalidades Principais

- **Gestão de Clientes**: Cadastro, listagem, busca por CPF, atualização e remoção de clientes.
- **Gestão de Veículos**: Cadastro, listagem, busca por placa, atualização e remoção de veículos (carros e motos, com atributos específicos).
- **Gestão de Mecânicos**: Cadastro, listagem, atualização e remoção de mecânicos.
- **Gestão de Ordens de Serviço**: Criação, listagem, atualização de status e adição/remoção de serviços.
- **Persistência de Dados**: Todos os dados são salvos em arquivos `.txt` para garantir a persistência entre as execuções do programa.
- **Tratamento de Exceções Personalizado**: Implementação de exceções específicas para cenários como CPF duplicado ou remoção de entidades associadas.
- **Interface Java**: Utilização de interface para detalhamento de informações de clientes e ordens de serviço.

## Estrutura do Projeto

O projeto segue uma estrutura próxima ao padrão MVC (Model-View-Controller):

```
Oficina_Mecanica-master/
├── src/
│   └── com/
│       └── oficinamecanica/
│           ├── controller/
│           │   └── OficinaController.java
│           ├── exceptions/
│           │   ├── CpfDuplicadoException.java
│           │   ├── OrdemServicoAssociadaException.java
│           │   └── VeiculoAssociadoException.java
│           ├── interfaces/
│           │   └── IDetalhavel.java
│           ├── main/
│           │   └── Main.java
│           ├── model/
│           │   ├── Carro.java
│           │   ├── Cliente.java
│           │   ├── Mecanico.java
│           │   ├── Moto.java
│           │   ├── OrdemServico.java
│           │   ├── Pessoa.java
│           │   ├── Servico.java
│           │   └── Veiculo.java
│           ├── persistencia/
│           │   ├── OficinaData.java
│           │   └── PersistenciaController.java
│           └── view/
│               └── OficinaView.java
└── .vscode/
    └── settings.json
```

- **`model/`**: Contém as classes que representam as entidades do negócio (Cliente, Veiculo, OrdemServico, etc.) e suas regras.
- **`view/`**: Responsável pela interação com o usuário, exibindo menus e coletando entradas.
- **`controller/`**: Gerencia a lógica de negócio, intermediando a comunicação entre Model e View.
- **`persistencia/`**: Lida com a leitura e escrita dos dados em arquivos.
- **`exceptions/`**: Contém as classes de exceções personalizadas.
- **`interfaces/`**: Contém as interfaces do projeto.
- **`main/`**: Contém a classe principal para iniciar a aplicação.

## Como Executar o Projeto

Para executar este projeto, siga os passos abaixo:

1.  **Pré-requisitos**: Certifique-se de ter o Java Development Kit (JDK) instalado em sua máquina (versão 8 ou superior).

2.  **Compilação**: Navegue até o diretório `src` do projeto e compile os arquivos `.java`:
    ```bash
    javac com/oficinamecanica/main/Main.java
    ```
    *Nota: Pode ser necessário compilar todos os arquivos recursivamente ou usar um IDE como VS Code, IntelliJ IDEA ou Eclipse para facilitar a compilação e execução.*

3.  **Execução**: Após a compilação, execute a classe principal:
    ```bash
    java com.oficinamecanica.main.Main
    ```

    O sistema será iniciado no console, apresentando o menu principal.

## Membros do Grupo

- [Seu Nome Completo] (Responsável pela implementação das exceções personalizadas, interface Java e README.md)
- [Nome do Colega 1]
- [Nome do Colega 2]
- [Nome do Colega 3]

## Requisitos Atendidos (Conforme solicitado pelo Professor)

-   **Herança e Classes Abstratas**: Implementado nas classes `Pessoa` (abstrata) e suas subclasses `Cliente` e `Mecanico`, e `Veiculo` (abstrata) e suas subclasses `Carro` e `Moto`.
-   **Polimorfismo (Sobrecarga e Sobrescrita)**: Presente em métodos como `toString()` sobrescritos em diversas classes, e na manipulação de objetos `Pessoa` e `Veiculo` de forma genérica.
-   **Interface Obrigatória**: A interface `IDetalhavel` foi criada e implementada pelas classes `Cliente` e `OrdemServico`, com o método `gerarRelatorio()`.
-   **Tratamento de Exceções**: Implementado com exceções personalizadas (`CpfDuplicadoException`, `VeiculoAssociadoException`, `OrdemServicoAssociadaException`) para tratamento de erros específicos do negócio.
-   **CRUDs (mínimo 3)**: Implementados CRUDs completos para Clientes, Veículos e Mecânicos, além de operações de adição/remoção de serviços em Ordens de Serviço.
-   **Coleções Java**: Utilização de `ArrayList` para armazenar listas de clientes, mecânicos, veículos, ordens de serviço e serviços.
-   **Persistência em Arquivos `.txt`**: Os dados são serializados e deserializados para arquivos de texto, garantindo a persistência.
-   **Projeto Organizado e Funcional**: A estrutura de pacotes segue o padrão MVC, e o código é modular e funcional.


