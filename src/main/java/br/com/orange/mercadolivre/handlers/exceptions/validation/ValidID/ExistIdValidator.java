package br.com.orange.mercadolivre.handlers.exceptions.validation.ValidID;

import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistIdValidator implements ConstraintValidator<ExistId, Object> {

    private  String domainAttribute;
    private Class<?> klass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistId params) {
        domainAttribute = params.fieldName();
        klass = params.domainClass();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if(value == null){
            return  true;
        }

        Query query = manager.createQuery( "select 1 from "+klass.getName()+" where "+domainAttribute+ "=:value");
        query.setParameter("value", value);

        List<?> list = query.getResultList();
        Assert.isTrue(list.size() <=1,"no results for id "+value );
        return !list.isEmpty();
    }
}