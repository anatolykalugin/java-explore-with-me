package ru.practicum.ewm.request.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "requests")
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    @ManyToOne
    @JoinColumn(name = "event")
    Event event;
    @ManyToOne
    @JoinColumn(name = "author")
    User author;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    State state;
    @Column(name = "created")
    LocalDateTime created;
}
