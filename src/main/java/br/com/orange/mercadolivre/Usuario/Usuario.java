package br.com.orange.mercadolivre.Usuario;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Entity
@Table(name = "usuario", uniqueConstraints = {@UniqueConstraint(columnNames = {"email","id"})})
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Size(min = 6)
    private String senha;
    @NotNull
    private LocalDateTime registro = LocalDateTime.now(ZoneId.systemDefault());

    @Deprecated
    public Usuario() {
    }

    /**
    * @param email string no formato de email
    * @param senha string em texto limpo
    */

    public Usuario(@NotBlank @Email String email, @NotBlank @Size(min = 6) String senha) {

        // princípios de programação defenciva

        Assert.isTrue(StringUtils.hasLength(email), "email não pode ser em branco");
        Assert.isTrue(StringUtils.hasLength(senha), "senha não pode ser em branco");
        Assert.isTrue(senha.length() >= 6, "senha não pode ser menor que 6 caracteres");

        this.email = email;
        this.senha = new BCryptPasswordEncoder().encode(senha);
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", registro=" + registro +
                '}';
    }
}
