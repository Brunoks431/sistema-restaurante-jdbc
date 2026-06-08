package service;

import model.Funcionario;
import DAO.FuncionarioDAO;

import java.util.List;

public class FuncionarioService {

    private final FuncionarioDAO funcionarioDAO;

    public FuncionarioService() {
        this.funcionarioDAO = new FuncionarioDAO();
    }

    public void cadastrar(Funcionario funcionario) {

        if(funcionario.getNome() == null ||
                funcionario.getNome().isBlank()) {

            throw new IllegalArgumentException(
                    "Nome obrigatório."
            );
        }

        funcionarioDAO.salvar(funcionario);
    }
    public void deletar(Long id) {
        funcionarioDAO.deletar(id);
    }

    public void atualizar(Funcionario funcionario) {
        funcionarioDAO.atualizar(funcionario);
    }

    public void remover(Long id) {
        funcionarioDAO.deletar(id);
    }

    public Funcionario buscarPorId(Long id) {
        return funcionarioDAO.buscarPorId(id);
    }

    public List<Funcionario> listarTodos() {
        return funcionarioDAO.listarTodos();
    }
}


