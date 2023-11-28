package main.java.com.ifma.locadora.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ifma.locadora.modelo.Cliente;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

import main.java.com.ifma.locadora.modelo.ItemLocacao;
import main.java.com.ifma.locadora.modelo.JogoPlataforma;
import main.java.com.ifma.locadora.modelo.Locacao;
import main.java.com.ifma.locadora.repositorio.LocacaoRepository;

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
            JogoPlataforma jogo = new JogoPlataforma();
            jogo.setTitulo("Jogo Teste");
            jogo.setPreco(BigDecimal.TEN);

            // Criando um item de locação com o jogo
            ItemLocacao itemLocacao = new ItemLocacao();
            itemLocacao.setJogoPlataforma(jogo);
            itemLocacao.setDias(3);

            // Testando a realização de locação de jogos
            locacaoRepository.realizarLocacao(cliente, Arrays.asList(itemLocacao));

            // Testando a verificação de disponibilidade de jogos
            LocalDate dataLocacao = LocalDate.now();
            boolean disponibilidadeJogos = locacaoRepository.verificarDisponibilidadeJogos(dataLocacao);
            System.out.println("Disponibilidade de jogos na data " + dataLocacao + ": " + disponibilidadeJogos);

            // Testando a verificação de disponibilidade de consoles
            boolean disponibilidadeConsoles = locacaoRepository.verificarDisponibilidadeConsole(dataLocacao);
            System.out.println("Disponibilidade de consoles na data " + dataLocacao + ": " + disponibilidadeConsoles);

            // Testando a realização de locação de consoles
            Console console = new Console();
            console.setNome("Console Teste");
            console.setPrecoPorHora(BigDecimal.valueOf(5));
            locacaoRepository.realizarLocacaoConsole(cliente, console, LocalDateTime.now(), LocalDateTime.now().plusHours(2));

        } finally {
            em.close();
            emf.close();
        }
    }
}
