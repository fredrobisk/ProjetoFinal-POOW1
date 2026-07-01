package controller;

import jakarta.servlet.http.HttpSession;
import model.Aluno;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import service.AlunoService;
import service.PlanoService;

@Controller
public class AlunoController {

    private final AlunoService alunoService;
    private final PlanoService planoService;

    public AlunoController(AlunoService alunoService, PlanoService planoService) {
        this.alunoService = alunoService;
        this.planoService = planoService;
    }

    @GetMapping("/alunos")
    public String listar(@RequestParam(value = "acao", required = false) String acao,
                         @RequestParam(value = "id", required = false) Integer id,
                         HttpSession session,
                         Model model) {
        if (!usuarioEstaLogado(session)) {
            return "redirect:/";
        }

        if ("editar".equals(acao) && id != null) {
            Aluno aluno = alunoService.buscarPorId(id);
            model.addAttribute("aluno", aluno);
        }

        if ("excluir".equals(acao) && id != null) {
            alunoService.excluir(id);
            return "redirect:/alunos?msg=excluido";
        }

        carregarDados(model);
        return "alunos";
    }

    @PostMapping("/alunos")
    public String salvar(@RequestParam(value = "id", required = false) String id,
                         @RequestParam("nome") String nome,
                         @RequestParam("cpf") String cpf,
                         @RequestParam(value = "dataNascimento", required = false) String dataNascimento,
                         @RequestParam(value = "endereco", required = false) String endereco,
                         @RequestParam(value = "telefone", required = false) String telefone,
                         @RequestParam("idPlano") String idPlano,
                         HttpSession session,
                         Model model) {
        if (!usuarioEstaLogado(session)) {
            return "redirect:/";
        }

        try {
            alunoService.salvar(id, nome, cpf, dataNascimento, endereco, telefone, idPlano);
            return "redirect:/alunos?msg=salvo";
        } catch (IllegalArgumentException e) {
            model.addAttribute("erro", e.getMessage());
            carregarDados(model);
            return "alunos";
        }
    }

    private void carregarDados(Model model) {
        model.addAttribute("alunos", alunoService.listar());
        model.addAttribute("planos", planoService.listar());
    }

    private boolean usuarioEstaLogado(HttpSession session) {
        return session != null && session.getAttribute("usuarioLogado") != null;
    }
}
