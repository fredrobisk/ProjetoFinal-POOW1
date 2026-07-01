package service;

import dao.UsuarioDAO;
import model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final UsuarioDAO usuarioDAO;

    public LoginService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

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
