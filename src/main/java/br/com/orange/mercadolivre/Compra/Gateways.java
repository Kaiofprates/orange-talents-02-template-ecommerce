package br.com.orange.mercadolivre.Compra;

import org.springframework.util.Assert;

public class Gateways {

    private Pagamentos pagamento;

    public enum Pagamentos {
        PAYPAL,
        PAGSEGURO
    }

    public Gateways(Pagamentos pagamento) {
        this.pagamento = pagamento;
    }

    public String  avaliaCompra(Compra compra){

        Assert.notNull(compra,"Compra não identificada");

        switch (getPagamento()){
            case PAYPAL: return "http://localhost:8080/mercadolivre/paypal.com/"+compra.getId()+"?redirectUrl=urlRetornoAppPosPagamento";
            case PAGSEGURO: return "http://localhost:8080/mercadolivre/pagseguro.com/"+compra.getId()+"?redirectUrl=urlRetornoAppPosPagamento";
        }
        return null;
    }

    public Pagamentos getPagamento() {
        return pagamento;
    }
}
