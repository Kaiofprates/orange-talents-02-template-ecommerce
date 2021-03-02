package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.MailSender.SendMail;
import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class CompraRequest {

    @NotNull
    @Positive(message = "Informe uma quantidade válida")
    private int quantidade;

    @NotNull
    private Gateways.Pagamentos pagamento;

    @Positive
    @NotNull
    private Long produtoId;


    @Deprecated
    public CompraRequest(){}

    public CompraRequest(int quantidade, Gateways.Pagamentos pagamento) {
        this.quantidade = quantidade;
        this.pagamento = pagamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public Gateways.Pagamentos getPagamento() {
        return pagamento;
    }

    public Compra toModel(Produto produto, Usuario comprador, SendMail sendMail)  {
        Assert.notNull(produto, "Produto não encontrado");
        Assert.notNull(comprador, "Usuário não encontrado");
        Assert.isTrue(produto.getQuantidade() >= quantidade, "Não há quantidade suficiente no estoque para a sua requisição");
        Assert.isTrue(produto.abateEstoque(quantidade), "Não foi possível abater a quantidade do estoque");

        // Deixo comentado para não ficar enviando emails por ae...
       //  sendMail.send("Há uma novo interesse de compra para o produto: "+ produto.getNome()+ " \n cliente: "+comprador.getEmail(),produto.getUsuario().getEmail());

        return new Compra(comprador,produto,quantidade,pagamento);
    }
}
