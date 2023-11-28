package main.java.com.ifma.locadora.modelo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.util.Collection;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Jogo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulo;

    @OneToMany(mappedBy = "jogo")
    private Collection<JogoDePlataforma> jogoDePlataforma;
}
