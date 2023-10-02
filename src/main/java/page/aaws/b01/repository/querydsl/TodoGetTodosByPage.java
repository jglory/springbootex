package page.aaws.b01.repository.querydsl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import page.aaws.b01.domain.TodoEntity;
import page.aaws.b01.dto.PageRequestDto;

public interface TodoGetTodosByPage {
    Page<TodoEntity> getTodosByPage(PageRequestDto pageRequestDto, Pageable pageable);
}
