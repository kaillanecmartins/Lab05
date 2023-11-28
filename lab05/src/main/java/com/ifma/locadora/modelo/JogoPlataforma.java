package main.java.com.ifma.locadora.modelo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class JogoPlataforma {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "plataforma_id")
    private Plataforma plataforma;

    @ManyToOne
    @JoinColumn(name = "jogo_id")
    private Jogo jogo;

    @OneToMany(mappedBy = "jogoDePlataforma")
    private Collection<ItemLocacao> itemLocacao;
}
