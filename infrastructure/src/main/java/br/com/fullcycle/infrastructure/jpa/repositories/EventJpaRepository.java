package br.com.fullcycle.infrastructure.jpa.repositories;

import java.util.UUID;

import br.com.fullcycle.infrastructure.jpa.entities.EventEntity;

import org.springframework.data.repository.CrudRepository;

public interface EventJpaRepository extends CrudRepository<EventEntity, UUID> {}
