package service;

import DAO.MesaDAO;
import model.Mesa;

import java.util.List;

public class MesaService {

    // O Service precisa do DAO para executar as operações de banco
    private MesaDAO mesaDAO;

    public MesaService() {
        this.mesaDAO = new MesaDAO();
    }

    /**
     * Regra de Negócio: Não pode existir mesa com número negativo ou sem cadeiras.
     */
    public void cadastrarMesa(Mesa mesa) {
        if (mesa.getNumero() <= 0) {
            throw new IllegalArgumentException("Erro: O número da mesa deve ser maior que zero.");
        }
        if (mesa.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Erro: A capacidade deve ser de pelo menos 1 pessoa.");
        }

        mesaDAO.salvar(mesa);
    }

    /**
     * Busca uma mesa e garante que ela existe antes de prosseguir.
     */
    public Mesa buscarPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Erro: ID inválido.");
        }

        Mesa mesa = mesaDAO.buscarPorId(id);
        if (mesa == null) {
            throw new RuntimeException("Erro: Mesa não encontrada para o ID " + id);
        }

        return mesa;
    }

    /**
     * Repassa a lista de todas as mesas para o Main.
     */
    public List<Mesa> listarTodas() {
        return mesaDAO.listarTodas();
    }

    /**
     * Repassa a lista apenas das mesas com status "DISPONIVEL".
     */
    public List<Mesa> listarMesasLivres() {
        return mesaDAO.listarMesasLivres();
    }


    public void atualizarStatus(Long id, String novoStatus) {
        novoStatus = novoStatus.toUpperCase();

        // Validação rigorosa: impede que digitem um status que não existe na operação
        if (!novoStatus.equals("DISPONIVEL") &&
                !novoStatus.equals("OCUPADA") &&
                !novoStatus.equals("RESERVADA") &&
                !novoStatus.equals("MANUTENCAO")) {
            throw new IllegalArgumentException("Erro: Status inválido. Use DISPONIVEL, OCUPADA, RESERVADA ou MANUTENCAO.");
        }

        // Verifica se a mesa existe antes de tentar alterar o status
        Mesa mesa = buscarPorId(id);

        mesaDAO.atualizarStatus(mesa.getId(), novoStatus);
        System.out.println("Status da Mesa " + mesa.getNumero() + " atualizado para " + novoStatus + ".");
    }
}
