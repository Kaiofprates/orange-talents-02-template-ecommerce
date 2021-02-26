package br.com.orange.mercadolivre.ImagemTeste;

import br.com.orange.mercadolivre.UtilTest;
import br.com.orange.mercadolivre.security.AuthUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

@SpringBootTest
@AutoConfigureMockMvc
public class ImagemTest {

    /**
     *  REFATORAR
     */


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthUtils authUtils;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UtilTest utilTest;

    @Test
    @Transactional
    @WithMockUser(username = "manu@email.com", password = "123456")
    public void usuarioNaoAutorizadoTest() throws Exception {
        utilTest.populaBanco();
        MockMultipartFile file
                = new MockMultipartFile(
                "imagens",
                "hello.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );
        try {
            mockMvc.perform(multipart("/mercadolivre/produtos/1/imagens").file(file));
        } catch (ResponseStatusException e) {
            Assertions.fail();
        }
    }

}