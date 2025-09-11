package br.com.aweb.sistema_vendas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.aweb.sistema_vendas.Entity.Produto;
import br.com.aweb.sistema_vendas.service.ProdutoService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/produtos")
public class ProductController {

    @Autowired
    private ProdutoService productservice;
    public ModelAndView create(){
        return new ModelAndView("produto/form", Map.of(k1: "produto", new Produto()))
    }

    @PostMapping("/novo")
    public String name(@Valid Produto produto, BindingResult result) {
        if (result.hasErrors())        
        
        return "produto/form";

        produtosService.salvar(produto);
        return "redirect://produtos"
    }
    
    
}
