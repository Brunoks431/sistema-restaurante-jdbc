package DAO;

import connections.ConnectionFactory;
import model.Funcionario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void salvar(Funcionario funcionario) {

        String sql = """
                INSERT INTO funcionarios
                (nome, cargo, login, senha)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getLogin());
            stmt.setString(4, funcionario.getSenha());


            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao salvar funcionário", e);
        }
    }

    public Funcionario buscarPorId(Long id) {

        String sql = """
                SELECT *
                FROM funcionarios
                WHERE id_funcionario = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                Funcionario funcionario = new Funcionario();

                funcionario.setId(id);


                funcionario.setNome(rs.getString("nome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setLogin(rs.getString("login"));
                funcionario.setSenha(rs.getString("senha"));


                return funcionario;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar funcionário", e);
        }

        return null;
    }

    public List<Funcionario> listarTodos() {

        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = """
                SELECT *
                FROM funcionarios
                ORDER BY nome
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {

            while (rs.next()) {

                Funcionario funcionario = new Funcionario();


                funcionario.setNome(rs.getString("nome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setLogin(rs.getString("login"));
                funcionario.setSenha(rs.getString("senha"));


                funcionarios.add(funcionario);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar funcionários", e);
        }

        return funcionarios;
    }


    public void atualizar(Funcionario funcionario) {

        String sql = """
                UPDATE funcionarios
                SET nome = ?,
                    cargo = ?,
                    login = ?,
                    senha = ?               
                WHERE id_funcionario = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getCargo());
            stmt.setString(3, funcionario.getLogin());
            stmt.setString(4, funcionario.getSenha());

            stmt.setLong(5, funcionario.getId());



            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar funcionário", e);
        }
    }


    public void deletar(Long id) {

        String sql = """
                DELETE FROM funcionarios
                WHERE id_funcionario = ?
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {

            stmt.setLong(1, id);

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao remover funcionário", e);
        }
    }






}
