package service;

import DAO.ItemPedidoDAO;
import DAO.PedidoDAO;
import model.ItemPedido;
import model.Pedido;

import java.util.List;

public class PedidoService {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ItemPedidoDAO itemPedidoDAO = new ItemPedidoDAO();

    public void salvar(Pedido pedido) {
        pedidoDAO.salvar(pedido);
    }

    public Pedido buscarPorId(Long id) {
        Pedido pedido = pedidoDAO.buscarPorId(id);
        if (pedido != null) {
            List<ItemPedido> itens = itemPedidoDAO.buscarPorPedido(id);
            pedido.setItens(itens);
        }
        return pedido;
    }

    public List<Pedido> listarTodos() {
        return pedidoDAO.listarTodos();
    }

    public void atualizar(Pedido pedido) {
        pedidoDAO.atualizar(pedido);
    }

    public void deletar(Long id) {
        pedidoDAO.deletar(id);
    }
}


