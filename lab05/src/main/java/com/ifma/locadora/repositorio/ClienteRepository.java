package com.ifma.transportadora.repositorio;

import com.ifma.transportadora.modelo.Cliente;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ClienteRepository{

    private final EntityManager manager;

    public ClienteRepository(EntityManager manager){
        this.manager = manager;
    }

    public void salvarCliente(Cliente cliente){
        manager.getTransaction().begin();
        manager.persist(cliente);
        manager.getTransaction().commit();
    }

    public Cliente buscarCliente(Integer codigoCliente) {
        return manager.find(Cliente.class, codigoCliente);
    }

    public void atualizaCliente(Cliente cliente){
        EntityTransaction transaction = manager.getTransaction();
        try {
            transaction.begin();
            Cliente clienteAtualizado = manager.find(Cliente.class, cliente.getCodigoCliente());
            if (clienteAtualizado != null) {
                clienteAtualizado.setNome(cliente.getNome());
                clienteAtualizado.setEndereco(cliente.getEndereco());
                clienteAtualizado.setTelefone(cliente.getTelefone());
                manager.merge(clienteAtualizado);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); 
        }
    }
    

}