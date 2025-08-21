package br.com.aweb.sistema_produto.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @NotNull(message = "O preço é obrigatório")
    @PositiveOrZero(message = "O preço deve ser maior ou igual a zero")
    private Double price;

    private String description;

    public Product() {
    }

    public Product(Long id, @NotBlank(message = "O nome é obrigatório") String name,
            @NotNull(message = "O preço é obrigatório") @PositiveOrZero(message = "O preço deve ser maior ou igual a zero") Double price,
            String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
