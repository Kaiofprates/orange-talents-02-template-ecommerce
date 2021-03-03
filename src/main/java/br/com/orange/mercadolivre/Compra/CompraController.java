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

    @PostMapping("/produtos/api/compra")
    @Transactional
    public String compra(@RequestBody @Valid  CompraRequest request, HttpServletResponse httpServletResponse) {

        Produto produto = manager.find(Produto.class, request.getProdutoId());
        Usuario comprador = authUtils.checkUser();
        Compra compra = request.toModel(produto,comprador,sendMail);

        manager.persist(compra);

        Gateways pagamento  = new Gateways(request.getPagamento());

        /*
        *   Seria correto fazer o redirect, mas achei melhor seguir os caminhos que o Alberto fez
        *   de qualquer forma em outro momento já esta aqui o código que redirecionaria o usuário
        *   à rota indicada nas especificações.
        *   Até porque esse redirect como tem um id que em tese é aleatório, não retorna o caminho certo
        *   porque tenta ir para a rota e dá outro retorno.
        */

        //httpServletResponse.setHeader("Location", pagamento.avaliaCompra(compra));
        //httpServletResponse.setStatus(302);

        return pagamento.avaliaCompra(compra);

    }


}
