package service;

import DAO.ItemPedidoDAO;
import model.ItemPedido;

import java.util.List;

public class ItemPedidoService {

    private final ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();

    public void salvar(ItemPedido item, Long pedidoId) {
        if (item.getQuantidade() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior do que zero.");
        }
        itemPedidoDAO.salvar(item, pedidoId);
    }

    public List<ItemPedido> buscarPorPedido(Long pedidoId) {
        return itemPedidoDAO.buscarPorPedido(pedidoId);
    }

    public void deletar(Long id) {
        itemPedidoDAO.deletar(id);
    }
}
