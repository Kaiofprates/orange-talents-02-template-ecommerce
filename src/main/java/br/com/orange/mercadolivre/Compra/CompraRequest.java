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

    @Deprecated
    public CompraRequest(){}

    public CompraRequest(int quantidade, Gateways.Pagamentos pagamento) {
        this.quantidade = quantidade;
        this.pagamento = pagamento;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public Gateways.Pagamentos getPagamento() {
        return pagamento;
    }

    public Compra toModel(Produto produto, Usuario comprador, SendMail sendMail)  {
        Assert.notNull(produto, "Produto não encontrado");
        Assert.notNull(comprador, "Usuário não encontrado");
        Assert.isTrue(produto.getQuantidade() >= quantidade, "Não há quantidade suficiente no estoque para a sua requisição");

        /*
        * Não há sentindo dentre as regras de negócio que eu abata o produto do estoque sem que a compra seja finalizada
        * deixarei essa lógica para o próximo passo do desafio.
        */

        // Deixo comentado para não ficar enviando emails por ae...
       //  sendMail.send("Há uma novo interesse de compra para o produto: "+ produto.getNome()+ " \n cliente: "+comprador.getEmail(),produto.getUsuario().getEmail());

        return new Compra(comprador,produto,quantidade,pagamento);
    }
}
