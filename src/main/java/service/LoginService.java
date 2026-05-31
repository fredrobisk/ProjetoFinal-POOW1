package service;

import dao.UsuarioDAO;
import model.Usuario;

public class LoginService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario autenticar(String email, String senha) {
        if (email == null || email.isBlank()) {
            return null;
        }

        if (senha == null || senha.isBlank()) {
            return null;
        }

        return usuarioDAO.autenticar(email.trim(), senha);
    }
}