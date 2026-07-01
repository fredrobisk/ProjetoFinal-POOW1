package controller;

import jakarta.servlet.http.HttpSession;
import model.Plano;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.PlanoService;

@Controller
public class PlanoController {

    private final PlanoService planoService;

    public PlanoController(PlanoService planoService) {
        this.planoService = planoService;
    }

    @GetMapping("/planos")
    public String listar(@RequestParam(value = "acao", required = false) String acao,
                         @RequestParam(value = "id", required = false) Integer id,
                         HttpSession session,
                         Model model) {
        if (!usuarioEstaLogado(session)) {
            return "redirect:/";
        }

        if ("editar".equals(acao) && id != null) {
            Plano plano = planoService.buscarPorId(id);
            model.addAttribute("plano", plano);
        }

        if ("excluir".equals(acao) && id != null) {
            try {
                planoService.excluir(id);
                return "redirect:/planos?msg=excluido";
            } catch (IllegalArgumentException e) {
                model.addAttribute("erro", e.getMessage());
            }
        }

        model.addAttribute("planos", planoService.listar());
        return "planos";
    }

    @PostMapping("/planos")
    public String salvar(@RequestParam(value = "id", required = false) String id,
                         @RequestParam("nome") String nome,
                         @RequestParam(value = "descricao", required = false) String descricao,
                         @RequestParam("valor") String valor,
                         @RequestParam(value = "ativo", required = false) String ativo,
                         HttpSession session,
                         Model model) {
        if (!usuarioEstaLogado(session)) {
            return "redirect:/";
        }

        try {
            planoService.salvar(id, nome, descricao, valor, ativo);
            return "redirect:/planos?msg=salvo";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            model.addAttribute("planos", planoService.listar());
            return "planos";
        }
    }

    private boolean usuarioEstaLogado(HttpSession session) {
        return session != null && session.getAttribute("usuarioLogado") != null;
    }
}
