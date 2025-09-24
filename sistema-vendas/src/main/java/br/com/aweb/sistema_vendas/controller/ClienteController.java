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

import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.service.ClienteService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // Listar clientes
    @GetMapping
    public ModelAndView list() {
        return new ModelAndView("cliente/list", Map.of("clientes", clienteService.listarTodos()));
    }

    // Formulário de cadastro
    @GetMapping("/novo")
    public ModelAndView create() {
        return new ModelAndView("cliente/form", Map.of("cliente", new Cliente()));
    }

    // Salvar cliente
    @PostMapping("/novo")
    public String create(@Valid Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cliente/form";
        }
        
        try {
            clienteService.salvar(cliente);
            redirectAttributes.addFlashAttribute("success", "Cliente cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.cliente", e.getMessage());
            result.rejectValue("cpf", "error.cliente", e.getMessage());
            return "cliente/form";
        }
        
        return "redirect:/clientes";
    }

    // Formulário de edição - CORRIGIDO
    @GetMapping("/edit/{id}") // ← Mudei de "editar" para "edit" para manter padrão
    public ModelAndView edit(@PathVariable Long id) {
        var optionalCliente = clienteService.buscarPorId(id);
        if (optionalCliente.isPresent()) {
            return new ModelAndView("cliente/form", Map.of("cliente", optionalCliente.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // Atualizar cliente - CORRIGIDO
    @PostMapping("/edit/{id}") // ← Mudei de "editar" para "edit" para manter padrão
    public String edit(@PathVariable Long id, @Valid Cliente cliente, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "cliente/form";
        }

        try {
            clienteService.atualizar(id, cliente);
            redirectAttributes.addFlashAttribute("success", "Cliente atualizado com sucesso!");
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.cliente", e.getMessage());
            result.rejectValue("cpf", "error.cliente", e.getMessage());
            return "cliente/form";
        }

        return "redirect:/clientes";
    }

    // Excluir cliente - Confirmação - CORRIGIDO
    @GetMapping("/delete/{id}") // ← Mudei de "excluir" para "delete" para manter padrão
    public ModelAndView delete(@PathVariable Long id) {
        var optionalCliente = clienteService.buscarPorId(id);
        if (optionalCliente.isPresent()) {
            return new ModelAndView("cliente/delete", Map.of("cliente", optionalCliente.get()));
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    // Excluir cliente - Execução - CORRIGIDO
    @PostMapping("/delete/{id}") // ← Mudei de "excluir" para "delete" para manter padrão
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            clienteService.excluir(id);
            redirectAttributes.addFlashAttribute("success", "Cliente excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/clientes";
    }
}