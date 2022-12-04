package ru.practicum.ewm.event.comment.dto;

import org.springframework.stereotype.Component;
import ru.practicum.ewm.event.comment.model.Comment;
import ru.practicum.ewm.event.model.Event;
import ru.practicum.ewm.user.model.User;

@Component
public class CommentMapper {

    public static Comment toClass(CommentDto commentDto, Event event, User author) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .event(event)
                .author(author)
                .published(commentDto.getPublished())
                .build();
    }

    public static CommentDto toDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .event(comment.getEvent().getId())
                .author(comment.getAuthor().getId())
                .published(comment.getPublished())
                .build();
    }

}
