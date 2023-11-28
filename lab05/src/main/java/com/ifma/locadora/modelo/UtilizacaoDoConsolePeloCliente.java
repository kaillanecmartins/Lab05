package main.java.com.ifma.locadora.modelo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import com.ifma.locadora.modelo.Cliente;
import java.time.LocalDateTime;
import java.util.Collection;

@Getter @Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class UtilizacaoDoConsolePeloCliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime inicio;
    private LocalDateTime fim;

    @ManyToOne
    @JoinColumn(name = "console_id")
    private Console console;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
