package br.com.fullcycle.infrastructure.jpa.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import br.com.fullcycle.infrastructure.jpa.entities.OutboxEntity;

public interface OutboxJpaRepository extends CrudRepository<OutboxEntity, UUID> {

    List<OutboxEntity> findAllByPublishedFalse();
}
