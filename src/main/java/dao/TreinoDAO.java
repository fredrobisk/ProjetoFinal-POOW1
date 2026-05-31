package dao;

import model.Treino;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TreinoDAO {

    private Treino mapearTreino(ResultSet rs) throws SQLException {
        Treino treino = new Treino();
        treino.setId(rs.getInt("id"));
        treino.setIdAluno(rs.getInt("id_aluno"));
        treino.setNomeAluno(rs.getString("nome_aluno"));
        treino.setNome(rs.getString("nome"));
        treino.setDescricao(rs.getString("descricao"));
        treino.setDiaSemana(rs.getString("dia_semana"));
        treino.setAtivo(rs.getBoolean("ativo"));
        return treino;
    }

    public List<Treino> listar() {
        String sql = """
                SELECT t.id, t.id_aluno, a.nome AS nome_aluno,
                       t.nome, t.descricao, t.dia_semana, t.ativo
                FROM treino t
                INNER JOIN aluno a ON a.id = t.id_aluno
                ORDER BY t.id
                """;

        List<Treino> treinos = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                treinos.add(mapearTreino(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar treinos.", e);
        }

        return treinos;
    }

    public Treino buscarPorId(int id) {
        String sql = """
                SELECT t.id, t.id_aluno, a.nome AS nome_aluno,
                       t.nome, t.descricao, t.dia_semana, t.ativo
                FROM treino t
                INNER JOIN aluno a ON a.id = t.id_aluno
                WHERE t.id = ?
                """;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearTreino(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar treino.", e);
        }

        return null;
    }

    public void inserir(Treino treino) {
        String sql = """
                INSERT INTO treino (id_aluno, nome, descricao, dia_semana, ativo)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherParametros(stmt, treino);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir treino.", e);
        }
    }

    public void atualizar(Treino treino) {
        String sql = """
                UPDATE treino
                SET id_aluno = ?, nome = ?, descricao = ?, dia_semana = ?, ativo = ?
                WHERE id = ?
                """;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherParametros(stmt, treino);
            stmt.setInt(6, treino.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar treino.", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM treino WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir treino.", e);
        }
    }

    private void preencherParametros(PreparedStatement stmt, Treino treino) throws SQLException {
        stmt.setInt(1, treino.getIdAluno());
        stmt.setString(2, treino.getNome());
        stmt.setString(3, treino.getDescricao());
        stmt.setString(4, treino.getDiaSemana());
        stmt.setBoolean(5, treino.isAtivo());
    }
}