package com.ifma.transportadora.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.ifma.transportadora.modelo.Cliente;
import com.ifma.transportadora.repositorio.ClienteRepository;

public class TesteCliente {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab04");
        EntityManager em = emf.createEntityManager();

        // Teste para a entidade Cliente
        ClienteRepository clienteRepository= new ClienteRepository(em);
        
        Cliente cliente = new Cliente();
        cliente.setNome("João");
        cliente.setEndereco("Rua A, 123");
        cliente.setTelefone("(98)91234-5678");

        clienteRepository.salvarCliente(cliente);

        Cliente clienteRecuperado = clienteRepository.buscarCliente(cliente.getCodigoCliente());
        System.out.println("Cliente Recuperado: " + clienteRecuperado.getNome());

        em.close();
        emf.close();
    }
}
