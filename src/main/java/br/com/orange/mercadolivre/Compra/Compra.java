package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.UUID;

@Entity
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne
    private Usuario comprador;

    @NotNull
    @OneToOne
    private Produto produto;

    @Positive
    private int quantidade;

    @NotNull
    private UUID identificador = UUID.randomUUID();


    @NotNull
    @Enumerated(EnumType.STRING)
    private Gateways.Pagamentos gateway;

    @Enumerated(EnumType.STRING)
    private Status statusCompra = Status.INICIADA;


    public enum Status{
        INICIADA,
        FINALIZADA
    }



    @Deprecated
    public Compra(){}

    public Compra(@NotNull Usuario comprador, @NotNull Produto produto, @Positive int quantidade, Gateways.Pagamentos gateway) {
        this.comprador = comprador;
        this.produto = produto;
        this.quantidade = quantidade;
        this.gateway = gateway;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", comprador=" + comprador +
                ", produto=" + produto +
                ", quantidade=" + quantidade +
                ", identificador=" + identificador +
                '}';
    }

    public String getIdentificador() {
        return identificador.toString();
    }
}
