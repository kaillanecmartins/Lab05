package com.ifma.locadora.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ifma.locadora.modelo.Cliente;

import java.math.BigDecimal;
import java.util.Arrays;

import com.ifma.locadora.modelo.ItemLocacao;
import com.ifma.locadora.modelo.JogoPlataforma;
import com.ifma.locadora.modelo.Jogo;
import com.ifma.locadora.repositorio.LocacaoRepository;

public class TesteLocacao {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab05_test");
        EntityManager em = emf.createEntityManager();
        
        try {
            LocacaoRepository locacaoRepository = new LocacaoRepository(em);

            // Criando um cliente para os testes
            Cliente cliente = new Cliente();
            cliente.setNome("João");
            cliente.setEmail("joao@example.com");
            cliente.setTelefone("123456789");
            cliente.setSenha("senha123");

            // Criando um jogo para os testes
            Jogo jogo = new Jogo();
            jogo.setTitulo("Jogo teste");

            JogoPlataforma jogoPlataforma = new JogoPlataforma();
            jogoPlataforma.setJogo(jogo);
            jogoPlataforma.setPreco(BigDecimal.TEN);

            // Criando um item de locação com o jogo
            ItemLocacao itemLocacao = new ItemLocacao();
            itemLocacao.setJogoDePlataforma(jogoPlataforma);
            itemLocacao.setDias(3);

            // Testando a realização de locação de jogos
            locacaoRepository.realizarLocacao(cliente, Arrays.asList(itemLocacao));

        } finally {
            em.close();
            emf.close();
        }
    }
}
