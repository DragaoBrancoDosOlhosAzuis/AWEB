package br.com.aweb.sistema_vendas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.aweb.sistema_vendas.model.Cliente;
import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);
    boolean existsByEmailAndIdNot(String email, Long id);
    boolean existsByCpfAndIdNot(String cpf, Long id);
}