package com.vtduarte.pizzahub.database.model;

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

    private LocalDateTime horario = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;
}
