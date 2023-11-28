package main.java.com.ifma.locadora.repositorio;

import main.java.com.ifma.locadora.modelo.Console;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ConsoleRepository {
    private final EntityManager manager;
    private final DAOGenerico<Console> daoGenerico;

    public ConsoleRepository(EntityManager manager){
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Console salvaOuAtualiza(Console console) {
		return daoGenerico.salvaOuAtualiza(console);
	}

	public Console buscaPor(Integer id) {
		return daoGenerico.buscaPorId(Console.class, id);
	}

    public List<Console> buscaPor(String nome) {
        return this.manager.createQuery("from Console " +
                "where upper(nome) like :nome", Console.class)
                .setParameter("nome", nome.toUpperCase() + "%")
                .getResultList();
    }
    public void remove(Console console) {
        daoGenerico.remove(console);
    }
}
