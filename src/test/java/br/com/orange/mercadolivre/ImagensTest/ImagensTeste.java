package br.com.orange.mercadolivre.ImagensTest;

import br.com.orange.mercadolivre.Categoria.Categoria;
import br.com.orange.mercadolivre.Usuario.Usuario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ImagensTeste {

    @Autowired
    private MockMvc mvc;

    @PersistenceContext
    private EntityManager manager;

    private Usuario cadastraProduto(){
        Usuario usuario = new Usuario("manuel@email.com","123456");
        Categoria categoria = new Categoria("Informática");
        manager.persist(usuario);
        manager.persist(categoria);
        return usuario;
    }


    @Test
    @Transactional
    @WithMockUser(username = "manuel@email.com",password = "123456")
    public void produtoCadastradoTeste() throws Exception {

        cadastraProduto();


        MockMultipartFile file
                = new MockMultipartFile(
                "imagens",
                "hello.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mvc.perform(multipart("/mercadolivre/produtos/1/imagens").file(file))
                .andExpect(status().isBadRequest());
    }


    @Test
    @Transactional
    @WithMockUser(username = "manuel@email.com",password = "123456")
    public void produtoNaoCadastradoTeste() throws Exception {

        cadastraProduto();

        MockMultipartFile file
                = new MockMultipartFile(
                "imagens",
                "hello.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mvc.perform(multipart("/mercadolivre/produtos/1/imagens").file(file))
                .andExpect(status().isBadRequest());
    }

}
