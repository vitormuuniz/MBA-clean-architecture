package br.com.fullcycle.infrastructure.repositories;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.fullcycle.domain.event.Event;
import br.com.fullcycle.domain.event.EventId;
import br.com.fullcycle.domain.event.EventRepository;
import br.com.fullcycle.infrastructure.jpa.entities.EventEntity;
import br.com.fullcycle.infrastructure.jpa.repositories.EventJpaRepository;

//Interface Adapter
//Adapta o que vem do domínio para o Driven/Secondary Actor
@Component
public class EventDatabaseRepository implements EventRepository {

    private final EventJpaRepository eventJpaRepository;

    public EventDatabaseRepository(final EventJpaRepository eventJpaRepository) {
        this.eventJpaRepository = Objects.requireNonNull(eventJpaRepository);
    }
    @Override
    public Optional<Event> eventOfId(final EventId eventId) {
        return this.eventJpaRepository.findById(UUID.fromString(eventId.value()))
                .map(EventEntity::toEvent);
    }

    @Override
    @Transactional
    public Event create(final Event event) {
        return this.eventJpaRepository.save(EventEntity.of(event)).toEvent();
    }

    @Override
    @Transactional
    public Event update(Event event) {
        return this.eventJpaRepository.save(EventEntity.of(event)).toEvent();
    }

    @Override
    public void deleteAll() {
        this.eventJpaRepository.deleteAll();
    }
}