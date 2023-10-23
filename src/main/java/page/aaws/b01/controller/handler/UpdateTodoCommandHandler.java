package page.aaws.b01.controller.handler;

import page.aaws.b01.controller.cqrs.command.UpdateTodoCommand;
import page.aaws.b01.dto.TodoDto;

public interface UpdateTodoCommandHandler extends CommandAndQueryHandler<UpdateTodoCommand, TodoDto> {
}
