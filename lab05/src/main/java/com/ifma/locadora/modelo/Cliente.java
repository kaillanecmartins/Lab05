package com.ifma.locadora.modelo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.util.Collection;
import javax.persistence.*;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Cliente implements EntidadeBase{

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;
    private String email;
    private String telefone;
    private String senha;

    @OneToMany(mappedBy = "cliente")
    private Collection<Locacao> locacoes;

    @OneToMany(mappedBy = "cliente")
    private Collection<UtilizacaoDoConsolePeloCliente> utilizacoes;

}