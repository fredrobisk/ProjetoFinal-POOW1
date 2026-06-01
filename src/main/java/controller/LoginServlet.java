package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Usuario;
import service.LoginService;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final LoginService loginService = new LoginService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        Usuario usuario = loginService.autenticar(email, senha);

        if (usuario == null) {
            request.setAttribute("erro", "E-mail ou senha inválidos.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession(); //aqui verifica se esta logado
        session.setAttribute("usuarioLogado", usuario);

        response.sendRedirect("servicos");
    }
}