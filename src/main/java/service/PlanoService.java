package service;

import dao.PlanoDAO;
import model.Plano;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PlanoService {

    private final PlanoDAO planoDAO;

    public PlanoService(PlanoDAO planoDAO) {
        this.planoDAO = planoDAO;
    }

    public List<Plano> listar() {
        return planoDAO.listar();
    }

    public Plano buscarPorId(int id) {
        return planoDAO.buscarPorId(id);
    }

    public void salvar(String idParam, String nome, String descricao, String valorParam, String ativoParam) {
        validar(nome, valorParam);

        Plano plano = new Plano();
        plano.setNome(nome.trim());
        plano.setDescricao(descricao == null ? "" : descricao.trim());
        plano.setValor(new BigDecimal(valorParam.replace(",", ".")));
        plano.setAtivo("on".equals(ativoParam) || "true".equals(ativoParam));

        if (idParam != null && !idParam.isBlank()) {
            plano.setId(Integer.parseInt(idParam));
            planoDAO.atualizar(plano);
        } else {
            planoDAO.inserir(plano);
        }
    }

    public void excluir(int id) {
        int alunosVinculados = planoDAO.contarAlunosVinculados(id);

        if (alunosVinculados > 0) {
            throw new IllegalArgumentException("Não é possível excluir este plano porque existem alunos vinculados a ele.");
        }

        planoDAO.excluir(id);
    }

    private void validar(String nome, String valorParam) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do plano é obrigatório.");
        }

        if (valorParam == null || valorParam.isBlank()) {
            throw new IllegalArgumentException("O valor do plano é obrigatório.");
        }

        try {
            BigDecimal valor = new BigDecimal(valorParam.replace(",", "."));

            if (valor.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("O valor do plano não pode ser negativo.");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("O valor do plano deve ser numérico.");
        }
    }
}
