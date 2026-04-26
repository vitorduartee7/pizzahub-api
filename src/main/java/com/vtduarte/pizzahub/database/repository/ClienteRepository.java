package com.vtduarte.pizzahub.database.repository;

import com.vtduarte.pizzahub.database.model.ClienteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {

    Optional<ClienteEntity> findByEmail(String email);

    Optional<ClienteEntity> findByTelefone(String telefone);
}
