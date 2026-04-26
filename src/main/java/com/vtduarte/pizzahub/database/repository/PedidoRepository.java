package com.vtduarte.pizzahub.database.repository;

import com.vtduarte.pizzahub.database.enums.StatusPedidoEnum;
import com.vtduarte.pizzahub.database.model.PedidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoEntity, Long> {

    List<PedidoEntity> findByStatus(StatusPedidoEnum status);
}
