package br.com.fullcycle.infrastructure.jpa.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import br.com.fullcycle.infrastructure.jpa.entities.OutboxEntity;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;

public interface OutboxJpaRepository extends CrudRepository<OutboxEntity, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint( name = "jakarta.persistence.lock.timeout", value = "2")
    })
    List<OutboxEntity> findTop100ByPublishedFalse();
}
