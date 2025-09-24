package br.com.aweb.sistema_vendas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.aweb.sistema_vendas.model.Cliente;
import br.com.aweb.sistema_vendas.repository.ClienteRepository;
import jakarta.transaction.Transactional;

@Service
public class ClienteService {

    @Autowired
    ClienteRepository clienteRepository;

    // CREATE
    @Transactional
    public Cliente salvar(Cliente cliente) {
        // Verificar se email já existe
        if (clienteRepository.findByEmail(cliente.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este e-mail.");
        }
        
        // Verificar se CPF já existe
        if (clienteRepository.findByCpf(cliente.getCpf()).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com este CPF.");
        }
        
        return clienteRepository.save(cliente);
    }

    // READ
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // UPDATE
    @Transactional
    public Cliente atualizar(Long id, Cliente clienteAtualizado) {
        var optionalCliente = buscarPorId(id);
        if (!optionalCliente.isPresent())
            throw new IllegalArgumentException("Cliente não encontrado.");

        // Verificar se email já existe para outro cliente
        if (clienteRepository.existsByEmailAndIdNot(clienteAtualizado.getEmail(), id)) {
            throw new IllegalArgumentException("Já existe outro cliente cadastrado com este e-mail.");
        }
        
        // Verificar se CPF já existe para outro cliente
        if (clienteRepository.existsByCpfAndIdNot(clienteAtualizado.getCpf(), id)) {
            throw new IllegalArgumentException("Já existe outro cliente cadastrado com este CPF.");
        }

        var clienteExistente = optionalCliente.get();

        clienteExistente.setNomeCompleto(clienteAtualizado.getNomeCompleto());
        clienteExistente.setEmail(clienteAtualizado.getEmail());
        clienteExistente.setCpf(clienteAtualizado.getCpf());
        clienteExistente.setTelefone(clienteAtualizado.getTelefone());
        clienteExistente.setLogradouro(clienteAtualizado.getLogradouro());
        clienteExistente.setNumero(clienteAtualizado.getNumero());
        clienteExistente.setComplemento(clienteAtualizado.getComplemento());
        clienteExistente.setBairro(clienteAtualizado.getBairro());
        clienteExistente.setCidade(clienteAtualizado.getCidade());
        clienteExistente.setUf(clienteAtualizado.getUf());
        clienteExistente.setCep(clienteAtualizado.getCep());

        return clienteRepository.save(clienteExistente);
    }

    // DELETE
    @Transactional
    public void excluir(Long id) {
        var optionalCliente = buscarPorId(id);
        if (!optionalCliente.isPresent())
            throw new IllegalArgumentException("Cliente não encontrado.");

        clienteRepository.deleteById(id);
    }
}