package com.ifma.transportadora.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.ifma.transportadora.modelo.Frete;
import com.ifma.transportadora.repositorio.FreteRepository;
import com.ifma.transportadora.modelo.Cliente;
import com.ifma.transportadora.modelo.Cidade;

public class TesteFrete {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("lab04_test");
        EntityManager em = emf.createEntityManager();

        FreteRepository freteRepository = new FreteRepository(em);

        Cliente cliente = new Cliente();
        cliente.setEndereco("Passeio das Palmeiras, 678");
        cliente.setNome("Carolina Ferreira");
        cliente.setTelefone("(099)92222-2222");
        
        Cidade cidadeDestino = new Cidade();
        cidadeDestino.setNome("Teresina");
        cidadeDestino.setUF("PI");
        cidadeDestino.setTaxa(14.0f);
        
        Frete frete = new Frete();
        frete.setDescricao("Entrega rápida");
        frete.setPeso(10.0f);
        frete.setValor(freteRepository.calcularValorFrete(frete)); 
        frete.setCidadeDestino(cidadeDestino);
        frete.setCliente(cliente);

        freteRepository.salvarFrete(frete);

        Frete freteRecuperado = freteRepository.buscarFrete(frete.getCodigoFrete());
        System.out.println("Frete Recuperado: " + freteRecuperado.getDescricao());

        em.close();
        emf.close();
    }
}
