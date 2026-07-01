package service;

import dao.RelatorioDAO;
import model.AlunosPorPlano;
import model.ResumoAcademia;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RelatorioService {

    private final RelatorioDAO relatorioDAO;

    public RelatorioService(RelatorioDAO relatorioDAO) {
        this.relatorioDAO = relatorioDAO;
    }

    public ResumoAcademia buscarResumo() {
        return relatorioDAO.buscarResumo();
    }

    public List<AlunosPorPlano> listarAlunosPorPlano() {
        return relatorioDAO.listarAlunosPorPlano();
    }
}
