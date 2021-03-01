package br.com.orange.mercadolivre.Pergunta;

import br.com.orange.mercadolivre.MailSender.SendMail;
import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
@RequestMapping("/mercadolivre/")
public class PerguntaController {

    @PersistenceContext
    private EntityManager manager;
    // 7
    @Autowired
    private SendMail sendMail;

    // 1
    @Autowired
    private AuthUtils authUtils;

    @PostMapping("/produtos/{id}/perguntas")
    @Transactional
    public String novaPergunta(@RequestBody @Valid PerguntaRequest request, @PathVariable("id") Long id){
        //2
        Usuario perguntador  = authUtils.checkUser();
        //3
        Produto produto = manager.find(Produto.class,id);
        //4
        Pergunta pergunta = request.toModel(perguntador, produto);
        //5
        manager.persist(pergunta);
        // 6
        sendMail.send("Seu produto possui uma nova mensagem "+pergunta.getTitulo(),produto.getUsuario().getEmail());

        return pergunta.toString();
    }


}
