package com.vtduarte.pizzahub.database.enums;

public enum StatusPedidoEnum {
    CRIADO,
    EM_PREPARO,
    SAIU_PARA_ENTREGA,
    ENTREGUE,
    CANCELADO;

    public boolean podeIrPara(StatusPedidoEnum novoStatus) {

        if (this == CANCELADO || this == ENTREGUE) {
            return false;
        }

        return switch (this) {
            case CRIADO -> novoStatus == EM_PREPARO || novoStatus == CANCELADO;
            case EM_PREPARO -> novoStatus == SAIU_PARA_ENTREGA || novoStatus == CANCELADO;
            case SAIU_PARA_ENTREGA -> novoStatus == ENTREGUE;
            default -> false;
        };
    }
}
