package page.aaws.b01.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import page.aaws.b01.controller.cqrs.query.GetTodosByPageQuery;
import page.aaws.b01.dto.PageDto;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

@Component
@Lazy
@RequiredArgsConstructor
public class GetTodosByPageQueryHandlerImpl implements GetTodosByPageQueryHandler {
    private final TodoService todoService;

    @Override
    public PageDto<TodoDto> process(GetTodosByPageQuery commandOrQuery) {
        return this.todoService.getTodosByPage(commandOrQuery.getPageRequestDto());
    }
}
