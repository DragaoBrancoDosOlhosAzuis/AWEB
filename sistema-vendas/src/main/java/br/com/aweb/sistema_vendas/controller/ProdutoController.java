package br.com.aweb.sistema_vendas.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.aweb.sistema_vendas.model.Produto;
import br.com.aweb.sistema_vendas.service.ProdutoService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/produtos") // ← CORRIGIDO: estava "/produto" no seu código?
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Listar produtos
    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("produto/list", Map.of("produtos", produtoService.listarTodos()));
    }

    // Formulário de cadastro
    @GetMapping("/novo")
    public ModelAndView create() {
        return new ModelAndView("produto/form", Map.of("produto", new Produto()));
    }

    // Salvar produto
    @PostMapping("/novo")
    public String create(@Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "produto/form";
        }
        produtoService.salvar(produto);
        redirectAttributes.addFlashAttribute("success", "Produto cadastrado com sucesso!");
        return "redirect:/produtos"; // ← CORRIGIDO: redireciona para /produtos
    }

    // Formulário de edição
    @GetMapping("/editar/{id}") // ← CORRIGIDO: padrão mais consistente
    public ModelAndView edit(@PathVariable Long id) {
        var optionalProduto = produtoService.buscarPorId(id);
        if (optionalProduto.isPresent()) {
            return new ModelAndView("produto/form", Map.of("produto", optionalProduto.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // Atualizar produto
    @PostMapping("/editar/{id}") // ← CORRIGIDO: padrão mais consistente
    public String edit(@PathVariable Long id, @Valid Produto produto, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "produto/form";
        }

        produtoService.atualizar(id, produto);
        redirectAttributes.addFlashAttribute("success", "Produto atualizado com sucesso!");
        return "redirect:/produtos";
    }

    // Excluir produto - Confirmação
    @GetMapping("/excluir/{id}") // ← CORRIGIDO: padrão mais consistente
    public ModelAndView delete(@PathVariable Long id) {
        var optionalProduto = produtoService.buscarPorId(id);
        if (optionalProduto.isPresent()) {
            return new ModelAndView("produto/delete", Map.of("produto", optionalProduto.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // Excluir produto - Execução
    @PostMapping("/excluir/{id}") // ← CORRIGIDO: padrão mais consistente
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        produtoService.excluir(id);
        redirectAttributes.addFlashAttribute("success", "Produto excluído com sucesso!");
        return "redirect:/produtos";
    }
}