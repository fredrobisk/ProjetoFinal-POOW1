package controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import service.RelatorioService;

@Controller
public class ServicosController {

    private final RelatorioService relatorioService;

    public ServicosController(RelatorioService relatorioService) {
        this.relatorioService = relatorioService;
    }

    @GetMapping("/servicos")
    public String servicos(HttpSession session, Model model) {
        if (session == null || session.getAttribute("usuarioLogado") == null) {
            return "redirect:/";
        }

        model.addAttribute("resumo", relatorioService.buscarResumo());
        model.addAttribute("alunosPorPlano", relatorioService.listarAlunosPorPlano());
        return "servicos";
    }
}
