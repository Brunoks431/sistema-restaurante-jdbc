package DAO;

import connections.ConnectionFactory;
import model.Funcionario;
import model.Mesa;
import model.Pedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void salvar(Pedido pedido) {

        String sql = """
                INSERT INTO pedido
                (mesa_id, funcionario_id, data_hora, status)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, pedido.getMesa().getId());
            stmt.setLong(2, pedido.getFuncionario().getId());
            stmt.setTimestamp(
                    3,
                    Timestamp.valueOf(pedido.getDataHora())
            );
            stmt.setString(4, pedido.getStatus());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao salvar pedido",
                    e
            );
        }
    }

    public Pedido buscarPorId(Long id) {

        String sql = """
                SELECT *
                FROM pedido
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Pedido pedido = new Pedido();

                Mesa mesa = new Mesa();
                mesa.setId(rs.getLong("mesa_id"));

                Funcionario funcionario =
                        new Funcionario();

                funcionario.setId(
                        rs.getLong("funcionario_id")
                );

                pedido.setId(rs.getLong("id"));
                pedido.setMesa(mesa);
                pedido.setFuncionario(funcionario);
                pedido.setDataHora(
                        rs.getTimestamp("data_hora")
                                .toLocalDateTime()
                );
                pedido.setStatus(
                        rs.getString("status")
                );

                return pedido;
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao buscar pedido",
                    e
            );
        }

        return null;
    }

    public List<Pedido> listarTodos() {

        List<Pedido> pedidos =
                new ArrayList<>();

        String sql = """
                SELECT *
                FROM pedido
                ORDER BY data_hora DESC
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql);

                ResultSet rs =
                        stmt.executeQuery()
        ) {

            while (rs.next()) {

                Pedido pedido = new Pedido();

                Mesa mesa = new Mesa();
                mesa.setId(rs.getLong("mesa_id"));

                Funcionario funcionario =
                        new Funcionario();

                funcionario.setId(
                        rs.getLong("funcionario_id")
                );

                pedido.setId(rs.getLong("id"));
                pedido.setMesa(mesa);
                pedido.setFuncionario(funcionario);
                pedido.setDataHora(
                        rs.getTimestamp("data_hora")
                                .toLocalDateTime()
                );
                pedido.setStatus(
                        rs.getString("status")
                );

                pedidos.add(pedido);
            }

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao listar pedidos",
                    e
            );
        }

        return pedidos;
    }

    public void atualizar(Pedido pedido) {

        String sql = """
                UPDATE pedido
                SET status = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt =
                        conn.prepareStatement(sql)
        ) {

            stmt.setString(
                    1,
                    pedido.getStatus()
            );

            stmt.setLong(
                    2,
                    pedido.getId()
            );

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(
                    "Erro ao atualizar pedido",
                    e
            );
        }
    }

    public void deletar(Long id) {

        String sql = """
                DELETE FROM pedido
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
                    "Erro ao deletar pedido",
                    e
            );
        }
    }
}
