package com.vtduarte.pizzahub.utils;

import com.vtduarte.pizzahub.database.model.ItemPedidoEntity;

import java.util.List;

public class TempoPedidoUtils {

    public static Integer calcularTempo(List<ItemPedidoEntity> itens) {

        int tempoBase = 20;

        if (itens == null || itens.isEmpty()) {
            return tempoBase;
        }

        for (ItemPedidoEntity item : itens) {

            // +5 min por pizza
            tempoBase += (item.getQuantidade() * 5);

            // ajuste por tamanho
            tempoBase += switch (item.getTamanhoPizza()) {
                case P -> 0;
                case M -> 2 * item.getQuantidade();
                case G -> 5 * item.getQuantidade();
            };
        }

        return tempoBase;
    }
}
