package com.vtduarte.pizzahub.database.repository;

import com.vtduarte.pizzahub.database.model.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, String> {

    Optional<EnderecoEntity> findByCep(String cep);
}
