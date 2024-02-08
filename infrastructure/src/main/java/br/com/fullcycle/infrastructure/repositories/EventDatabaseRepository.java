package br.com.fullcycle.infrastructure.repositories;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.fullcycle.domain.DomainEvent;
import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.infrastructure.jpa.entities.EventEntity;
import br.com.fullcycle.infrastructure.jpa.entities.OutboxEntity;
import br.com.fullcycle.infrastructure.jpa.repositories.EventJpaRepository;
import br.com.fullcycle.infrastructure.jpa.repositories.OutboxJpaRepository;

//Interface Adapter
//Adapta o que vem do domínio para o Driven/Secondary Actor
@Component
public class EventDatabaseRepository implements EventRepository {

    private final EventJpaRepository eventJpaRepository;
    private final OutboxJpaRepository outboxJpaRepository;
    private final ObjectMapper objectMapper;

    public EventDatabaseRepository(
            final EventJpaRepository eventJpaRepository,
            final OutboxJpaRepository outboxJpaRepository,
            final ObjectMapper objectMapper
    ) {
        this.eventJpaRepository = Objects.requireNonNull(eventJpaRepository);
        this.outboxJpaRepository = Objects.requireNonNull(outboxJpaRepository);
        this.objectMapper = Objects.requireNonNull(objectMapper);
    }
    @Override
    public Optional<Event> eventOfId(final EventId eventId) {
        return this.eventJpaRepository.findById(UUID.fromString(eventId.value()))
                .map(EventEntity::toEvent);
    }

    @Override
    @Transactional
    public Event create(final Event event) {
        return save(event);
    }

    @Override
    @Transactional
    public Event update(Event event) {
        return save(event);
    }

    @Override
    public void deleteAll() {
        this.eventJpaRepository.deleteAll();
    }

    private Event save(Event event) {
        this.outboxJpaRepository.saveAll(
                event.allDomainEvents().stream()
                        .map(it -> OutboxEntity.of(it, this::toJson))
                        .toList()
        );
        return this.eventJpaRepository.save(EventEntity.of(event)).toEvent();
    }

    private String toJson(final DomainEvent domainEvent) {
        try {
            return this.objectMapper.writeValueAsString(domainEvent);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}