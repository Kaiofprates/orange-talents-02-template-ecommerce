package br.com.orange.mercadolivre.Compra;


import br.com.orange.mercadolivre.MailSender.SendMail;
import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre")
public class CompraController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private AuthUtils authUtils;

    @Autowired
    private SendMail sendMail;

    @PostMapping("/produtos/{id}/compra")
    @Transactional
    public void compra(@RequestBody @Valid  CompraRequest request, @PathVariable("id") Long id, HttpServletResponse httpServletResponse) {

        Produto produto = manager.find(Produto.class, id);
        Usuario comprador = authUtils.checkUser();
        Compra compra = request.toModel(produto,comprador,sendMail);

        manager.persist(compra);

        // redirecionamento

        Gateways pagamento  = new Gateways(request.getPagamento());
        httpServletResponse.setHeader("Location", pagamento.avaliaCompra(compra));
        httpServletResponse.setStatus(302);

    }


}
