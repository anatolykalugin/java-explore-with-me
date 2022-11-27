package ru.practicum.ewm.event.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import ru.practicum.ewm.category.model.Category;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode
@ToString
@Table(name = "events")
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "annotation", nullable = false)
    String annotation;
    @Column(name = "title", nullable = false)
    String title;
    @Column(name = "description", nullable = false)
    String description;
    @Column(name = "state", nullable = false)
    @Enumerated(EnumType.STRING)
    State state = State.PENDING;
    @Column(name = "created")
    @CreationTimestamp
    LocalDateTime created;
    @Column(name = "published")
    LocalDateTime published;
    @Column(name = "start_date")
    LocalDateTime startDate;
    @JoinColumn(name = "initiator")
    @ManyToOne
    User initiator;
    @Column(name = "lat")
    Double lat;
    @Column(name = "lon")
    Double lon;
    @JoinColumn(name = "category")
    @ManyToOne
    Category category;
    @Column(name = "paid")
    Boolean paid;
    @Column(name = "moderation")
    Boolean moderation;
    @Column(name = "participant_limit")
    Integer participantLimit;
}
