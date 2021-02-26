package br.com.orange.mercadolivre.ImagemTeste;

import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.UtilTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc

public class UsuarioNaoAutorizadoTeste {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UtilTest utilTest;

    @PersistenceContext
    private EntityManager manager;


    //  Tenho minhas dúvidas se esse teste funciona de fato!
    @Test
    @Transactional
    @WithMockUser(username = "manu@email.com", password = "123456")
    public void usuarioCadastradoMasNaoDonoDoProdutoTeste() throws Exception {
        utilTest.limpaBanco();
        utilTest.populaBanco();

        // Salva o usuário manu no banco

        Usuario usuario = new Usuario("manu@email.com", "123456");
        manager.persist(usuario);

        MockMultipartFile file
                = new MockMultipartFile(
                "imagens",
                "hello.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mockMvc.perform(multipart("/mercadolivre/produtos/1/imagens").file(file))
                .andExpect(status().isForbidden());
    }
}
