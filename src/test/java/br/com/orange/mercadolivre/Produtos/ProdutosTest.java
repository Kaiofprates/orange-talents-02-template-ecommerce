package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Categoria.Categoria;
import br.com.orange.mercadolivre.Usuario.Usuario;
import br.com.orange.mercadolivre.Usuario.UsuarioRepository;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ProdutosTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private UsuarioRepository repository;

    private  Usuario cadastraProduto(){
        Usuario usuario = new Usuario("manuel@email.com","123456");
        Categoria categoria = new Categoria("Informática");
        manager.persist(usuario);
        manager.persist(categoria);
        return usuario;
    }


    @Test
    @Transactional
    @WithMockUser(username = "manuel@email.com",password = "123456")
    public void cadastroDeProdutoPorUsuarioTeste() throws Exception {
        /*
        * Esse teste segue os seguintes passos:
        *  1 - Cria um usuário
        *  2 - Cria um categoria
        *  3 - Testa a passagem do token e compara o username autorizado com o passado
        *  4 - Testa o cadastro de um produto 
        */

        cadastraProduto();
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = repository.findByEmail(a.getName());
        Assertions.assertEquals(usuario.getEmail(),a.getName());

        String request = "{\n" +
                "    \"nome\" : \"Tenis\",\n" +
                "    \"valor\" : 1089.0,\n" +
                "    \"descricao\" : \"Adidas usado \",\n" +
                "    \"quantidade\" : 1,\n" +
                "    \"idCategoria\" : 1,\n" +
                "    \"caracteristicas\" : [\n" +
                "        {\"nome\" : \"Marca\",\"descricao\" : \"Adidas\"} ,\n" +
                "        { \"nome\" : \"Cor \" , \"descricao\" : \"Branco\"},\n" +
                "        {\"nome\" : \"Desconto\", \"descricao\": \"10% a prazo\"}    \n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

    }

    @Test
    @Transactional
    @WithMockUser(username = "manuel@email.com",password = "123456")
    public void idCategoriaInvalidoTeste() throws Exception {

        cadastraProduto();
        Authentication a = SecurityContextHolder.getContext().getAuthentication();
        Usuario usuario = repository.findByEmail(a.getName());
        Assertions.assertEquals(usuario.getEmail(),a.getName());

        String request = "{\n" +
                "    \"nome\" : \"Tenis\",\n" +
                "    \"valor\" : 1089.0,\n" +
                "    \"descricao\" : \"Adidas usado \",\n" +
                "    \"quantidade\" : 1,\n" +
                "    \"idCategoria\" : 3,\n" +
                "    \"caracteristicas\" : [\n" +
                "        {\"nome\" : \"Marca\",\"descricao\" : \"Adidas\"} ,\n" +
                "        { \"nome\" : \"Cor \" , \"descricao\" : \"Branco\"},\n" +
                "        {\"nome\" : \"Desconto\", \"descricao\": \"10% a prazo\"}    \n" +
                "    ]\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/produtos")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andExpect(jsonPath("$[0].error").value("Id not found"))
                .andDo(MockMvcResultHandlers.print());

    }


}
