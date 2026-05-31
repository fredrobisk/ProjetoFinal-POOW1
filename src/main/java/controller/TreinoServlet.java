package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Aluno;
import model.Treino;
import service.AlunoService;
import service.TreinoService;

import java.io.IOException;
import java.util.List;

@WebServlet("/treinos")
public class TreinoServlet extends HttpServlet {

    private final TreinoService treinoService = new TreinoService();
    private final AlunoService alunoService = new AlunoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!usuarioEstaLogado(request)) {
            response.sendRedirect("index.jsp");
            return;
        }

        String acao = request.getParameter("acao");

        if ("editar".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Treino treino = treinoService.buscarPorId(id);
            request.setAttribute("treino", treino);
        }

        if ("excluir".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            treinoService.excluir(id);
            response.sendRedirect("treinos?msg=excluido");
            return;
        }

        carregarDados(request);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/treinos.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if (!usuarioEstaLogado(request)) {
            response.sendRedirect("index.jsp");
            return;
        }

        try {
            treinoService.salvar(
                    request.getParameter("id"),
                    request.getParameter("idAluno"),
                    request.getParameter("nome"),
                    request.getParameter("descricao"),
                    request.getParameter("diaSemana"),
                    request.getParameter("ativo")
            );

            response.sendRedirect("treinos?msg=salvo");
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            carregarDados(request);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/treinos.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void carregarDados(HttpServletRequest request) {
        List<Treino> treinos = treinoService.listar();
        List<Aluno> alunos = alunoService.listar();

        request.setAttribute("treinos", treinos);
        request.setAttribute("alunos", alunos);
    }

    private boolean usuarioEstaLogado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("usuarioLogado") != null;
    }
}