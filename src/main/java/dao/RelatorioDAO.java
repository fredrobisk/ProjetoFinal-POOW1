package dao;

import model.AlunosPorPlano;
import model.ResumoAcademia;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RelatorioDAO {

    public ResumoAcademia buscarResumo() {
        ResumoAcademia resumo = new ResumoAcademia();

        resumo.setTotalAlunos(contar("SELECT COUNT(*) FROM aluno"));
        resumo.setTotalPlanosAtivos(contar("SELECT COUNT(*) FROM plano WHERE ativo = TRUE"));
        resumo.setTotalTreinosAtivos(contar("SELECT COUNT(*) FROM treino WHERE ativo = TRUE"));

        return resumo;
    }

    public List<AlunosPorPlano> listarAlunosPorPlano() {
        String sql = """
                SELECT p.nome AS nome_plano, COUNT(a.id) AS quantidade_alunos
                FROM plano p
                LEFT JOIN aluno a ON a.id_plano = p.id
                GROUP BY p.nome
                ORDER BY p.nome
                """;

        List<AlunosPorPlano> lista = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                AlunosPorPlano item = new AlunosPorPlano();
                item.setNomePlano(rs.getString("nome_plano"));
                item.setQuantidadeAlunos(rs.getInt("quantidade_alunos"));
                lista.add(item);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos por plano.", e);
        }

        return lista;
    }

    private int contar(String sql) {
        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao executar consulta de contagem.", e);
        }

        return 0;
    }
}
