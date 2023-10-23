package page.aaws.b01.controller.handler;

import page.aaws.b01.controller.cqrs.command.AddNewTodoCommand;
public interface AddNewTodoCommandHandler extends CommandAndQueryHandler<AddNewTodoCommand, Long> {
}
