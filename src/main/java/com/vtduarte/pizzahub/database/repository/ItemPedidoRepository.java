package com.vtduarte.pizzahub.database.repository;

import com.vtduarte.pizzahub.database.model.ItemPedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPedidoRepository extends JpaRepository<ItemPedidoEntity, Long> {
}
