package br.com.orange.mercadolivre.Compra;

import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre")
public class GatewayController {

    @PersistenceContext
    private EntityManager manager;


    @PostMapping("/paypal.com/{id}")
    @Transactional
    public String paypalPagamento(@PathVariable("id") Long id,@Valid  PaypalRequest request){

        Compra compra = manager.find(Compra.class,id);
        Assert.isTrue(compra != null, "Registro de compra não encontrado!");
        compra.registraTransacao(request.getStatus(), request.getIdTransacao());

        manager.merge(compra);

        return compra.toString();
    }

    @PostMapping("/pagseguro.com/{id}")
    @Transactional
    public String pagseguroPagamento(@PathVariable("id") Long id,@Valid  PageseguroRequest request){

        Compra compra = manager.find(Compra.class,id);
        Assert.isTrue(compra != null, "Registro de compra não encontrado!");
        compra.registraTransacao(request.getStatus(), request.getIdTransacao());

        manager.merge(compra);

        return compra.toString();
    }


}
