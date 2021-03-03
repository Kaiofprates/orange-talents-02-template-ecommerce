package br.com.orange.mercadolivre.ImagemTeste;

import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.UtilTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc

public class UsuarioNaoAutorizadoTeste {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Testa a tentativa de cadastro de imagens em um produto que está cadastrado por outro usuário")
    @Transactional
    @WithMockUser(username = "manuel@email.com", password = "123456")
    public void usuarioCadastradoMasNaoDonoDoProdutoTeste() throws Exception {

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
