package main.java.com.ifma.locadora.modelo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemLocacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int dias;
    private int quantidade;

    @ManyToOne
    @JoinColumn(name = "locacao_id")
    private Locacao locacao;

    @ManyToOne
    @JoinColumn(name = "jogodeplataforma_id")
    private JogoDePlataforma jogoDePlataforma;
}
