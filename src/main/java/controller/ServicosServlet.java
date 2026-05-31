package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.AlunosPorPlano;
import model.ResumoAcademia;
import service.RelatorioService;

import java.io.IOException;
import java.util.List;

@WebServlet("/servicos")
public class ServicosServlet extends HttpServlet {

    private final RelatorioService relatorioService = new RelatorioService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("usuarioLogado") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        ResumoAcademia resumo = relatorioService.buscarResumo();
        List<AlunosPorPlano> alunosPorPlano = relatorioService.listarAlunosPorPlano();

        request.setAttribute("resumo", resumo);
        request.setAttribute("alunosPorPlano", alunosPorPlano);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/servicos.jsp");
        dispatcher.forward(request, response);
    }
}