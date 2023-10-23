package page.aaws.b01.controller.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import page.aaws.b01.controller.cqrs.command.AddNewTodoCommand;
import page.aaws.b01.dto.TodoDto;
import page.aaws.b01.service.TodoService;

@Component
@RequiredArgsConstructor
public class AddNewTodoCommandHandlerImpl implements AddNewTodoCommandHandler {
    private final TodoService todoService;

    @Override
    public TodoDto process(AddNewTodoCommand commandOrQuery) {
        return this.todoService.addNewTodo(commandOrQuery.getTodoDto());
    }
}
