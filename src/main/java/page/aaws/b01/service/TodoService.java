package page.aaws.b01.service;

import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.dto.PageDto;
import page.aaws.b01.dto.PageRequestDto;

public interface TodoService {
    TodoDto addNewTodo(TodoDto dto);

    TodoDto getTodo(Long id);

    PageDto<TodoDto> getTodosByPage(PageRequestDto pageRequestDto);

    TodoDto updateTodo(TodoDto dto);

    Long deleteTodo(Long id);
}
