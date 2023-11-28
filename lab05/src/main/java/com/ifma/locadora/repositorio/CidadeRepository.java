package com.ifma.transportadora.repositorio;

import com.ifma.transportadora.modelo.Cidade;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class CidadeRepository {
    private final EntityManager entityManager;

    public CidadeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void salvarCidade(Cidade cidade) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(cidade);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public Cidade buscarCidade(int id) {
        return entityManager.find(Cidade.class, id);
    }

    public void atualizarCidade(Cidade cidade) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(cidade);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

}
