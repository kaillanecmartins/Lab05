package main.java.com.ifma.locadora.repositorio;

import main.java.com.ifma.locadora.modelo.Cliente;
import main.java.com.ifma.locadora.repositorio.DAOGenerico;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ClienteRepository{

    private final EntityManager manager;
    private final DAOGenerico<Cliente> daoGenerico;

    public ClienteRepository(EntityManager manager){
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Cliente salvaOuAtualiza(Cliente cliente) {
		return daoGenerico.salvaOuAtualiza(cliente);
	}

	public Cliente buscaPor(Integer id) {
		return daoGenerico.buscaPorId(Cliente.class, id);
	}

    public List<Cliente> buscaPor(String nome) {
        return this.manager.createQuery("from Cliente " +
                "where upper(nome) like :nome", Cliente.class)
                .setParameter("nome", nome.toUpperCase() + "%")
                .getResultList();
    }

    public void remove(Cliente cliente) {
        daoGenerico.remove(cliente );
    }

}