package br.com.orange.mercadolivre;

import br.com.orange.mercadolivre.Categoria.Categoria;
import br.com.orange.mercadolivre.Produtos.CaracteristicaRequest;
import br.com.orange.mercadolivre.Produtos.Produto;
import br.com.orange.mercadolivre.Usuario.Usuario;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Component
public class UtilTest {

    @PersistenceContext
    private EntityManager manager;

    @Transactional
    public void populaBanco() {
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

    @Transactional
    public void limpaBanco(){
        Usuario usuario = manager.find(Usuario.class,1L);
        Categoria categoria = manager.find(Categoria.class,1L);
        Produto produto = manager.find(Produto.class,1L);

        if(usuario != null || categoria != null || produto != null) {
            manager.remove(usuario);
            manager.remove(categoria);
            manager.remove(produto);
        }

    }




}
