package com.vtduarte.pizzahub.utils;

import com.vtduarte.pizzahub.database.enums.TamanhoPizzaEnum;

import java.math.BigDecimal;

public class PizzaPrecoUtils {

    public static BigDecimal aplicarTamanho(BigDecimal precoBase, TamanhoPizzaEnum tamanho) {

        return switch (tamanho) {
            case P -> precoBase;
            case M -> precoBase.multiply(BigDecimal.valueOf(1.3));
            case G -> precoBase.multiply(BigDecimal.valueOf(1.6));
        };
    }
}
