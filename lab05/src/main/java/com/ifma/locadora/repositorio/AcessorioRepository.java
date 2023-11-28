package main.java.com.ifma.locadora.repositorio;

import main.java.com.ifma.locadora.modelo.Acessorio;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

public class AcessorioRepository {
    
    private final EntityManager manager;
    private final DAOGenerico<Acessorio> daoGenerico;

    public AcessorioRepository(EntityManager manager){
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Acessorio salvaOuAtualiza(Acessorio acessorio) {
		return daoGenerico.salvaOuAtualiza(acessorio);
	}

	public Acessorio buscaPor(Integer id) {
		return daoGenerico.buscaPorId(Acessorio.class, id);
	}

    public void remove(Acessorio acessorio) {
        daoGenerico.remove(acessorio);
    }
    public List<Acessorio> buscaPor(String nome) {
        return this.manager.createQuery("from Acessorio " +
                "where upper(nome) like :nome", Acessorio.class)
                .setParameter("nome", nome.toUpperCase() + "%")
                .getResultList();
    }
}
