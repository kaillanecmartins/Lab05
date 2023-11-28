package com.ifma.locadora.modelo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Console implements EntidadeBase{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private BigDecimal precoPorHora;

    @ManyToMany
    @JoinTable(
      name = "console_acessorio", 
      joinColumns = @JoinColumn(name = "console_id"), 
      inverseJoinColumns = @JoinColumn(name = "acessorio_id"))
    private Collection<Acessorio> acessorios;

    @OneToMany(mappedBy = "console")
    private Collection<UtilizacaoDoConsolePeloCliente> utilizacoes;
}
