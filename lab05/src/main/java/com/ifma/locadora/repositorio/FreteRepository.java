package com.ifma.transportadora.repositorio;

import com.ifma.transportadora.modelo.Cidade;
import com.ifma.transportadora.modelo.Cliente;
import com.ifma.transportadora.modelo.Frete;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class FreteRepository {
    private final EntityManager entityManager;

    public FreteRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void salvarFrete(Frete frete) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(frete);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Frete buscarFrete(int id) {
        return entityManager.find(Frete.class, id);
    }

    public void atualizarFrete(Frete frete) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(frete);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace(); 
        }
    }

    public Float calcularValorFrete(Frete frete) {
        Cidade cidadeDestino = frete.getCidadeDestino();
        Float taxaEntrega = cidadeDestino.getTaxa();
        Float valorFrete = frete.getPeso() * 10 + taxaEntrega;
        return valorFrete;
    }

    public List<Frete> listarFretesPorCliente(Cliente cliente) {
        TypedQuery<Frete> query = entityManager.createQuery("SELECT f FROM Frete f WHERE f.cliente = :cliente", Frete.class);
        query.setParameter("cliente", cliente);
        return query.getResultList();
    }
}
