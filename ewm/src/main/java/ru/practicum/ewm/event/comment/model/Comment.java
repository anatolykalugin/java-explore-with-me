package ru.practicum.ewm.event.comment.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "comment_text")
    String text;
    @ManyToOne
    @JoinColumn(name = "event", nullable = false)
    Event event;
    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    User author;
    @Column(name = "published")
    LocalDateTime published;
}
