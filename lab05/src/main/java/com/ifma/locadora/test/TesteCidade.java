package com.ifma.transportadora.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ifma.transportadora.modelo.Cidade;
import com.ifma.transportadora.repositorio.CidadeRepository;

public class TesteCidade {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab04_test");
        EntityManager em = emf.createEntityManager();

        CidadeRepository cidadeRepository = new CidadeRepository(em);

        Cidade cidade = new Cidade();
        cidade.setNome("Belém");
        cidade.setUF("PA");
        cidade.setTaxa(5.0f);

        cidadeRepository.salvarCidade(cidade);

        Cidade cidadeRecuperada = cidadeRepository.buscarCidade(cidade.getCodigoCidade());
        System.out.println("Cidade Recuperada: " + cidadeRecuperada.getNome());

        em.close();
        emf.close();
    }
}
