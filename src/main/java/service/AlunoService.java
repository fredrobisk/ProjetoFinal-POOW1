package service;

import dao.AlunoDAO;
import model.Aluno;

import java.time.LocalDate;
import java.util.List;

public class AlunoService {

    private final AlunoDAO alunoDAO = new AlunoDAO();

    public List<Aluno> listar() {
        return alunoDAO.listar();
    }

    public Aluno buscarPorId(int id) {
        return alunoDAO.buscarPorId(id);
    }

    public void salvar(String idParam, String nome, String cpf, String dataNascimentoParam,
                       String endereco, String telefone, String idPlanoParam) {
        validar(nome, cpf, idPlanoParam);

        Aluno aluno = new Aluno();
        aluno.setNome(nome.trim());
        aluno.setCpf(cpf.trim());
        aluno.setEndereco(endereco == null ? "" : endereco.trim());
        aluno.setTelefone(telefone == null ? "" : telefone.trim());
        aluno.setIdPlano(Integer.parseInt(idPlanoParam));

        if (dataNascimentoParam != null && !dataNascimentoParam.isBlank()) {
            aluno.setDataNascimento(LocalDate.parse(dataNascimentoParam));
        }

        if (idParam != null && !idParam.isBlank()) {
            aluno.setId(Integer.parseInt(idParam));
            alunoDAO.atualizar(aluno);
        } else {
            alunoDAO.inserir(aluno);
        }
    }

    public void excluir(int id) {
        alunoDAO.excluir(id);
    }

    private void validar(String nome, String cpf, String idPlanoParam) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do aluno é obrigatório.");
        }

        if (cpf == null || cpf.isBlank()) {
            throw new IllegalArgumentException("O CPF do aluno é obrigatório.");
        }

        if (idPlanoParam == null || idPlanoParam.isBlank()) {
            throw new IllegalArgumentException("Selecione um plano para o aluno.");
        }
    }
}