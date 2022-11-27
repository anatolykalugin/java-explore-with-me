package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByIdAndState(Long id, State state);

    Event findByIdAndInitiatorId(Long id, Long initiatorId);

    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);
}
