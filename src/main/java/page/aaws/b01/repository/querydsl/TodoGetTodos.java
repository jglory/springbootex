package page.aaws.b01.repository.querydsl;

import java.util.List;

import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.dto.PageRequestDto;

public interface TodoGetTodos {
    List<TodoEntity> getTodos(PageRequestDto pageRequestDto);
}
