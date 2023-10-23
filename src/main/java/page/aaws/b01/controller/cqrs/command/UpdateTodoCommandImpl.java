package page.aaws.b01.controller.cqrs.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import page.aaws.b01.dto.TodoDto;

@AllArgsConstructor
@Getter
public class UpdateTodoCommandImpl implements UpdateTodoCommand {
    private final TodoDto todoDto;
}
