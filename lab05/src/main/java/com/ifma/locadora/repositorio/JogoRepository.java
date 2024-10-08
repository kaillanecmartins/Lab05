package com.ifma.locadora.repositorio;

import com.ifma.locadora.modelo.Jogo;
import java.util.List;
import javax.persistence.EntityManager;

public class JogoRepository {
    private final EntityManager manager;
    private final DAOGenerico<Jogo> daoGenerico;

    public JogoRepository(EntityManager manager){
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Jogo salvaOuAtualiza(Jogo jogo) {
		return daoGenerico.salvaOuAtualiza(jogo);
	}

	public Jogo buscaPor(Integer id) {
		return daoGenerico.buscaPorId(Jogo.class, id);
	}

    public List<Jogo> buscaPor(String nome) {
        return this.manager.createQuery("from Jogo " +
                "where upper(titulo) like :titulo", Jogo.class)
                .setParameter("titulo", nome.toUpperCase() + "%")
                .getResultList();
    }

    public void remove(Jogo jogo) {
        daoGenerico.remove(jogo);
    }

}
