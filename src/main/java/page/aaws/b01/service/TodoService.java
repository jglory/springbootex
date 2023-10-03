package page.aaws.b01.service;

import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.dto.PageDto;
import page.aaws.b01.dto.PageRequestDto;

public interface TodoService {
    Long addNewTodo(TodoDto dto);

    TodoDto getTodo(Long id);

    PageDto<TodoDto> getTodosByPage(PageRequestDto pageRequestDto);

    void updateTodo(TodoDto dto);

    void deleteTodo(Long id);
}