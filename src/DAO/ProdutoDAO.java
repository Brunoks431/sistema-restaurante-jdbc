package DAO;

import connections.ConnectionFactory;
import model.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void salvar(Produto produto) {

        String sql = """
                INSERT INTO produto
                (nome, categoria, preco)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(1, produto.getNome());
            stmt.setString(2, produto.getCategoria());
            stmt.setBigDecimal(3, produto.getPreco());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao salvar produto",
                    e
            );
        }
    }

    public Produto buscarPorId(Long id) {

        String sql = """
                SELECT *
                FROM produto
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            ResultSet rs =
                    stmt.executeQuery();

            if (rs.next()) {

                Produto produto =
                        new Produto();

                produto.setId(
                        rs.getLong("id")
                );

                produto.setNome(
                        rs.getString("nome")
                );

                produto.setCategoria(
                        rs.getString("categoria")
                );

                produto.setPreco(
                        rs.getBigDecimal("preco")
                );

                return produto;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar produto",
                    e
            );
        }

        return null;
    }

    public List<Produto> listarTodos() {

        List<Produto> produtos =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM produto
                ORDER BY nome
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Produto produto =
                        new Produto();

                produto.setId(
                        rs.getLong("id")
                );

                produto.setNome(
                        rs.getString("nome")
                );

                produto.setCategoria(
                        rs.getString("categoria")
                );

                produto.setPreco(
                        rs.getBigDecimal("preco")
                );

                produtos.add(produto);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar produtos",
                    e
            );
        }

        return produtos;
    }

    public void atualizar(Produto produto) {

        String sql = """
                UPDATE produto
                SET nome = ?,
                    categoria = ?,
                    preco = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    produto.getNome()
            );

            stmt.setString(
                    2,
                    produto.getCategoria()
            );

            stmt.setBigDecimal(
                    3,
                    produto.getPreco()
            );

            stmt.setLong(
                    4,
                    produto.getId()
            );

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar produto",
                    e
            );
        }
    }

    public void deletar(Long id) {

        String sql = """
                DELETE FROM produto
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
                    "Erro ao deletar produto",
                    e
            );
        }
    }
}
