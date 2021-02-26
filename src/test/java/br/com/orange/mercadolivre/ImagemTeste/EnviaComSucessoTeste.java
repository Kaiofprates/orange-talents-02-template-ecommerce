package br.com.orange.mercadolivre.ImagemTeste;

import br.com.orange.mercadolivre.UtilTest;
import br.com.orange.mercadolivre.security.AuthUtils;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EnviaComSucessoTeste {

    /**
     *   REFATORAR
     */


    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UtilTest utilTest;
    @Autowired
    private AuthUtils authUtils;

    @Test
    @WithMockUser(username = "manuel@email.com", password = "123456")
    public void enviaImagemComSucesso() throws Exception {

        utilTest.populaBanco();

        Assertions.assertNotNull(authUtils.checkUser().getEmail());
        MockMultipartFile file
                = new MockMultipartFile(
                "imagens",
                "hello.jpg",
                MediaType.TEXT_PLAIN_VALUE,
                "Hello, World!".getBytes()
        );

        mockMvc.perform(multipart("/mercadolivre/produtos/1/imagens").file(file))
                .andExpect(status().isOk());

    }


}
