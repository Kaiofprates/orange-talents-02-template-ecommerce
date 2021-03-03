package br.com.orange.mercadolivre.Pergunta;

import br.com.orange.mercadolivre.MailSender.SendMail;
import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.security.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    @Autowired
    private SendMail sendMail;

    @Autowired
    private AuthUtils authUtils;

    @PostMapping("/produtos/{id}/perguntas")
    @Transactional
    public ResponseEntity<?> novaPergunta(@RequestBody @Valid PerguntaRequest request, @PathVariable("id") Long id){
        Usuario perguntador  = authUtils.checkUser();
        Produto produto = manager.find(Produto.class,id);
        Pergunta pergunta = request.toModel(perguntador, produto);
        manager.persist(pergunta);
        sendMail.send("Seu produto possui uma nova mensagem "+pergunta.getTitulo(),produto.getUsuario().getEmail());

        return ResponseEntity.ok().build();
    }


}
