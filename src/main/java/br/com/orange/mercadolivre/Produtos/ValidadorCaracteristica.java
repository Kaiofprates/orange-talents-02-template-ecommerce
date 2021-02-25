package br.com.orange.mercadolivre.Produtos;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ValidadorCaracteristica implements Validator {


    @Override
    public boolean supports(Class<?> aClass) {
        return ProdutoRequest.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors()){
            return ;
        }
        ProdutoRequest request = (ProdutoRequest) target;
        Set<String> retornoCaracteristicasIquais = request.caracteristicasIguals();
        if(!retornoCaracteristicasIquais.isEmpty()){
            errors.rejectValue("caracteristicas", "400","Campo de caracteristicas duplicado");
        }

    }
}
