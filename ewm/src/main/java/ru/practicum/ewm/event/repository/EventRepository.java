package ru.practicum.ewm.event.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.event.model.State;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    Event findByIdAndState(Long id, State state);

    Event findByIdAndInitiatorId(Long id, Long initiatorId);

    List<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    List<Event> getByIdIn(List<Long> ids);

    @Query("SELECT e FROM Event e " +
            "WHERE ((:users) IS NULL OR e.initiator.id IN :users) " +
            "AND ((:states) IS NULL OR e.state IN :states) " +
            "AND ((:categories) IS NULL OR e.category.id IN :categories) " +
            "AND (e.eventDate >= :neededStart) " +
            "AND (DATE(:neededEnd) IS NULL) OR (e.eventDate <= :neededEnd)" +
            "ORDER BY e.id DESC")
    List<Event> getAdminEvents(List<Long> users, List<State> states, List<Long> categories,
                               LocalDateTime neededStart, LocalDateTime neededEnd, Pageable pageable);

    @Query("SELECT e FROM Event e " +
            "WHERE (LOWER(e.annotation) LIKE LOWER(CONCAT('%', :text, '%')) OR " +
            "LOWER(e.description) LIKE LOWER(CONCAT('%', :text, '%')))" +
            "AND ((:categoriesIds) IS NULL OR e.category.id IN :categoriesIds) " +
            "AND e.paid = :isPaid " +
            "AND e.eventDate BETWEEN :neededStart AND :neededEnd")
    List<Event> getPublicEvents(String text, List<Long> categoriesIds, LocalDateTime neededStart,
                                LocalDateTime neededEnd, Boolean isPaid, Pageable pageable);

}