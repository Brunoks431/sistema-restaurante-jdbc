package service;

import DAO.MesaDAO;
import model.Mesa;

import java.util.List;

public class MesaService {


    private MesaDAO mesaDAO;

    public MesaService() {
        this.mesaDAO = new MesaDAO();
    }


    public void cadastrarMesa(Mesa mesa) {
        if (mesa.getNumero() <= 0) {
            throw new IllegalArgumentException("Erro: O número da mesa deve ser maior que zero.");
        }
        if (mesa.getCapacidade() <= 0) {
            throw new IllegalArgumentException("Erro: A capacidade deve ser de pelo menos 1 pessoa.");
        }

        mesaDAO.salvar(mesa);
    }


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


    public List<Mesa> listarTodas() {
        return mesaDAO.listarTodas();
    }


    public List<Mesa> listarMesasLivres() {
        return mesaDAO.listarMesasLivres();
    }


    public void atualizarStatus(Long id, String novoStatus) {
        novoStatus = novoStatus.toUpperCase();


        if (!novoStatus.equals("DISPONIVEL") &&
                !novoStatus.equals("OCUPADA") &&
                !novoStatus.equals("RESERVADA") &&
                !novoStatus.equals("MANUTENCAO")) {
            throw new IllegalArgumentException("Erro: Status inválido. Use DISPONIVEL, OCUPADA, RESERVADA ou MANUTENCAO.");
        }


        Mesa mesa = buscarPorId(id);

        mesaDAO.atualizarStatus(mesa.getId(), novoStatus);
        System.out.println("Status da Mesa " + mesa.getNumero() + " atualizado para " + novoStatus + ".");
    }
}
