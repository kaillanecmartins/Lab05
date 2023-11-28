package main.java.com.ifma.locadora.repositorio;

import main.java.com.ifma.locadora.modelo.Plataforma;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class PlataformaRepository {
    private final EntityManager manager;
    private final DAOGenerico<Plataforma> daoGenerico;

    public PlataformaRepository(EntityManager manager){
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Plataforma salvaOuAtualiza(Plataforma plataforma) {
		return daoGenerico.salvaOuAtualiza(plataforma);
	}

	public Plataforma buscaPor(Integer id) {
		return daoGenerico.buscaPorId(Plataforma.class, id);
	}

    public List<Plataforma> buscaPor(String nome) {
        return this.manager.createQuery("from Plataforma " +
                "where upper(nome) like :nome", Plataforma.class)
                .setParameter("nome", nome.toUpperCase() + "%")
                .getResultList();
    }

    public void remove(Plataforma plataforma) {
        daoGenerico.remove(plataforma);
    }
}
