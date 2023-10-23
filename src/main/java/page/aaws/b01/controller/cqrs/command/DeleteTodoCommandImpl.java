package page.aaws.b01.controller.cqrs.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteTodoCommandImpl implements DeleteTodoCommand {
    private final Long id;
}
