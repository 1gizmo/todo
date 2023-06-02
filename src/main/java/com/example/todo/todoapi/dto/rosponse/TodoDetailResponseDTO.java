package com.example.todo.todoapi.dto.rosponse;

import com.example.todo.todoapi.entity.Todo;
import lombok.*;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoDetailResponseDTO {


    private String id;
    private String title;
    private boolean done;

    //엔터티를 DTO로 변환
    public TodoDetailResponseDTO(Todo todo){
        this.id = todo.getTodoId();
        this.title = todo.getTitle();
        this.done = todo.isDone();
    }
}
