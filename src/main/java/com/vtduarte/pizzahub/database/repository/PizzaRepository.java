package com.vtduarte.pizzahub.database.repository;

import com.vtduarte.pizzahub.database.model.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PizzaRepository extends JpaRepository<PizzaEntity, Long> {
}
