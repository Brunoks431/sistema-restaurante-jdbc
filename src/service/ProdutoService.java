package service;

import DAO.ProdutoDAO;
import model.Produto;

import java.util.List;

public class ProdutoService {

    private final ProdutoDAO produtoDAO = new ProdutoDAO();

    public void cadastrar(Produto produto) {
        if (produto.getNome() == null || produto.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser vazio.");
        }
        if (produto.getPreco() == null || produto.getPreco().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("O preço deve ser maior que zero.");
        }
        produtoDAO.salvar(produto);
    }

    public Produto buscarPorId(Long id) {
        return produtoDAO.buscarPorId(id);
    }

    public List<Produto> listarTodos() {
        return produtoDAO.listarTodos();
    }

    public void atualizar(Produto produto) {
        produtoDAO.atualizar(produto);
    }

    public void deletar(Long id) {
        produtoDAO.deletar(id);
    }
}
