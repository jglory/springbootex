package page.aaws.b01.controller.cqrs.command;

import page.aaws.b01.cqrs.Command;
import page.aaws.b01.dto.TodoDto;

public interface AddNewTodoCommand extends Command {
    TodoDto getTodoDto();
}
