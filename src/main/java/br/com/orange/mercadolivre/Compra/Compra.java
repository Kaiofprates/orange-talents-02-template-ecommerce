package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;

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
    @Enumerated(EnumType.STRING)
    private Gateways.Pagamentos gateway;

    @Enumerated(EnumType.STRING)
    private Status statusCompra = Status.INICIADA;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.MERGE)
    private Set<Transacao> transacoes = new HashSet<>();


    public void registraTransacao(Boolean status, String idTransacao) {

        /**
         *  Não há necessidade de percorrer transações para validar apenas uma como finalizada
         *  se o estado for finalizado não haverá mais transações :)
         */
        Assert.isTrue(this.statusCompra != Status.FINALIZADA, "Não há mais transações disponíveis para esse produto");

        transacoes.add(new Transacao(status, idTransacao, this));

        if(status){
            this.statusCompra = Status.FINALIZADA;
        }
    }


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
                ", gateway=" + gateway +
                ", statusCompra=" + statusCompra +
                ", transacoes=" + transacoes +
                '}';
    }

    public String getId() {
        return id.toString();
    }
}
