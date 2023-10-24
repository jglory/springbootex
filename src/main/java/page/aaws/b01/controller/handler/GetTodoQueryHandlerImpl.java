package page.aaws.b01.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import page.aaws.b01.controller.cqrs.query.GetTodoQuery;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

@Component
@Lazy
@RequiredArgsConstructor
public class GetTodoQueryHandlerImpl implements GetTodoQueryHandler {
    private final TodoService todoService;

    @Override
    public TodoDto process(GetTodoQuery commandOrQuery) {
        return this.todoService.getTodo(commandOrQuery.getId());
    }
}
