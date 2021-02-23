package br.com.orange.mercadolivre.Produtos;

import br.com.orange.mercadolivre.Categoria.Categoria;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class ValidaCategoria implements Validator {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public boolean supports(Class<?> aClass) {
        return ProdutosRequest.class.isAssignableFrom(aClass);
    }
    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        ProdutosRequest request = (ProdutosRequest) target;
        Categoria categoria = manager.find(Categoria.class, request.getCategoria());
        if (categoria == null) {
            errors.rejectValue("categoria", "400", "Categoria não encontrada");
        }

    }
}
