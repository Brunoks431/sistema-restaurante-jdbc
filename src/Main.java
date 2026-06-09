
import model.Funcionario;
import model.Mesa;
import model.Produto;
import model.Pedido;
import model.ItemPedido;
import service.FuncionarioService;
import service.MesaService;
import service.ProdutoService;
import service.PedidoService;
import service.ItemPedidoService;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.List;





private static final Scanner scanner = new Scanner(System.in);
private static final FuncionarioService funcionarioService = new FuncionarioService();
private static final MesaService mesaService = new MesaService();
private static final ProdutoService produtoService = new ProdutoService();
private static final PedidoService pedidoService = new PedidoService();
private static final ItemPedidoService itemPedidoService = new ItemPedidoService();


void main() {
            int opcao = -1;

            while (opcao != 0) {
                System.out.println("\n==================================");
                System.out.println("      SISTEMA DE RESTAURANTE      ");
                System.out.println("==================================");
                System.out.println("1 - Gerenciar Funcionários");
                System.out.println("2 - Gerenciar Mesas");
                System.out.println("3 - Gerenciar Produtos (Cardápio)");
                System.out.println("4 - Gerenciar Pedidos (Vendas/Contas)");
                System.out.println("0 - Sair do Sistema");
                System.out.print("Escolha uma opção: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());

                    switch (opcao) {
                        case 1 -> menuFuncionarios();
                        case 2 -> menuMesas();
                        case 3 -> menuProdutos();
                        case 4 -> menuPedidos();
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
                System.out.println(func);
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
        //                 MENU DE MESAS
        // ==========================================
        private static void menuMesas() {
            int opcao = -1;
            while (opcao != 0) {
                System.out.println("\n--- MENU DE MESAS ---");
                System.out.println("1 - Cadastrar nova mesa");
                System.out.println("2 - Listar TODAS as mesas");
                System.out.println("3 - Listar mesas DISPONÍVEIS");
                System.out.println("4 - Alterar status de uma mesa");
                System.out.print("0 - Voltar ao Menu Principal\nEscolha: ");

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
                    System.out.println("ID: " + m.getId() + " | Mesa " + m.getNumero() + " | Lugares: " + m.getCapacidade() + " | Status: " + m.getStatus());
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
                    System.out.println("ID: " + m.getId() + " | Mesa " + m.getNumero() + " | Lugares: " + m.getCapacidade());
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
            System.out.println("Status da mesa modificado para: " + novoStatus);
        }

        // ==========================================
        //               MENU DE PRODUTOS
        // ==========================================
        private static void menuProdutos() {
            int opcao = -1;
            while (opcao != 0) {
                System.out.println("\n--- MENU DE PRODUTOS (CARDÁPIO) ---");
                System.out.println("1 - Cadastrar Produto");
                System.out.println("2 - Listar Cardápio");
                System.out.println("3 - Atualizar Produto");
                System.out.println("4 - Deletar Produto");
                System.out.print("0 - Voltar ao Menu Principal\nEscolha: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());
                    switch (opcao) {
                        case 1 -> cadastrarProduto();
                        case 2 -> listarProdutos();
                        case 3 -> atualizarProduto();
                        case 4 -> deletarProduto();
                        case 0 -> System.out.println("Voltando...");
                        default -> System.out.println("Opção inválida!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Erro: Digite apenas números.");
                }
            }
        }

        private static void cadastrarProduto() {
            System.out.println("\n-- Cadastrar Novo Produto --");
            Produto prod = new Produto();

            System.out.print("Nome do Produto: ");
            prod.setNome(scanner.nextLine());
            System.out.print("Categoria (Bebidas, Lanches, Sobremesas): ");
            prod.setCategoria(scanner.nextLine());
            System.out.print("Preço (ex: 24.50): ");
            prod.setPreco(new BigDecimal(scanner.nextLine()));

            produtoService.cadastrar(prod);
            System.out.println("Produto incluído no cardápio com sucesso!");
        }

        private static void listarProdutos() {
            System.out.println("\n--- CARDÁPIO DO RESTAURANTE ---");
            List<Produto> lista = produtoService.listarTodos();
            if (lista.isEmpty()) {
                System.out.println("Nenhum produto cadastrado no cardápio.");
            } else {
                for (Produto p : lista) {
                    System.out.println("ID: " + p.getId() + " | " + p.getCategoria() + " -> " + p.getNome() + " | Preço: R$ " + p.getPreco());
                }
            }
        }

        private static void atualizarProduto() {
            System.out.print("\nDigite o ID do produto para atualizar: ");
            Long id = Long.parseLong(scanner.nextLine());
            Produto p = produtoService.buscarPorId(id);

            if (p == null) {
                System.out.println("Produto não encontrado.");
                return;
            }

            System.out.print("Novo Nome (" + p.getNome() + "): ");
            p.setNome(scanner.nextLine());
            System.out.print("Nova Categoria (" + p.getCategoria() + "): ");
            p.setCategoria(scanner.nextLine());
            System.out.print("Novo Preço (" + p.getPreco() + "): ");
            p.setPreco(new BigDecimal(scanner.nextLine()));

            produtoService.atualizar(p);
            System.out.println("Produto atualizado com sucesso!");
        }

        private static void deletarProduto() {
            System.out.print("\nDigite o ID do produto para remover: ");
            Long id = Long.parseLong(scanner.nextLine());
            produtoService.deletar(id);
            System.out.println("Produto removido.");
        }

        // ==========================================
        //               MENU DE PEDIDOS
        // ==========================================
        private static void menuPedidos() {
            int opcao = -1;
            while (opcao != 0) {
                System.out.println("\n--- GESTÃO DE PEDIDOS (CONTA DE MESAS) ---");
                System.out.println("1 - Abrir Novo Pedido (Mesa Ocupada)");
                System.out.println("2 - Adicionar Itens de Consumo");
                System.out.println("3 - Exibir Extrato Parcial e Valor Total");
                System.out.println("4 - Concluir/Mudar Status do Pedido");
                System.out.print("0 - Voltar ao Menu Principal\nEscolha: ");

                try {
                    opcao = Integer.parseInt(scanner.nextLine());
                    switch (opcao) {
                        case 1 -> abrirPedido();
                        case 2 -> lancarItens();
                        case 3 -> visualizarExtrato();
                        case 4 -> encerrarPedido();
                        case 0 -> System.out.println("Voltando...");
                        default -> System.out.println("Opção inválida!");
                    }
                } catch (Exception e) {
                    System.out.println("Erro no fluxo do pedido: " + e.getMessage());
                }
            }
        }

        private static void abrirPedido() {
            System.out.println("\n-- Abrir Novo Pedido --");
            System.out.print("ID da Mesa: ");
            Long mesaId = Long.parseLong(scanner.nextLine());
            Mesa mesa = mesaService.buscarPorId(mesaId);

            System.out.print("ID do Atendente/Funcionário: ");
            Long funcId = Long.parseLong(scanner.nextLine());
            Funcionario func = funcionarioService.buscarPorId(funcId);

            if (mesa == null || func == null) {
                System.out.println("Erro: Verifique se a mesa ou funcionário de fato existem.");
                return;
            }

            // Criando a instância utilizando o seu construtor parametrizado de Pedido.java
            Pedido pedido = new Pedido(null, mesa, func, "ABERTO");
            pedidoService.salvar(pedido);

            // Modifica automaticamente o status da mesa para ocupada
            mesaService.atualizarStatus(mesaId, "OCUPADA");
            System.out.println("Pedido de atendimento iniciado com sucesso para a Mesa " + mesa.getNumero());
        }

        private static void lancarItens() {
            System.out.print("\nDigite o ID do Pedido ativo: ");
            Long pedidoId = Long.parseLong(scanner.nextLine());
            Pedido pedido = pedidoService.buscarPorId(pedidoId);

            if (pedido == null) {
                System.out.println("Pedido de conta não localizado.");
                return;
            }

            System.out.print("Digite o ID do Produto do cardápio: ");
            Long prodId = Long.parseLong(scanner.nextLine());
            Produto produto = produtoService.buscarPorId(prodId);

            if (produto == null) {
                System.out.println("Produto não encontrado.");
                return;
            }

            System.out.print("Quantidade consumida: ");
            Integer qtd = Integer.parseInt(scanner.nextLine());

            // Constrói o ItemPedido utilizando sua classe
            ItemPedido item = new ItemPedido(null, produto, qtd);

            // Salva na tabela associativa do banco passando o ID do pedido pai
            itemPedidoService.salvar(item, pedidoId);
            System.out.println(qtd + "x '" + produto.getNome() + "' adicionado(s) à conta.");
        }

        private static void visualizarExtrato() {
            System.out.print("\nDigite o ID do Pedido para emitir extrato: ");
            Long pedidoId = Long.parseLong(scanner.nextLine());

            // O PedidoService já trará os itens agregados internamente do ItemPedidoDAO
            Pedido pedido = pedidoService.buscarPorId(pedidoId);

            if (pedido == null) {
                System.out.println("Pedido não localizado.");
                return;
            }

            System.out.println("\n=================================================");
            System.out.println("            EXTRATO DE CONSUMO ATUAL             ");
            System.out.println("=================================================");
            System.out.println("Pedido ID:   " + pedido.getId());
            System.out.println("Status:      " + pedido.getStatus());
            System.out.println("Mesa Número: " + pedido.getMesa().getNumero());
            System.out.println("Atendente:   " + pedido.getFuncionario().getNome());
            System.out.println("Data/Hora:   " + pedido.getDataHora());
            System.out.println("-------------------------------------------------");
            System.out.println("ITENS LANÇADOS:");

            if (pedido.getItens().isEmpty()) {
                System.out.println("  [Nenhum item consumido até o momento]");
            } else {
                for (ItemPedido item : pedido.getItens()) {
                    // Como o ItemPedidoDAO traz apenas o ID do produto, buscamos os dados dele
                    Produto realProd = produtoService.buscarPorId(item.getProduto().getId());
                    item.setProduto(realProd);

                    System.out.printf("- %dx %-20s | Unitário: R$ %-6s | Subtotal: R$ %s%n",
                            item.getQuantidade(),
                            item.getProduto().getNome(),
                            item.getProduto().getPreco(),
                            item.calcularSubtotal()); // Método nativo de sua classe ItemPedido
                }
            }
            System.out.println("-------------------------------------------------");
            // Processa a sua regra de somatório nativa de Pedido.java
            System.out.println("TOTAL ACUMULADO DA CONTA: R$ " + pedido.calcularTotal());
            System.out.println("=================================================");
        }

        private static void encerrarPedido() {
            System.out.print("\nID do Pedido a atualizar: ");
            Long pedidoId = Long.parseLong(scanner.nextLine());
            Pedido pedido = pedidoService.buscarPorId(pedidoId);

            if (pedido == null) {
                System.out.println("Pedido inválido.");
                return;
            }

            System.out.print("Defina o novo status (Ex: FINALIZADO, PREPARANDO): ");
            String status = scanner.nextLine().toUpperCase();
            pedido.setStatus(status);
            pedidoService.atualizar(pedido);

            if (status.equalsIgnoreCase("FINALIZADO")) {
                // Libera a mesa de volta para livre no salão
                mesaService.atualizarStatus(pedido.getMesa().getId(), "DISPONIVEL");
                System.out.println("Mesa encerrada, conta paga e liberada para novos clientes!");
            } else {
                System.out.println("Status do pedido modificado para: " + status);
            }
        }




















