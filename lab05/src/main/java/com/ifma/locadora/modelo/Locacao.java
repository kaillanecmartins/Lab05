package main.java.com.ifma.locadora.modelo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collection;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Locacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDate data;

    @OneToMany(mappedBy = "locacao")
    private Collection<ItemLocacao> itemLocacao;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
