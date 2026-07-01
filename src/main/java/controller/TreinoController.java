package controller;

import jakarta.servlet.http.HttpSession;
import model.Treino;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.AlunoService;
import service.TreinoService;

@Controller
public class TreinoController {

    private final TreinoService treinoService;
    private final AlunoService alunoService;

    public TreinoController(TreinoService treinoService, AlunoService alunoService) {
        this.treinoService = treinoService;
        this.alunoService = alunoService;
    }

    @GetMapping("/treinos")
    public String listar(@RequestParam(value = "acao", required = false) String acao,
                         @RequestParam(value = "id", required = false) Integer id,
                         HttpSession session,
                         Model model) {
        if (!usuarioEstaLogado(session)) {
            return "redirect:/";
        }

        if ("editar".equals(acao) && id != null) {
            Treino treino = treinoService.buscarPorId(id);
            model.addAttribute("treino", treino);
        }

        if ("excluir".equals(acao) && id != null) {
            treinoService.excluir(id);
            return "redirect:/treinos?msg=excluido";
        }

        carregarDados(model);
        return "treinos";
    }

    @PostMapping("/treinos")
    public String salvar(@RequestParam(value = "id", required = false) String id,
                         @RequestParam("idAluno") String idAluno,
                         @RequestParam("nome") String nome,
                         @RequestParam("descricao") String descricao,
                         @RequestParam(value = "diaSemana", required = false) String diaSemana,
                         @RequestParam(value = "ativo", required = false) String ativo,
                         HttpSession session,
                         Model model) {
        if (!usuarioEstaLogado(session)) {
            return "redirect:/";
        }

        try {
            treinoService.salvar(id, idAluno, nome, descricao, diaSemana, ativo);
            return "redirect:/treinos?msg=salvo";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            carregarDados(model);
            return "treinos";
        }
    }

    private void carregarDados(Model model) {
        model.addAttribute("treinos", treinoService.listar());
        model.addAttribute("alunos", alunoService.listar());
    }

    private boolean usuarioEstaLogado(HttpSession session) {
        return session != null && session.getAttribute("usuarioLogado") != null;
    }
}
