package com.vtduarte.pizzahub.database.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vtduarte.pizzahub.database.enums.StatusPedidoEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "status_event")
public class StatusEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedidoEnum statusAntigo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPedidoEnum statusNovo;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime horario;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;

    @PrePersist
    public void prePersist() {
        if (this.horario == null) {
            this.horario = LocalDateTime.now();
        }
    }
}
