package br.com.orange.mercadolivre.Usuario;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc

public class UsuarioTest {

    @Autowired
    private MockMvc mockMvc;

    @PersistenceContext
    private EntityManager manager;


    @Autowired
    private UsuarioRepository repository;


    @Transactional
    private void populaBanco(){
        Usuario usuario = new Usuario("johndoe@email.com","123456");
        manager.persist(usuario);
    }


    String usuarioRequest = "{\n" +
            "    \"email\" : \"email@email.br\",\n" +
            "    \"senha\" : \"123456\"\n" +
            "}";

    @Test
    @WithMockUser
    @DisplayName("Deveria lidar com o sucesso no cadastro de usuário")

    public void criaUsuarioTeste() throws  Exception{

        /*
        * Nesse desafio seguiremos a risca todas as especificações
        * retornaremos apenas o status code 200 e não mais um objeto!
        */
         mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/usuarios")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(this.usuarioRequest)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
    }
    @Test
    @WithMockUser
    @DisplayName("Deveria lidar com o email duplicado no banco de dados")
    @Transactional
    public void emailDuplicadoTeste() throws  Exception{

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/usuarios")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.usuarioRequest)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andExpect(jsonPath("$[0].error").value("duplicate values"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser
    public void emailVazioTeste() throws  Exception{

        String usuarioNullouVazio = "{\n" +
                "    \"email\" : \"\",\n" +
                "    \"senha\" : \"123456\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/usuarios")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(usuarioNullouVazio)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andExpect(jsonPath("$[0].error").value("O campo email não pode estar vazio"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser
    @DisplayName("Deveria lidar com login (email) com formato inválido")
    public void emailFormatoInvalidoTeste() throws  Exception{

        String emailMalFormatado = "{\n" +
                "    \"email\" : \"qualquer.comemail\",\n" +
                "    \"senha\" : \"123456\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/usuarios")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(emailMalFormatado)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andExpect(jsonPath("$[0].error").value("O campo email deve ter um formato válido"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @WithMockUser
    @DisplayName("Deveria lidar com a senha branca ou nula")
    public void senhaBrancaOuNulaTeste() throws  Exception{

        String senhaBrancaOuNula = "{\n" +
                "    \"email\" : \"john@email.com\",\n" +
                "    \"senha\" : \"\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/usuarios")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(senhaBrancaOuNula)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    @DisplayName("Deveria lidar com uma senha com o tamanho menor que 6 caracteres")
    @WithMockUser
    public void senhaSemTamanhoMinimo() throws  Exception{

        String senhaMenor = "{\n" +
                "    \"email\" : \"john@email.com\",\n" +
                "    \"senha\" : \"12345\"\n" +
                "}";

        mockMvc.perform(MockMvcRequestBuilders.post("/mercadolivre/usuarios")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(senhaMenor)
        ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$[0].field").isString())
                .andExpect(jsonPath("$[0].status").isNumber())
                .andExpect(jsonPath("$[0].error").value("O campo senha deve ter no mínimo 6 caracteres"))
                .andDo(MockMvcResultHandlers.print());
    }

}


