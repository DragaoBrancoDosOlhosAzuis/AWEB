package br.com.aweb.sistema_vendas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Nome completo é obrigatório")
    @Column(nullable = false, length = 100)
    private String nomeCompleto;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail deve ser válido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos")
    @Column(nullable = false, unique = true, length = 11)
    private String cpf;

    @NotBlank(message = "Telefone é obrigatório")
    @Column(nullable = false, length = 20)
    private String telefone;

    @NotBlank(message = "Logradouro é obrigatório")
    @Column(nullable = false, length = 100)
    private String logradouro;

    private String numero;

    private String complemento;

    @NotBlank(message = "Bairro é obrigatório")
    @Column(nullable = false, length = 50)
    private String bairro;

    @NotBlank(message = "Cidade é obrigatório")
    @Column(nullable = false, length = 50)
    private String cidade;

    @NotBlank(message = "UF é obrigatório")
    @Column(nullable = false, length = 2)
    private String uf;

    @NotBlank(message = "CEP é obrigatório")
    @Pattern(regexp = "\\d{8}", message = "CEP deve conter 8 dígitos")
    @Column(nullable = false, length = 8)
    private String cep;

    public String getEnderecoCompleto() {
        StringBuilder endereco = new StringBuilder();
        endereco.append(logradouro);
        if (numero != null && !numero.trim().isEmpty()) {
            endereco.append(", ").append(numero);
        }
        if (complemento != null && !complemento.trim().isEmpty()) {
            endereco.append(" - ").append(complemento);
        }
        endereco.append(" - ").append(bairro)
               .append(" - ").append(cidade)
               .append("/").append(uf)
               .append(" - CEP: ").append(cep);
        return endereco.toString();
    }
}