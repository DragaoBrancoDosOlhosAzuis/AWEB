package br.com.aweb.sistema_vendas.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index"; // ← Crie um arquivo index.html na pasta templates
    }
}