package service;

import dao.RelatorioDAO;
import model.AlunosPorPlano;
import model.ResumoAcademia;

import java.util.List;

public class RelatorioService {

    private final RelatorioDAO relatorioDAO = new RelatorioDAO();

    public ResumoAcademia buscarResumo() {
        return relatorioDAO.buscarResumo();
    }

    public List<AlunosPorPlano> listarAlunosPorPlano() {
        return relatorioDAO.listarAlunosPorPlano();
    }
}