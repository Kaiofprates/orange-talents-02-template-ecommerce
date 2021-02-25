package br.com.orange.mercadolivre.ImagemTeste;

import br.com.orange.mercadolivre.Categoria.Categoria;
import br.com.orange.mercadolivre.Produtos.Caracteristica;
import br.com.orange.mercadolivre.Produtos.CaracteristicaRequest;
import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.security.AuthUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ImagemTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthUtils authUtils;

    @PersistenceContext
    private EntityManager manager;

    private void populaBanco() {
        Usuario usuario = new Usuario("manuel@email.com", "123456");
        Categoria categoria = new Categoria("Informática");

        manager.persist(categoria);
        manager.persist(usuario);

        /**
         * Depois eu refatoro isso!
         */

        CaracteristicaRequest caracteristica1 = new CaracteristicaRequest("Marca", "Adidas");
        CaracteristicaRequest caracteristica2 = new CaracteristicaRequest("Cor", "Branco");
        CaracteristicaRequest caracteristica3 = new CaracteristicaRequest("Tamanho", "Médio");

        List<CaracteristicaRequest> caracteristicas = new ArrayList<>();
        caracteristicas.add(caracteristica1);
        caracteristicas.add(caracteristica2);
        caracteristicas.add(caracteristica3);

        Produto produto = new Produto("Tenis", 2, "um tenis legal!", BigDecimal.TEN, categoria, usuario, caracteristicas);

        manager.persist(produto);

    }

    @Test
    @Transactional
    @WithMockUser(username = "manuel@email.com", password = "123456")
    public void enviaImagemComSucesso() throws Exception {

        populaBanco();

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


    @Test
    @Transactional
    @WithMockUser(username = "manu@email.com", password = "123456")
    public void usuarioNaoAutorizadoTest() throws Exception {
        populaBanco();
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
            Assert.fail();
        }

    }

    //  Tenho minhas dúvidas se esse teste funciona de fato!
    @Test
    @Transactional
    @WithMockUser(username = "manu@email.com", password = "123456")
    public void usuarioCadastradoMasNaoDonoDoProdutoTeste() throws Exception {
        populaBanco();

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
