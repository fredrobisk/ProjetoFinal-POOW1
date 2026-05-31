package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Plano;
import service.PlanoService;

import java.io.IOException;
import java.util.List;

@WebServlet("/planos")
public class PlanoServlet extends HttpServlet {

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
            Plano plano = planoService.buscarPorId(id);
            request.setAttribute("plano", plano);
        }

        if ("excluir".equals(acao)) {
            int id = Integer.parseInt(request.getParameter("id"));

            try {
                planoService.excluir(id);
                response.sendRedirect("planos?msg=excluido");
            } catch (IllegalArgumentException e) {
                request.setAttribute("erro", e.getMessage());

                List<Plano> planos = planoService.listar();
                request.setAttribute("planos", planos);

                RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/planos.jsp");
                dispatcher.forward(request, response);
            }

            return;
        }

        List<Plano> planos = planoService.listar();
        request.setAttribute("planos", planos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/planos.jsp");
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
            planoService.salvar(
                    request.getParameter("id"),
                    request.getParameter("nome"),
                    request.getParameter("descricao"),
                    request.getParameter("valor"),
                    request.getParameter("ativo")
            );

            response.sendRedirect("planos?msg=salvo");
        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());

            List<Plano> planos = planoService.listar();
            request.setAttribute("planos", planos);

            RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/pages/planos.jsp");
            dispatcher.forward(request, response);
        }
    }

    private boolean usuarioEstaLogado(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("usuarioLogado") != null;
    }
}