package dao;

import model.Plano;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PlanoDAO {

    private Plano mapearPlano(ResultSet rs) throws SQLException {
        Plano plano = new Plano();
        plano.setId(rs.getInt("id"));
        plano.setNome(rs.getString("nome"));
        plano.setDescricao(rs.getString("descricao"));
        plano.setValor(rs.getBigDecimal("valor"));
        plano.setAtivo(rs.getBoolean("ativo"));
        return plano;
    }

    public List<Plano> listar() {
        String sql = "SELECT id, nome, descricao, valor, ativo FROM plano ORDER BY id";
        List<Plano> planos = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                planos.add(mapearPlano(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar planos.", e);
        }

        return planos;
    }

    public Plano buscarPorId(int id) {
        String sql = "SELECT id, nome, descricao, valor, ativo FROM plano WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearPlano(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar plano.", e);
        }

        return null;
    }

    public void inserir(Plano plano) {
        String sql = "INSERT INTO plano (nome, descricao, valor, ativo) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setBigDecimal(3, plano.getValor());
            stmt.setBoolean(4, plano.isAtivo());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir plano.", e);
        }
    }

    public void atualizar(Plano plano) {
        String sql = "UPDATE plano SET nome = ?, descricao = ?, valor = ?, ativo = ? WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setBigDecimal(3, plano.getValor());
            stmt.setBoolean(4, plano.isAtivo());
            stmt.setInt(5, plano.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar plano.", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM plano WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir plano.", e);
        }
    }

    public int contarAlunosVinculados(int idPlano) {
        String sql = "SELECT COUNT(*) FROM aluno WHERE id_plano = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idPlano);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar alunos vinculados ao plano.", e);
        }

        return 0;
    }
}
