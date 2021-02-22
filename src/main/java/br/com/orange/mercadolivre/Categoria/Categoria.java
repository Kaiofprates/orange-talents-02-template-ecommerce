package br.com.orange.mercadolivre.Categoria;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "categoria", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "id"})})
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @ManyToOne
    private Categoria categoria;

    @Deprecated
    private Categoria() {
    }

    public Categoria(@NotBlank String nome) {
        this.nome = nome;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

}
