package com.ifma.locadora.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ifma.locadora.repositorio.JogoRepository;
import com.ifma.locadora.modelo.Jogo;

public class TesteJogo {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab05_test");
        EntityManager em = emf.createEntityManager();

        JogoRepository jogoRepository = new JogoRepository(em);

        Jogo jogo = new Jogo();
        jogo.setTitulo("The King of Fighters");
        
        jogoRepository.salvaOuAtualiza(jogo);

        Jogo pesquisaJogo = jogoRepository.buscaPor(jogo.getId());
        System.out.println("Jogo pesquisado: " + pesquisaJogo.getTitulo());

        em.close();
        emf.close();
    }
}
