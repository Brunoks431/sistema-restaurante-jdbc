package DAO;

import connections.ConnectionFactory;
import model.ItemPedido;
import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemPedidoDAO {

    public void salvar(
            ItemPedido item,
            Long pedidoId
    ) {

        String sql = """
                INSERT INTO item_pedido
                (pedido_id, produto_id, quantidade)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, pedidoId);

            stmt.setLong(
                    2,
                    item.getProduto().getId()
            );

            stmt.setInt(
                    3,
                    item.getQuantidade()
            );

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao salvar item",
                    e
            );
        }
    }

    public List<ItemPedido> buscarPorPedido(
            Long pedidoId
    ) {

        List<ItemPedido> itens =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM item_pedido
                WHERE pedido_id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, pedidoId);

            ResultSet rs =
                    stmt.executeQuery();

            while (rs.next()) {

                Produto produto =
                        new Produto();

                produto.setId(
                        rs.getLong("produto_id")
                );

                ItemPedido item =
                        new ItemPedido();

                item.setId(
                        rs.getLong("id")
                );

                item.setProduto(produto);

                item.setQuantidade(
                        rs.getInt("quantidade")
                );

                itens.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar itens",
                    e
            );
        }

        return itens;
    }

    public void deletar(Long id) {

        String sql = """
                DELETE FROM item_pedido
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao remover item",
                    e
            );
        }
    }
}
