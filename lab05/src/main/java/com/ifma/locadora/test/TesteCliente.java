package com.ifma.locadora.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ifma.locadora.modelo.Cliente;
import com.ifma.locadora.repositorio.ClienteRepository;

public class TesteCliente {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab05_test");
        EntityManager em = emf.createEntityManager();

        // Teste para a entidade Cliente
        ClienteRepository clienteRepository= new ClienteRepository(em);
        
        Cliente cliente = new Cliente();
        cliente.setNome("Josivaldo Leite");
        cliente.setEmail("josivaldo@example.com");
        cliente.setTelefone("987654321");
        cliente.setSenha("123senha");
        

        clienteRepository.salvaOuAtualiza(cliente);

        Cliente clienteRecuperado = clienteRepository.buscaPor(cliente.getId());
        System.out.println("Cliente Recuperado: " + clienteRecuperado.getNome());

        em.close();
        emf.close();
    }
}
