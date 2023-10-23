package page.aaws.b01.controller.handler;

import page.aaws.b01.controller.cqrs.command.AddNewTodoCommand;
import page.aaws.b01.dto.TodoDto;

public interface AddNewTodoCommandHandler extends CommandAndQueryHandler<AddNewTodoCommand, TodoDto> {
}
