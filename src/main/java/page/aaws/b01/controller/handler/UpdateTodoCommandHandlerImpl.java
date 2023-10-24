package page.aaws.b01.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import page.aaws.b01.controller.cqrs.command.UpdateTodoCommand;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

@Component
@Lazy
@RequiredArgsConstructor
public class UpdateTodoCommandHandlerImpl implements UpdateTodoCommandHandler {
    private final TodoService todoService;

    @Override
    public TodoDto process(UpdateTodoCommand commandOrQuery) {
        return this.todoService.updateTodo(commandOrQuery.getTodoDto());
    }
}
