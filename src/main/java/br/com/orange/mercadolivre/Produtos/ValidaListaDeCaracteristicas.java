package br.com.orange.mercadolivre.Produtos;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class ValidaListaDeCaracteristicas implements Validator {

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
        List<CaracteristicasRequest> caracteristicasList = request.getCaracteristicas();

        for(CaracteristicasRequest c : caracteristicasList){
            if(c.getNome() == null || c.getDescricao() == null){
                errors.rejectValue("caracteristicas","400", "Dados inválidos para a lista de caracteristicas");
            }
        }

        if(caracteristicasList == null || caracteristicasList.size() < 2 ){
            errors.rejectValue("caracteristicas","400", "Dados inválidos para a lista de caracteristicas");
        }

    }
}
