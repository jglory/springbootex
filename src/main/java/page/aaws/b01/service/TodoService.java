package page.aaws.b01.service;

import page.aaws.b01.dto.TodoDto;

public interface TodoService {
    Long addNewTodo(TodoDto dto);

    TodoDto getTodo(Long id);

    void updateTodo(TodoDto dto);

    void deleteTodo(Long id);
}
