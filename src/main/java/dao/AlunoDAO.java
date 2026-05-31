package dao;

import model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

    private Aluno mapearAluno(ResultSet rs) throws SQLException {
        Aluno aluno = new Aluno();
        aluno.setId(rs.getInt("id"));
        aluno.setNome(rs.getString("nome"));
        aluno.setCpf(rs.getString("cpf"));

        Date dataNascimento = rs.getDate("data_nascimento");
        if (dataNascimento != null) {
            aluno.setDataNascimento(dataNascimento.toLocalDate());
        }

        aluno.setEndereco(rs.getString("endereco"));
        aluno.setTelefone(rs.getString("telefone"));
        aluno.setIdPlano(rs.getInt("id_plano"));
        aluno.setNomePlano(rs.getString("nome_plano"));

        return aluno;
    }

    public List<Aluno> listar() {
        String sql = """
                SELECT a.id, a.nome, a.cpf, a.data_nascimento, a.endereco, a.telefone,
                       a.id_plano, p.nome AS nome_plano
                FROM aluno a
                LEFT JOIN plano p ON p.id = a.id_plano
                ORDER BY a.id
                """;

        List<Aluno> alunos = new ArrayList<>();

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                alunos.add(mapearAluno(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos.", e);
        }

        return alunos;
    }

    public Aluno buscarPorId(int id) {
        String sql = """
                SELECT a.id, a.nome, a.cpf, a.data_nascimento, a.endereco, a.telefone,
                       a.id_plano, p.nome AS nome_plano
                FROM aluno a
                LEFT JOIN plano p ON p.id = a.id_plano
                WHERE a.id = ?
                """;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapearAluno(rs);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno.", e);
        }

        return null;
    }

    public void inserir(Aluno aluno) {
        String sql = """
                INSERT INTO aluno (nome, cpf, data_nascimento, endereco, telefone, id_plano)
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherParametros(stmt, aluno);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir aluno.", e);
        }
    }

    public void atualizar(Aluno aluno) {
        String sql = """
                UPDATE aluno
                SET nome = ?, cpf = ?, data_nascimento = ?, endereco = ?, telefone = ?, id_plano = ?
                WHERE id = ?
                """;

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherParametros(stmt, aluno);
            stmt.setInt(7, aluno.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno.", e);
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM aluno WHERE id = ?";

        try (Connection conn = ConexaoDB.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir aluno.", e);
        }
    }

    private void preencherParametros(PreparedStatement stmt, Aluno aluno) throws SQLException {
        stmt.setString(1, aluno.getNome());
        stmt.setString(2, aluno.getCpf());

        if (aluno.getDataNascimento() != null) {
            stmt.setDate(3, Date.valueOf(aluno.getDataNascimento()));
        } else {
            stmt.setNull(3, Types.DATE);
        }

        stmt.setString(4, aluno.getEndereco());
        stmt.setString(5, aluno.getTelefone());
        stmt.setInt(6, aluno.getIdPlano());
    }
}