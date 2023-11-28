package com.ifma.locadora.repositorio;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.ifma.locadora.modelo.ItemLocacao;
import com.ifma.locadora.modelo.Locacao;
import com.ifma.locadora.modelo.Cliente;
import com.ifma.locadora.modelo.JogoPlataforma;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class LocacaoRepository{

    private final EntityManager manager;
    private DAOGenerico<Locacao> daoGenerico;


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
        locacao.setData(LocalDate.now());

        BigDecimal precoTotal = BigDecimal.ZERO;

        for (ItemLocacao itemLocacao : itensLocacao) {
            JogoPlataforma jogoPlataforma = itemLocacao.getJogoDePlataforma();

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

        locacao.setValorTotal(precoTotal);

        // Salva a locação no banco de dados
        manager.getTransaction().begin();
        manager.persist(locacao);
        manager.getTransaction().commit();
    }

    private boolean verificarDisponibilidadeJogo(int jogoPlataformaId, LocalDate data) {
        String jpql = "SELECT COUNT(l) FROM Locacao l " +
                      "JOIN l.itensLocacao il " +
                      "WHERE il.jogoPlataforma.id = :jogoId " +
                      "AND :data BETWEEN l.dataLocacao AND l.dataDevolucao";

        TypedQuery<Long> query = manager.createQuery(jpql, Long.class);
        query.setParameter("jogoId", jogoPlataformaId);
        query.setParameter("data", data);

        return query.getSingleResult() == 0;
    }

    private void atualizarDisponibilidadeJogo(int jogoPlataformaId, LocalDate dataDevolucao) {
        // Atualiza a data de devolução do jogo na tabela de Locacao
        String jpql = "UPDATE ItemLocacao il " +
                      "SET il.locacao.dataDevolucao = :dataDevolucao " +
                      "WHERE il.jogoPlataforma.id = :jogoId " +
                      "AND il.locacao.dataDevolucao IS NULL";

        manager.getTransaction().begin();
        manager.createQuery(jpql)
                .setParameter("jogoId", jogoPlataformaId)
                .setParameter("dataDevolucao", dataDevolucao)
                .executeUpdate();
        manager.getTransaction().commit();
    }

}
