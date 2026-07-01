package service;

import dao.TreinoDAO;
import model.Treino;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TreinoService {

    private final TreinoDAO treinoDAO;

    public TreinoService(TreinoDAO treinoDAO) {
        this.treinoDAO = treinoDAO;
    }

    public List<Treino> listar() {
        return treinoDAO.listar();
    }

    public Treino buscarPorId(int id) {
        return treinoDAO.buscarPorId(id);
    }

    public void salvar(String idParam, String idAlunoParam, String nome, String descricao,
                       String diaSemana, String ativoParam) {
        validar(idAlunoParam, nome, descricao);

        Treino treino = new Treino();
        treino.setIdAluno(Integer.parseInt(idAlunoParam));
        treino.setNome(nome.trim());
        treino.setDescricao(descricao.trim());
        treino.setDiaSemana(diaSemana == null ? "" : diaSemana.trim());
        treino.setAtivo("on".equals(ativoParam) || "true".equals(ativoParam));

        if (idParam != null && !idParam.isBlank()) {
            treino.setId(Integer.parseInt(idParam));
            treinoDAO.atualizar(treino);
        } else {
            treinoDAO.inserir(treino);
        }
    }

    public void excluir(int id) {
        treinoDAO.excluir(id);
    }

    private void validar(String idAlunoParam, String nome, String descricao) {
        if (idAlunoParam == null || idAlunoParam.isBlank()) {
            throw new IllegalArgumentException("Selecione um aluno para o treino.");
        }

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do treino é obrigatório.");
        }

        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("A descrição do treino é obrigatória.");
        }
    }
}
