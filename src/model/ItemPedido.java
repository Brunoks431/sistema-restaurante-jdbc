package model;

import java.math.BigDecimal;

public class ItemPedido {

    private Long id;
    private Produto produto;
    private Integer quantidade;

    public ItemPedido() {
    }

    public ItemPedido(Long id, Produto produto, Integer quantidade) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public BigDecimal calcularSubtotal() {
        return produto.getPreco()
                .multiply(BigDecimal.valueOf(quantidade));
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}
