package main.java.com.ifma.locadora.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import main.java.com.ifma.locadora.modelo.Cliente;
import main.java.com.ifma.locadora.repositorio.ClienteRepository;

public class TesteCliente {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab05_test");
        EntityManager em = emf.createEntityManager();

        // Teste para a entidade Cliente
        ClienteRepository clienteRepository= new ClienteRepository(em);
        
        Cliente cliente = new Cliente();
        cliente.setNome("Josivaldo Leite");
        

        clienteRepository.salvarCliente(cliente);

        Cliente clienteRecuperado = clienteRepository.buscarCliente(cliente.getCodigoCliente());
        System.out.println("Cliente Recuperado: " + clienteRecuperado.getNome());

        em.close();
        emf.close();
    }
}
