package br.com.aweb.sistema_vendas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.aweb.sistema_vendas.Entity.Produto;
import br.com.aweb.sistema_vendas.Repository.ProdutoRepository;

@Service
public class ProdutoService {
    
    @Autowired
    ProdutoRepository produtoRepository;

    
    @Transactional
    public Produto salvar(Produto produto){
        return produtoRepository.save(produto);

    }

}
