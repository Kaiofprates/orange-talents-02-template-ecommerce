package br.com.orange.mercadolivre.Compra;

import br.com.orange.mercadolivre.Compra.Notas.NotaFiscal;
import br.com.orange.mercadolivre.Compra.Ranking.Ranking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private NotaFiscal nf;

    @Autowired
    private Ranking ranking;


    @PostMapping("/paypal.com/{id}")
    @Transactional
    public ResponseEntity<?> paypalPagamento(@PathVariable("id") Long id, @Valid  PaypalRequest request){

        Compra compra = manager.find(Compra.class,id);
        Assert.isTrue(compra != null, "Registro de compra não encontrado!");
        compra.registraTransacao(request.getStatus(), request.getIdTransacao());

        manager.merge(compra);

        if(compra != null){

            nf.processa(compra);
            ranking.processa(compra);

        }


        return ResponseEntity.ok().build();
    }

    @PostMapping("/pagseguro.com/{id}")
    @Transactional
    public ResponseEntity<?> pagseguroPagamento(@PathVariable("id") Long id,@Valid  PageseguroRequest request){

        Compra compra = manager.find(Compra.class,id);
        Assert.isTrue(compra != null, "Registro de compra não encontrado!");
        compra.registraTransacao(request.getStatus(), request.getIdTransacao());

        manager.merge(compra);

        if(compra != null){

            nf.processa(compra);
            ranking.processa(compra);

        }
        return ResponseEntity.ok().build();
    }


}
