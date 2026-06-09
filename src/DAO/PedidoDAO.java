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

        if (pedido == null || pedido.getMesa() == null || pedido.getFuncionario() == null) {
            throw new RuntimeException("Erro: O pedido, a mesa ou o funcionário não estão instanciados no Java!");
        }

        String sql = """
                INSERT INTO pedidos
                (id_mesa, id_funcionario)
                VALUES (?, ?)
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setLong(1, pedido.getMesa().getId());
            stmt.setLong(2, pedido.getFuncionario().getId());


            stmt.executeUpdate();


            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pedido.setId(generatedKeys.getLong(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao salvar pedido no banco: " + e.getMessage(), e);
        }
    }

    public Pedido buscarPorId(Long id) {
        String sql = """
                SELECT *
                FROM pedidos
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
                pedido.setId(rs.getLong("id"));

                Mesa m = new Mesa();
                m.setId(rs.getLong("mesa_id"));
                pedido.setMesa(m);

                Funcionario f = new Funcionario();
                f.setId(rs.getLong("funcionario_id"));
                pedido.setFuncionario(f);


                return pedido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao buscar pedido por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public List<Pedido> listarTodos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM pedidos";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {

                Pedido pedido = new Pedido();
                pedido.setId(rs.getLong("id"));

                Mesa m = new Mesa();
                m.setId(rs.getLong("id_mesa"));
                pedido.setMesa(m);

                Funcionario f = new Funcionario();
                f.setId(rs.getLong("id_funcionario"));
                pedido.setFuncionario(f);

                if (rs.getTimestamp("data_hora") != null) {
                    pedido.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                }
                pedido.setStatus(rs.getString("status"));
                pedidos.add(pedido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao listar pedidos: " + e.getMessage(), e);
        }
        return pedidos;
    }

    public void atualizar(Pedido pedido) {
        String sql = """
                UPDATE pedidos
                SET status = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, pedido.getStatus());
            stmt.setLong(2, pedido.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao atualizar pedido: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        String sql = """
                DELETE FROM pedidos
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao deletar pedido: " + e.getMessage(), e);
        }
    }
}