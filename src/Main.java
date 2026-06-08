import model.Funcionario;
import model.Mesa;
import service.FuncionarioService;
import service.MesaService;

private static final Scanner scanner = new Scanner(System.in);
private static final FuncionarioService funcionarioService =
        new FuncionarioService();
private static final MesaService mesaService = new MesaService();


void main() {

    int opcao = -1;

    while (opcao != 0) {
        System.out.println("\n==================================");
        System.out.println("      SISTEMA DE RESTAURANTE      ");
        System.out.println("==================================");
        System.out.println("1 - Gerenciar Funcionários");
        System.out.println("2 - Gerenciar Mesas");
        System.out.println("0 - Sair do Sistema");
        System.out.print("Escolha uma opção: ");

        try {
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> menuFuncionarios();
                case 2 -> menuMesas();
                case 0 -> System.out.println("Encerrando o sistema... Até logo!");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Por favor, digite apenas números.");
        } catch (Exception e) {
            System.out.println("Ocorreu um erro inesperado: " + e.getMessage());
        }
    }
    scanner.close();
}

// ==========================================
//            MENU DE FUNCIONÁRIOS
// ==========================================
private static void menuFuncionarios() {
    int opcao = -1;
    while (opcao != 0) {
        System.out.println("\n--- MENU DE FUNCIONÁRIOS ---");
        System.out.println("1 - Cadastrar novo funcionário");
        System.out.println("2 - Buscar funcionário por ID");
        System.out.println("3 - Atualizar dados do funcionário");
        System.out.println("4 - Listar todos os funcionários");
        System.out.println("5 - Deletar funcionário");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha: ");

        try {
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarFuncionario();
                case 2 -> buscarFuncionario();
                case 3 -> atualizarFuncionario();
                case 4 -> listarFuncionarios();
                case 5 -> deletarFuncionario();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números.");
        }
    }
}

private static void cadastrarFuncionario() {
    System.out.println("\n-- Cadastrar Funcionário --");
    Funcionario func = new Funcionario();

    System.out.print("Nome: ");
    func.setNome(scanner.nextLine());
    System.out.print("Cargo: ");
    func.setCargo(scanner.nextLine());
    System.out.print("Login: ");
    func.setLogin(scanner.nextLine());
    System.out.print("Senha: ");
    func.setSenha(scanner.nextLine());

    funcionarioService.cadastrar(func);
    System.out.println("Funcionário cadastrado com sucesso!");
}

private static void buscarFuncionario() {
    System.out.print("\nDigite o ID do funcionário: ");
    Long id = Long.parseLong(scanner.nextLine());

    Funcionario func = funcionarioService.buscarPorId(id);
    if (func != null) {
        System.out.println(func); // O seu toString() fará o trabalho aqui
    } else {
        System.out.println("Funcionário não encontrado.");
    }
}

private static void atualizarFuncionario() {
    System.out.print("\nID do funcionário a ser atualizado: ");
    Long id = Long.parseLong(scanner.nextLine());

    Funcionario funcionario = funcionarioService.buscarPorId(id);

    if (funcionario == null) {
        System.out.println("Funcionário não encontrado.");
        return;
    }

    System.out.print("Novo nome (" + funcionario.getNome() + "): ");
    funcionario.setNome(scanner.nextLine());
    System.out.print("Novo cargo (" + funcionario.getCargo() + "): ");
    funcionario.setCargo(scanner.nextLine());
    System.out.print("Novo login (" + funcionario.getLogin() + "): ");
    funcionario.setLogin(scanner.nextLine());
    System.out.print("Nova senha: ");
    funcionario.setSenha(scanner.nextLine());

    funcionarioService.atualizar(funcionario);
    System.out.println("Funcionário atualizado com sucesso!");
}

private static void listarFuncionarios() {
    System.out.println("\n-- Lista de Funcionários --");
    List<Funcionario> funcionarios = funcionarioService.listarTodos();

    if (funcionarios.isEmpty()) {
        System.out.println("Nenhum funcionário cadastrado no sistema.");
    } else {
        for (Funcionario f : funcionarios) {

            System.out.println(f.toString());
        }
    }
}

private static void deletarFuncionario() {
    System.out.print("\nID do funcionário a ser deletado: ");
    Long id = Long.parseLong(scanner.nextLine());


    Funcionario funcionario = funcionarioService.buscarPorId(id);

    if (funcionario == null) {
        System.out.println("Funcionário não encontrado no banco de dados.");
        return;
    }

    System.out.print("Tem certeza que deseja demitir/deletar " + funcionario.getNome() + "? (S/N): ");
    String confirmacao = scanner.nextLine();

    if (confirmacao.equalsIgnoreCase("S")) {
        funcionarioService.deletar(id);
        System.out.println("Funcionário deletado com sucesso.");
    } else {
        System.out.println("Operação cancelada.");
    }
}

// ==========================================
//               MENU DE MESAS
// ==========================================
private static void menuMesas() {
    int opcao = -1;
    while (opcao != 0) {
        System.out.println("\n--- MENU DE MESAS ---");
        System.out.println("1 - Cadastrar nova mesa");
        System.out.println("2 - Listar TODAS as mesas");
        System.out.println("3 - Listar mesas DISPONÍVEIS");
        System.out.println("4 - Alterar status de uma mesa");
        System.out.println("0 - Voltar ao Menu Principal");
        System.out.print("Escolha: ");

        try {
            opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1 -> cadastrarMesa();
                case 2 -> listarTodasMesas();
                case 3 -> listarMesasDisponiveis();
                case 4 -> alterarStatusMesa();
                case 0 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida!");
            }
        } catch (NumberFormatException e) {
            System.out.println("Erro: Digite apenas números.");
        } catch (IllegalArgumentException e) {
            // Captura as validações de regra de negócio do MesaService
            System.out.println(e.getMessage());
        }
    }
}

private static void cadastrarMesa() {
    System.out.println("\n-- Cadastrar Mesa --");
    Mesa mesa = new Mesa();

    System.out.print("Número da Mesa: ");
    mesa.setNumero(Integer.parseInt(scanner.nextLine()));

    System.out.print("Capacidade de Pessoas: ");
    mesa.setCapacidade(Integer.parseInt(scanner.nextLine()));

    mesaService.cadastrarMesa(mesa);
    System.out.println("Mesa cadastrada com sucesso!");
}

private static void listarTodasMesas() {
    System.out.println("\n-- Salão Completo --");
    List<Mesa> mesas = mesaService.listarTodas();

    if (mesas.isEmpty()) {
        System.out.println("Nenhuma mesa cadastrada no sistema.");
    } else {
        for (Mesa m : mesas) {
            System.out.println("Mesa " + m.getNumero() + " | Lugares: " + m.getCapacidade() + " | Status: " + m.getStatus());
        }
    }
}

private static void listarMesasDisponiveis() {
    System.out.println("\n-- Mesas Livres --");
    List<Mesa> mesas = mesaService.listarMesasLivres();

    if (mesas.isEmpty()) {
        System.out.println("Não há mesas disponíveis no momento.");
    } else {
        for (Mesa m : mesas) {
            System.out.println("Mesa " + m.getNumero() + " | Lugares: " + m.getCapacidade());
        }
    }
}

private static void alterarStatusMesa() {
    System.out.print("\nID da Mesa no banco de dados: ");
    Long id = Long.parseLong(scanner.nextLine());

    System.out.println("Status aceitos: DISPONIVEL, OCUPADA, RESERVADA, MANUTENCAO");
    System.out.print("Digite o novo status: ");
    String novoStatus = scanner.nextLine();

    mesaService.atualizarStatus(id, novoStatus);
}











