package page.aaws.b01.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import page.aaws.b01.controller.cqrs.command.DeleteTodoCommand;
import page.aaws.b01.service.TodoService;

@Component
@RequiredArgsConstructor
public class DeleteTodoCommandHandlerImpl implements DeleteTodoCommandHandler {
    private final TodoService todoService;

    @Override
    public Long process(DeleteTodoCommand commandOrQuery) {
        return this.todoService.deleteTodo(commandOrQuery.getId());
    }
}
