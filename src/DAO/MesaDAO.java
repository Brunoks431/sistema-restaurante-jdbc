package DAO;
import connections.ConnectionFactory;
import model.Funcionario;
import model.Mesa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MesaDAO {

    public void salvar(Mesa mesa) {

        String sql = """
                INSERT INTO mesa
                (numero, capacidade, status)
                VALUES (?, ?, ?)
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, mesa.getNumero());
            stmt.setInt(2, mesa.getCapacidade());
            stmt.setString(3, "DISPONIVEL");

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar mesa", e);
        }
    }

    public Mesa buscarPorId(Long id) {

        String sql = """
                SELECT *
                FROM mesa
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Mesa mesa = new Mesa();

                mesa.setId(rs.getLong("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setCapacidade(rs.getInt("capacidade"));
                mesa.setStatus(rs.getString("status"));

                return mesa;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar mesa", e);
        }

        return null;
    }

    public List<Mesa> listarTodas() {

        List<Mesa> mesas = new ArrayList<>();

        String sql = """
                SELECT *
                FROM mesa
                ORDER BY numero
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Mesa mesa = new Mesa();

                mesa.setId(rs.getLong("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setCapacidade(rs.getInt("capacidade"));
                mesa.setStatus(rs.getString("status"));

                mesas.add(mesa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar mesas", e);
        }

        return mesas;
    }

    public List<Mesa> listarMesasLivres() {

        List<Mesa> mesas = new ArrayList<>();

        String sql = """
                SELECT *
                FROM mesa
                WHERE status = 'LIVRE'
                ORDER BY numero
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Mesa mesa = new Mesa();

                mesa.setId(rs.getLong("id"));
                mesa.setNumero(rs.getInt("numero"));
                mesa.setCapacidade(rs.getInt("capacidade"));
                mesa.setStatus(rs.getString("status"));

                mesas.add(mesa);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar mesas livres", e);
        }

        return mesas;

    }

    public void atualizar(Mesa mesa) {

        String sql = """
                UPDATE mesa
                SET numero = ?,
                    capacidade = ?,
                    status = ?
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setInt(1, mesa.getNumero());
            stmt.setInt(2, mesa.getCapacidade());
            stmt.setString(3, mesa.getStatus());
            stmt.setLong(4, mesa.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar mesa", e);
        }
    }

    public void atualizarStatus(Long id, String novoStatus) {
        String sql = """
                UPDATE mesas
                SET status = ?
                WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setLong(2, id);

            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar o status da mesa.", e);
        }
    }

    public void deletar(Long id) {

        String sql = """
                DELETE FROM mesa
                WHERE id = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover mesa", e);
        }
    }
}
