package main.java.com.ifma.locadora.repositorio;

import main.java.com.ifma.locadora.modelo.ItemLocacao;
import main.java.com.ifma.locadora.modelo.Locacao;
import main.java.com.ifma.locadora.repositorio.DAOGenerico;
import main.java.com.ifma.locadora.modelo.Acessorio;
import main.java.com.ifma.locadora.modelo.Cliente;
import main.java.com.ifma.locadora.modelo.JogoPlataforma;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class LocacaoRepository {
    private final EntityManager manager;
    private final DAOGenerico<Locacao> daoGenerico;

    public LocacaoRepository(EntityManager manager){
        this.manager = manager;
        this.daoGenerico = new DAOGenerico<>(manager);
    }

    public Locacao salvaOuAtualiza(Locacao locacao) {
		return daoGenerico.salvaOuAtualiza(locacao);
	}

	public Locacao buscaPor(Integer id) {
		return daoGenerico.buscaPorId(Locacao.class, id);
	}

    public void remove(Locacao locacao) {
        daoGenerico.remove(locacao);
    }

    public void realizarLocacao(Cliente cliente, List<ItemLocacao> itensLocacao) {
        Locacao locacao = new Locacao();
        locacao.setCliente(cliente);
        locacao.setDataLocacao(LocalDate.now());

        BigDecimal precoTotal = BigDecimal.ZERO;

        for (ItemLocacao itemLocacao : itensLocacao) {
            JogoPlataforma jogoPlataforma = itemLocacao.getJogoPlataforma();

            // Verifica se o jogo está disponível para locação na data atual
            if (!verificarDisponibilidadeJogo(jogoPlataforma.getId(), LocalDate.now())) {
                throw new RuntimeException("Jogo não disponível para locação na data atual.");
            }

            int diasLocacao = itemLocacao.getDias();
            BigDecimal precoJogo = jogoPlataforma.getPreco();

            // Calcula o preço total para este jogo
            BigDecimal precoJogoTotal = precoJogo.multiply(BigDecimal.valueOf(diasLocacao));
            precoTotal = precoTotal.add(precoJogoTotal);

            // Atualiza a disponibilidade do jogo
            atualizarDisponibilidadeJogo(jogoPlataforma.getId(), LocalDate.now().plusDays(diasLocacao));

            // Associa o item de locação à locação
            itemLocacao.setLocacao(locacao);
        }

        locacao.setPrecoTotal(precoTotal);

        // Salva a locação no banco de dados
        entityManager.getTransaction().begin();
        entityManager.persist(locacao);
        entityManager.getTransaction().commit();
    }

    private boolean verificarDisponibilidadeJogo(int jogoPlataformaId, LocalDate data) {
        // Verifica se há alguma locação para o jogo na data especificada
        String jpql = "SELECT COUNT(l) FROM Locacao l " +
                      "JOIN l.itensLocacao il " +
                      "WHERE il.jogoPlataforma.id = :jogoId " +
                      "AND :data BETWEEN l.dataLocacao AND l.dataDevolucao";

        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        query.setParameter("jogoId", jogoPlataformaId);
        query.setParameter("data", data);

        return query.getSingleResult() == 0;
    }

    private boolean verificarDisponibilidadeConsoles(LocalDate data) {
        // Verifica a disponibilidade dos consoles na data especificada
        String jpql = "SELECT COUNT(lc) FROM LocacaoConsole lc " +
                      "WHERE :data BETWEEN lc.inicio AND lc.fim";

        Long count = entityManager.createQuery(jpql, Long.class)
                .setParameter("data", data)
                .getSingleResult();

        return count == 0;
    }

    private void atualizarDisponibilidadeJogo(int jogoPlataformaId, LocalDate dataDevolucao) {
        // Atualiza a data de devolução do jogo na tabela de Locacao
        String jpql = "UPDATE ItemLocacao il " +
                      "SET il.locacao.dataDevolucao = :dataDevolucao " +
                      "WHERE il.jogoPlataforma.id = :jogoId " +
                      "AND il.locacao.dataDevolucao IS NULL";

        entityManager.getTransaction().begin();
        entityManager.createQuery(jpql)
                .setParameter("jogoId", jogoPlataformaId)
                .setParameter("dataDevolucao", dataDevolucao)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }

    //extra: Locação de Consoles

    public void realizarLocacaoConsole(Cliente cliente, Console console, LocalDateTime inicio, LocalDateTime fim) {
        // Verifica a disponibilidade do console
        if (!verificarDisponibilidadeConsole(console.getId(), inicio, fim)) {
            throw new RuntimeException("Console não disponível para locação no período especificado.");
        }

        // Calcula o valor total da locação com base no tempo de uso e preço por hora
        BigDecimal precoTotal = calcularPrecoLocacaoConsole(console, inicio, fim);

        // Associa os acessórios ao console
        List<Acessorio> acessorios = console.getAcessorios();

        // Cria a locação de console
        LocacaoConsole locacaoConsole = new LocacaoConsole();
        locacaoConsole.setCliente(cliente);
        locacaoConsole.setConsole(console);
        locacaoConsole.setInicio(inicio);
        locacaoConsole.setFim(fim);
        locacaoConsole.setPrecoTotal(precoTotal);

        // Associa os acessórios à locação de console
        locacaoConsole.setAcessorios(acessorios);

        // Atualiza a disponibilidade do console
        atualizarDisponibilidadeConsole(console.getId(), inicio, fim);

        // Salva a locação de console no banco de dados
        entityManager.getTransaction().begin();
        entityManager.persist(locacaoConsole);
        entityManager.getTransaction().commit();
    }

    //extra: verifica disponibilidade de Jogos e Consoles

    public boolean verificarDisponibilidade(LocalDate data) {
        return verificarDisponibilidadeJogo(data) && verificarDisponibilidadeConsoles(data);
    }

    public boolean verificarDisponibilidadeJogos(LocalDate data){
        return verificarDisponibilidadeJogo(data);
    }

    public boolean verificarDisponibilidadeConsole(LocalDate data){
        return verificarDisponibilidadeConsoles(data);
    }

}
