package com.vtduarte.pizzahub.database.model;

import com.vtduarte.pizzahub.database.enums.TamanhoPizzaEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item_pedido")
public class ItemPedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_pedido_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoEntity pedido;

    @OneToOne
    @JoinColumn(name = "pizza_id")
    private PizzaEntity pizza;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TamanhoPizzaEnum tamanhoPizza;

    @Column(nullable = false)
    private Integer quantidade;

    private String observacao;

    @Column(nullable = false)
    private BigDecimal precoUnitario;

    @Column(nullable = false)
    private BigDecimal subtotal;
}
