package main.java.com.ifma.locadora.repositorio;

import main.java.com.ifma.locadora.modelo.EntidadeBase;
import javax.persistence.EntityManager;
import java.util.Objects;

public class DAOGenerico<T extends EntidadeBase> {

    private final EntityManager manager;

    DAOGenerico(EntityManager manager) {
        this.manager = manager;
    }

    T buscaPorId(Class<T> classe, Integer id) {
        return manager.find(classe, id);
    }

    T salvaOuAtualiza(T objeto) {
        if (Objects.isNull(objeto.getId())) {
            this.manager.persist(objeto);
        } else {
            objeto = this.manager.merge(objeto);
        }

        return objeto;
    }

    void remove(T objeto) {
        manager.remove(objeto);
        manager.flush();
    }
}
