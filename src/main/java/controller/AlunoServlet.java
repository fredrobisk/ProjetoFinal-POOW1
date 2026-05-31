package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Aluno;
import model.Plano;
import service.AlunoService;
import service.PlanoService;

import java.io.IOException;
import java.util.List;

@WebServlet("/alunos")
public class AlunoServlet extends HttpServlet {

    private final AlunoService alunoService = new AlunoService();
    private final PlanoService planoService = new PlanoService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!usuarioEstaLogado(request)) {
            response.sendRedirect("index.jsp");
            return;
        }

        String acao = request.getParameter("acao");

        if ("editar".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Aluno aluno = alunoService.buscarPorId(id);
            request.setAttribute("aluno", aluno);
        }

        if ("excluir".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));
            alunoService.excluir(id);
            response.sendRedirect("alunos?msg=excluido");
            return;
        }

        carregarDados(request);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/alunos.jsp");
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
            alunoService.salvar(
                    request.getParameter("id"),
                    request.getParameter("nome"),
                    request.getParameter("cpf"),
                    request.getParameter("dataNascimento"),
                    request.getParameter("endereco"),
                    request.getParameter("telefone"),
                    request.getParameter("idPlano")
            );

            response.sendRedirect("alunos?msg=salvo");
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            carregarDados(request);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/alunos.jsp");
            dispatcher.forward(request, response);
        }
    }

    private void carregarDados(HttpServletRequest request) {
        List<Aluno> alunos = alunoService.listar();
        List<Plano> planos = planoService.listar();

        request.setAttribute("alunos", alunos);
        request.setAttribute("planos", planos);
    }

    private boolean usuarioEstaLogado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("usuarioLogado") != null;
    }
}
